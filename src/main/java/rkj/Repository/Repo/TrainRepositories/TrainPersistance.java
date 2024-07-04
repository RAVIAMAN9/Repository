package rkj.Repository.Repo.TrainRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import rkj.objLib.objLib.TrainServiceObject.Dto.Train;
import rkj.objLib.objLib.TrainServiceObject.Entity.TrainEntity;

@Component
public class TrainPersistance {

    private ObjectMapper mapper = new ObjectMapper();

    private TrainRepo trainRepo;

    public void addTrain(Train train){
        trainRepo.save(mapper.convertValue(train, TrainEntity.class));
    }
}
