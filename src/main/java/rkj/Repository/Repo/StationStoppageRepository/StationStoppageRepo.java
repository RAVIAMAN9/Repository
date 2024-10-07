package rkj.Repository.Repo.StationStoppageRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import rkj.objLib.objLib.ServiceObjects.StationStoppageObject.StationStoppageEntity;

public interface StationStoppageRepo extends JpaRepository<StationStoppageEntity,String> {


}
