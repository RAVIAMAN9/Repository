package rkj.Repository.Repo.TicketRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rkj.objLib.objLib.ServiceObjects.PassengerObject.Passenger;
import rkj.objLib.objLib.ServiceObjects.TicketObject.Ticket;
import rkj.objLib.objLib.ServiceObjects.TicketObject.TicketEntity;
import rkj.objLib.objLib.ServiceObjects.TicketObject.TicketResponse;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class TicketPersistence {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TicketRepository ticketRepository;

    public TicketResponse addTicket(Ticket ticket){
        Gson gson = new Gson();
        String passengerList = gson.toJson(ticket.getPassengerList());
        TicketEntity tt = new TicketEntity();
        tt.setTrainNumber(ticket.getTrainNumber());
        tt.setBoarding(ticket.getBoarding());
        tt.setDestination(ticket.getDestination());
        tt.setPassengerList(passengerList);
        tt.setCoachType(ticket.getCoachType());
        TicketEntity te = ticketRepository.save(tt);
        TicketResponse tr = new TicketResponse();
        tr.setPnrNumber(te.getPnrNumber());
        tr.setTrainNumber(te.getTrainNumber());
        Type listType = new TypeToken<List<Passenger>>(){}.getType();
        List<Passenger> lst = gson.fromJson(passengerList,listType);
        tr.setPassengerList(lst);
        return tr;
    }

    public TicketResponse getTicketDetails(Integer pnrNumber) throws JsonProcessingException {
        TicketEntity te = ticketRepository.findById(pnrNumber).get();
        List<Passenger> p = mapper.readValue(te.getPassengerList(), new TypeReference<List<Passenger>>() {});
        TicketResponse tr = new TicketResponse();
        tr.setTrainNumber(te.getTrainNumber());
        tr.setPnrNumber(te.getPnrNumber());
        tr.setBoarding(te.getBoarding());
        tr.setDestination(te.getDestination());
        tr.setCoachType(te.getCoachType());
        tr.setPassengerList(p);
        return tr;
    }

    @Transactional
    public void updateTicketStatus(Integer pnrNumber){
        ticketRepository.updateTicketStatus(1,pnrNumber);
    }
}
