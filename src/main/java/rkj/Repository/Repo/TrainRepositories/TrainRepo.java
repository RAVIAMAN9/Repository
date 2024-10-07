package rkj.Repository.Repo.TrainRepositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Entity.TrainEntity;

import java.util.List;


public interface TrainRepo extends JpaRepository<TrainEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update train.train set stoppages=:stoppages where train_number = :trainNumber", nativeQuery = true)
    int updateStoppagesOfTrain(String stoppages, Integer trainNumber);

    List<TrainEntity> findByTrainNumberIn(List<Integer> trainNumbers);
}
