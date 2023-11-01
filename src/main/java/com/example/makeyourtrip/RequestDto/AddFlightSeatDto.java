package com.example.makeyourtrip.RequestDto;

import lombok.Data;

@Data
public class AddFlightSeatDto {

    private Integer noOfEconomySeats;
    private Integer noOfBusinessSeats;

    private Integer priceOfEconomySeat;
    private Integer priceOfBusinessSeat;

    private Integer transportId;
}
