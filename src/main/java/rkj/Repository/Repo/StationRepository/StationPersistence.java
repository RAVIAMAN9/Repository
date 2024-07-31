package rkj.Repository.Repo.StationRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import rkj.Repository.Repo.TrainRepositories.TrainRepo;
import rkj.objLib.objLib.AsynchronousObjects.KafkaObjects.TrainStoppage;
import rkj.objLib.objLib.ServiceObjects.StationServiceObject.Station;
import rkj.objLib.objLib.ServiceObjects.StationServiceObject.StationEntity;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Dto.Train;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Entity.TrainEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StationPersistence {

    private ObjectMapper mapper = new ObjectMapper();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StationRepo stationRepo;

    @Autowired
    private TrainRepo tr;

    public Station getStationDetails(String stationCode){
        StationEntity se = stationRepo.findById(stationCode).get();
        return mapper.convertValue(stationRepo.findById(stationCode).get(),Station.class);
    }

    public Station getStation(String stationCode){
        List<Integer> se = stationRepo.getTrainNumbers(stationCode);
        se.add(1);
        return mapper.convertValue(stationRepo.findById(stationCode).get(),Station.class);
    }
    public void addStation(Station station) {
        StationEntity se = new StationEntity();
        se.setStationCode(station.getStationCode());
        se.setStationName(station.getStationName());
        se.setState(station.getState());
        List<Integer> s = station.getTrainNumbers();
        se.setTrainNumbers(s);
        stationRepo.save(se);
        //stationRepo.save(mapper.convertValue(station, StationEntity.class));
    }

    @Transactional
    public void updateTrainStoppage(String stationCode, Integer trainNumber) throws JsonProcessingException {
        StationEntity se = stationRepo.findById(stationCode).get();
        List<Integer> lst = se.getTrainNumbers();
        lst.add(trainNumber);
        String s = lst.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        s.replace("[","");
        s.replace("]","");
        stationRepo.updateTrainNumbers(s, stationCode);
        //updateTrainStoppageList(stationCode, String.valueOf(sb));
    }

//    private void updateTrainStoppageList(String stationCode, String trainList){
//        CriteriaBuilder cd = entityManager.getCriteriaBuilder();
//        CriteriaUpdate<StationEntity> cu = cd.createCriteriaUpdate(StationEntity.class);
//        Root<StationEntity> root = cu.from(StationEntity.class);
//        cu.set(root.get())
//    }
}
