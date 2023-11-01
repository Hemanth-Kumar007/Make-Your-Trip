package com.example.makeyourtrip.Services;

import com.example.makeyourtrip.Models.*;
import com.example.makeyourtrip.Repositories.BookingRepository;
import com.example.makeyourtrip.Repositories.TransportRepository;
import com.example.makeyourtrip.Repositories.UserRepository;
import com.example.makeyourtrip.RequestDto.BookingRequestDto;
import com.example.makeyourtrip.RequestDto.GetAvailableSeatsDto;
import com.example.makeyourtrip.ResponseDto.AvailableSeatResponseDto;
import com.example.makeyourtrip.Transformers.BookingTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AvailableSeatResponseDto> getAvailableSeatResponse(GetAvailableSeatsDto entryDto){

        //We will get the list of already done bookings from booking repository
        List<Booking> doneBookings = bookingRepository.findBookings(entryDto.getJourneyDate(), entryDto.getTransportId());

        //No we are extracting booked seats from each booking, since there will be multiple comma separated seats in 1 booking
        //like 1E,2B,3E

        Set<String> bookedSeats = new TreeSet<>();

        for(Booking booking : doneBookings){

            String str = booking.getSeatNos();
            String[] bookedSeatsInThatBooking = str.split(","); // array of booked seats of a booking

            for(String seatNo : bookedSeatsInThatBooking){
                bookedSeats.add(seatNo);
            }
        }

        Transport transport = transportRepository.findById(entryDto.getTransportId()).get();

        List<Seat> seatList = transport.getSeatList();

        //Total seats - Booked Seats = remaining seats

        List<AvailableSeatResponseDto> finalAvailableSeats = new ArrayList<>();

        for(Seat seat : seatList){

            if(bookedSeats.equals(seat.getSeatNo())){
                continue;
            }else{

                //We will be building the response object
                AvailableSeatResponseDto availableSeat = AvailableSeatResponseDto.builder()
                        .seatNo(seat.getSeatNo())
                        .seatType(seat.getSeatType())
                        .seatPrice(seat.getPrice())
                        .build();

                finalAvailableSeats.add(availableSeat); // adding the built dto to final list of available seats dto
            }
        }
        return finalAvailableSeats;
    }

    public String makeABooking(BookingRequestDto bookingRequestDto){


        User userObj = userRepository.findById(bookingRequestDto.getUserId()).get();
        Transport transportObj = transportRepository.findById(bookingRequestDto.getTransportId()).get();

        Booking booking = BookingTransformer.convertDtoToEntity(bookingRequestDto);

        //Create the ticket entity
        TicketEntity ticketEntity = createTicketEntity(transportObj, bookingRequestDto);

        //Set the FK
        booking.setTransport(transportObj);
        booking.setUser(userObj);
        booking.setTicketEntity(ticketEntity);

        //Setting the bidirectional mappings
        //Setting for ticket
        ticketEntity.setBooking(booking);

        //Setting the booking object in the transport
        transportObj.getBookings().add(booking);

        //Setting the booking object in the userObject
        userObj.getBookingList().add(booking);

        //Save is homework, figure it out

    }

    private TicketEntity createTicketEntity(Transport transport, BookingRequestDto bookingRequestDto){

        Integer totalPricePaid = findTotalPricePaid(transport, bookingRequestDto.getSeatNos());

        String routeDetails = findRouteDetails(transport);

        TicketEntity ticketEntity = TicketEntity.builder().seatNos(bookingRequestDto.getSeatNos())
                .journeyDate(bookingRequestDto.getJourneyDate())
                .startTime(transport.getStartTime())
                .routeDetails(routeDetails)
                .totalCostPaid(totalPricePaid)
                .build();

        return ticketEntity;
    }

    private String findRouteDetails(Transport transport){

        //route details should be a string like "DELHI TO BANGALORE"

        Routes route = transport.getRoute();

        String result = route.getFromCity()+" TO "+route.getToCity();

        return result;
    }

    private Integer findTotalPricePaid(Transport transport, String seatNos){
        //need to try as homework
    }
}
