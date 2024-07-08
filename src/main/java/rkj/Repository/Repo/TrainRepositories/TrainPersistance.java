package rkj.Repository.Repo.TrainRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rkj.objLib.objLib.TrainServiceObject.Dto.Train;
import rkj.objLib.objLib.TrainServiceObject.Entity.TrainEntity;

@Component
public class TrainPersistance {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TrainRepo trainRepo;

    public void addTrain(Train train){
        trainRepo.save(mapper.convertValue(train, TrainEntity.class));
    }

    public Train getTrainDetails(Integer trainNumber){
        return mapper.convertValue(trainRepo.findById(trainNumber).get(),Train.class);
    }
}
