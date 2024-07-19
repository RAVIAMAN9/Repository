package rkj.Repository.Repo.StationStoppageRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rkj.objLib.objLib.ServiceObjects.StationStoppageObject.StationStoppage;
import rkj.objLib.objLib.ServiceObjects.StationStoppageObject.StationStoppageEntity;

import java.util.List;

@Component
public class StationStoppagePersistance {

    @Autowired
    private StationStoppageRepo stationStoppageRepo;

    private ObjectMapper mapper = new ObjectMapper();

    public void addStationStoppage(StationStoppage stationStoppage) {
        stationStoppageRepo.save(mapper.convertValue(stationStoppage, StationStoppageEntity.class));
    }

    public List<Integer> getTrainNumbers(String stationCode){
        StationStoppageEntity s =  stationStoppageRepo.findById(stationCode.toUpperCase()).get();
        return mapper.convertValue(s,StationStoppage.class).getTrainList();
    }
}
