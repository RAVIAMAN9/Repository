package rkj.Repository.Repo.TicketRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import rkj.objLib.objLib.ServiceObjects.TicketObject.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
}
