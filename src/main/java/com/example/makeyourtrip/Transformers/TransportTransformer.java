package com.example.makeyourtrip.Transformers;

import com.example.makeyourtrip.Models.Transport;
import com.example.makeyourtrip.RequestDto.AddTransportDto;
import com.example.makeyourtrip.ResponseDto.FlightResultDto;

public class TransportTransformer {

    public static Transport convertDtoToEntity(AddTransportDto addTransportDto){

        Transport transportObj = Transport.builder()
                .modeOfTransport(addTransportDto.getModeOfTransport())
                .startTime(addTransportDto.getStartTime())
                .journeyDate(addTransportDto.getJourneyDate())
                .journeyTime(addTransportDto.getJourneyTime())
                .companyName(addTransportDto.getCompanyName())
                .build();

        return transportObj;
    }

    public static FlightResultDto convertEntityToFlightResultDto(Transport transport){

        return FlightResultDto.builder().journeyDate(transport.getJourneyDate())
                .startTime(transport.getStartTime())
                .journeyTime(transport.getJourneyTime())
                .companyName(transport.getCompanyName())
                .build();
    }
}
