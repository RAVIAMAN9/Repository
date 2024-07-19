package rkj.Repository.Repo.TrainRepositories;

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
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Dto.Train;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Dto.TrainResponse;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Entity.TrainEntity;

@Component
public class TrainPersistance {

    @PersistenceContext
    private EntityManager entityManager;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TrainRepo trainRepo;

    public void addTrain(Train train){
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
        return tr;
    }

//    @Transactional
//    public void updateTrain(TicketEvent ticketEvent){
//        Integer q = getNumberOfSeats(ticketEvent.getCoachType(), ticketEvent.getTrainNumber());
//        updateTrainDetails(ticketEvent.getTrainNumber(), ticketEvent.getCoachType(), (q-ticketEvent.getNumberOfSeats()));
//    }

    private Integer getNumberOfSeats(String coachType, Integer trainNumber){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Root<TrainEntity> root = cq.from(TrainEntity.class);
        cq.select(root.get(coachType));
        cq.where(cb.equal(root.get("trainNumber"),trainNumber));
        return entityManager.createQuery(cq).getFirstResult();

    }

    private void updateTrainDetails(Integer trainNumber, String columnName, Integer numberOfSeats){
        String sql = "Update train.train set "+columnName+ " = :numberOfSeats where train_number= :trainNumber";
        entityManager.createNativeQuery(sql)
                .setParameter("numberOfSeats",numberOfSeats)
                .setParameter("trainNumber",trainNumber)
                .executeUpdate();
    }
}
