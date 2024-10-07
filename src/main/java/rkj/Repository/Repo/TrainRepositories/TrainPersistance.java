package rkj.Repository.Repo.TrainRepositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rkj.objLib.objLib.AsynchronousObjects.RabbitMqObjects.TicketEvent;
import rkj.objLib.objLib.Exception.ExceptionObjects.TrainException;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Dto.Train;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Dto.TrainResponse;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Entity.TrainEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TrainPersistance {

    @PersistenceContext
    private EntityManager entityManager;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TrainRepo trainRepo;

    public void addTrain(Train train) throws TrainException {
        Optional<TrainEntity> se = trainRepo.findById(train.getTrainNumber());
        if(se.isPresent()){
            throw new TrainException(String.format("Train with train number %s already exists.",train.getTrainNumber()));
        }
        trainRepo.save(mapper.convertValue(train, TrainEntity.class));
    }

    public TrainResponse getTrainDetails(Integer trainNumber){
        TrainResponse tr = new TrainResponse();
        TrainEntity t =  trainRepo.findById(trainNumber).get();
        tr.setTrainNumber(t.getTrainNumber());
        tr.setTrainName(t.getTrainName());
        tr.setSource(t.getSource());
        tr.setDestination(t.getDestination());
        tr.setTrainType(t.getTrainType());
        tr.setAc1Tier(t.getAc1Tier());
        tr.setAc2Tier(t.getAc2Tier());
        tr.setAc3Tier(t.getAc3Tier());
        tr.setSleeper(t.getSleeper());
        tr.setChairCar(t.getChairCar());
        tr.setStoppages(t.getStoppages());
        return tr;
    }

    @Transactional
    public void updateTrain(TicketEvent ticketEvent) throws TrainException {
        Integer q = getNumberOfSeats(ticketEvent.getCoachType(), ticketEvent.getTrainNumber());
        Integer seatNumber = 0;
        if(ticketEvent.getIsCancellable().equals(0)){
            seatNumber = q-ticketEvent.getNumberOfSeats();
        } else {
            seatNumber =q+ticketEvent.getNumberOfSeats();
        }
        updateTrainDetails(ticketEvent.getTrainNumber(), ticketEvent.getCoachType(), seatNumber);
        if(ticketEvent.getCoachType().equals("Ac3Tier")) {
            throw new TrainException();
        }
    }

    private Integer getNumberOfSeats(String coachType, Integer trainNumber){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Root<TrainEntity> root = cq.from(TrainEntity.class);
        cq.select(root.get(coachType));
        cq.where(cb.equal(root.get("trainNumber"),trainNumber));
        return entityManager.createQuery(cq).getSingleResult();

    }

    private int updateTrainDetails(Integer trainNumber, String columnName, Integer numberOfSeats) throws TrainException {
       CriteriaBuilder cb = entityManager.getCriteriaBuilder();
       CriteriaUpdate<TrainEntity> cq = cb.createCriteriaUpdate(TrainEntity.class);
       Root<TrainEntity> root = cq.from(TrainEntity.class);
       cq.set(root.get(columnName),numberOfSeats);
       cq.where(cb.equal(root.get("trainNumber"),trainNumber));
        return entityManager.createQuery(cq).executeUpdate();
    }

    @Transactional
    public void updateStoppagesInTrain(String stationCode, Integer trainNumber) throws JsonProcessingException {
        List<String> list = trainRepo.findById(trainNumber).get().getStoppages();
        //List list = mapper.readValue(s,List.class);
        list.add(stationCode);
        String s = list.stream()
                        .collect(Collectors.joining(","));
        s.replace("[","");
        s.replace("]","");
        trainRepo.updateStoppagesOfTrain(s, trainNumber);
    }

    public Map<Integer,String> getTrainList(List<Integer> trainNumbers) {
        List<TrainEntity> lst = trainRepo.findByTrainNumberIn(trainNumbers);
        return lst.stream()
                .collect(Collectors.toMap(TrainEntity::getTrainNumber,TrainEntity::getTrainName));
    }
}
