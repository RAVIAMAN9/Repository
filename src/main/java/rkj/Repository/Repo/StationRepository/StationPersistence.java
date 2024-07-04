package rkj.Repository.Repo.StationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import rkj.objLib.objLib.StationServiceObject.Station;
import rkj.objLib.objLib.StationServiceObject.StationEntity;

@Component
public class StationPersistence {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StationRepo stationRepo;

    public void addStation(Station station) {
        stationRepo.save(mapper.convertValue(station, StationEntity.class));
    }

}
