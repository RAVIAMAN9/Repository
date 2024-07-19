package rkj.Repository.Repo.StationRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import rkj.objLib.objLib.ServiceObjects.StationServiceObject.StationEntity;

public interface StationRepo extends JpaRepository<StationEntity,String> {
}
