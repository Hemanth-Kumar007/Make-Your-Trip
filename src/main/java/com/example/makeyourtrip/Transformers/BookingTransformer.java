package com.example.makeyourtrip.Transformers;

import com.example.makeyourtrip.Models.Booking;
import com.example.makeyourtrip.RequestDto.BookingRequestDto;

public class BookingTransformer {

    public static Booking convertDtoToEntity(BookingRequestDto bookingRequestDto){

        Booking bookingObj = Booking.builder().journeyDate(bookingRequestDto.getJourneyDate())
                .seatNos(bookingRequestDto.getSeatNos())
                .transportId(bookingRequestDto.getTransportId())
                .build();

        return bookingObj;
    }
}
