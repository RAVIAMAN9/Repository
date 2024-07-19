package rkj.Repository.Repo.TrainRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rkj.objLib.objLib.ServiceObjects.TrainServiceObject.Entity.TrainEntity;


public interface TrainRepo extends JpaRepository<TrainEntity, Integer> {

}
