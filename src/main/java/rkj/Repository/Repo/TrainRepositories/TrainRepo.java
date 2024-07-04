package rkj.Repository.Repo.TrainRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rkj.objLib.objLib.TrainServiceObject.Entity.TrainEntity;


public interface TrainRepo extends JpaRepository<TrainEntity, Integer> {
}
