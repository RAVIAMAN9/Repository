package rkj.Repository.Repo.TrainRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rkj.objLib.objLib.TrainServiceObject.Dto.Train;
import rkj.objLib.objLib.TrainServiceObject.Dto.TrainResponse;
import rkj.objLib.objLib.TrainServiceObject.Entity.TrainEntity;

@Component
public class TrainPersistance {

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
}
