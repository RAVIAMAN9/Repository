package rkj.Repository.Repo.TicketRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rkj.objLib.objLib.ServiceObjects.TicketObject.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update train.ticket set ticket_status=:value where pnr_number=:pnrNumber", nativeQuery = true)
    int updateTicketStatus(@Param("value") Integer value, @Param("pnrNumber") Integer pnrNumber);
}
