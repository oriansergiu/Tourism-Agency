package agency.network.dto;

import agency.model.entities.Agent;
import agency.model.entities.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class DTOUtils {

    public static Agent getFromDTO(AgentDTO agdto){
        String user = agdto.getUser();
        String pass = agdto.getPassword();
        return new Agent(user, pass);

    }

    public static AgentDTO getDTO(Agent ag){
        String user = ag.getUser();
        String pass = ag.getPassword();
        return new AgentDTO(user, pass);

    }

    public static Trip getFromDTO(TripDTO trDto)
    {
        Integer id = trDto.getId();
        String landmark = trDto.getLandmark();
        String transportCompanyName = trDto.getTransportCompanyName();
        String time = trDto.getTime();
        String price = trDto.getPrice();
        String numberOfSeats = trDto.getNumberOfSeats();
        return new Trip(id,landmark,transportCompanyName,time,price,numberOfSeats);
    }

    public static TripDTO getDTO(Trip tr)
    {
        Integer id = tr.getId();
        String landmark = tr.getLandmark();
        String transportCompanyName = tr.getTransportCompanyName();
        String time = tr.getTime();
        String price = tr.getPrice();
        String numberOfSeats = tr.getNumberOfSeats();
        return new TripDTO(id,landmark,transportCompanyName,time,price,numberOfSeats);
    }

    public static Trip[] getFromDTO(TripDTO[] tripsDTO)
    {
        Trip[] trips = new Trip[tripsDTO.length];
        for (int elem = 0; elem < tripsDTO.length; elem++)
            trips[elem] = getFromDTO(tripsDTO[elem]);
        return trips;
    }

    public static TripDTO[] getDTO(Trip[] trips)
    {
        TripDTO[] tripsDTO = new TripDTO[trips.length];
        for(int elem =0; elem < trips.length; elem++)
            tripsDTO[elem] = getDTO(trips[elem]);
        return tripsDTO;
    }
}
