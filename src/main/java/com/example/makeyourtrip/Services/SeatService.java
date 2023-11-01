package com.example.makeyourtrip.Services;

import com.example.makeyourtrip.Enums.SeatType;
import com.example.makeyourtrip.Models.Seat;
import com.example.makeyourtrip.Models.Transport;
import com.example.makeyourtrip.Repositories.SeatRepository;
import com.example.makeyourtrip.Repositories.TransportRepository;
import com.example.makeyourtrip.RequestDto.AddFlightSeatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TransportRepository transportRepository;

    public String addFlightSeats(AddFlightSeatDto seatDto){

        Transport transport = transportRepository.findById(seatDto.getTransportId()).get();

        //int seatNo = 1;
        for(int i=1; i<seatDto.getNoOfEconomySeats(); i++){

            Seat seat = Seat.builder().seatNo(String.valueOf("E"+i))
                    .seatType(SeatType.ECONOMY)
                    .price(seatDto.getPriceOfEconomySeat())
                    .transport(transport)   //setting transport object for seat entity(foreign key)
                    .build();

            //seatNo++;
            //Adding, because of bidirectional setting in parent class also
            transport.getSeatList().add(seat);
        }

        for(int i=1; i<seatDto.getNoOfBusinessSeats(); i++){

            Seat seat = Seat.builder().seatNo(String.valueOf("B"+i))
                    .seatType(SeatType.BUSINESS)
                    .price(seatDto.getPriceOfBusinessSeat())
                    .transport(transport)   //setting transport object for seat entity(foreign key)
                    .build();

            //seatNo++;
            //Adding, because of bidirectional setting in parent class also
            transport.getSeatList().add(seat);
        }

        transportRepository.save(transport);

        return "Defined seats have been added";
    }
}
