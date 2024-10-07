package rkj.Repository.Repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rkj.Repository.Repo.TrainRepositories.TrainPersistance;
import rkj.objLib.objLib.AsynchronousObjects.RabbitMqObjects.TicketEvent;
import rkj.objLib.objLib.Exception.ExceptionObjects.TrainException;

@RestController
@RequestMapping("/repo")
public class RepositoryController {

    @Autowired
    TrainPersistance trainPersistance;

    @PostMapping
    public void get(@RequestBody TicketEvent ticketEvent) throws TrainException {
        trainPersistance.updateTrain(ticketEvent);
    }
}
