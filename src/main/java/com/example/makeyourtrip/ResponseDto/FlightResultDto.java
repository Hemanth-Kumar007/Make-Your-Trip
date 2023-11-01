package com.example.makeyourtrip.ResponseDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class FlightResultDto {

    private LocalDate journeyDate;

    private LocalTime startTime;

    private double journeyTime;

    private String companyName;

    private String listOfStopsInBetween;
}
