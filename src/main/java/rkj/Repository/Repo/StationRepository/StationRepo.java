package rkj.Repository.Repo.StationRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rkj.objLib.objLib.ServiceObjects.StationServiceObject.StationEntity;

import java.util.List;

public interface StationRepo extends JpaRepository<StationEntity,String> {

    @Transactional
    @Modifying
    @Query(value = "update train.station set train_numbers=:trainNumbers where station_code=:stationCode", nativeQuery = true)
    int updateTrainNumbers(String trainNumbers, String stationCode);

    @Query(value = "select train_numbers from train.station where station_code=:stationCode", nativeQuery = true)
    List<Integer> getTrainNumbers(String stationCode);
}
