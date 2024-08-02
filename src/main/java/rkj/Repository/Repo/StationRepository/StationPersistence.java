package rkj.Repository.Repo.StationRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import rkj.Repository.Repo.TrainRepositories.TrainRepo;
import rkj.objLib.objLib.Exception.ExceptionObjects.StationException;
import rkj.objLib.objLib.ServiceObjects.StationServiceObject.Station;
import rkj.objLib.objLib.ServiceObjects.StationServiceObject.StationEntity;
import java.util.List;
import java.util.Optional;
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
    public void addStation(Station station) throws StationException {
        Optional<StationEntity> stationEntity = stationRepo.findById(station.getStationCode());
        if(stationEntity.isPresent()){
            throw new StationException(String.format("Station with the station code %s already exists.",station.getStationCode()));
        }
        StationEntity se = mapper.convertValue(station,StationEntity.class);
        stationRepo.save(se);
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
    }
}
