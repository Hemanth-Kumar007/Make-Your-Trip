package com.example.makeyourtrip.Controllers;

import com.example.makeyourtrip.RequestDto.AddTransportDto;
import com.example.makeyourtrip.RequestDto.SearchFlightDto;
import com.example.makeyourtrip.ResponseDto.FlightResultDto;
import com.example.makeyourtrip.Services.TransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;
    @PostMapping("/add")
    public ResponseEntity addTransport(@RequestBody AddTransportDto addTransportDto){

        try{
            String result = transportService.addTransport(addTransportDto);
            return new ResponseEntity(result, HttpStatus.OK);

        }catch (Exception e){
            log.error("Add Transport failed {}", e.getMessage()); // we can user '+' instead of ',' here
            // but many people use ',', because '+' will create a new string and occupy more memory
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/searchFlights")
    public ResponseEntity searchFlights(@RequestBody SearchFlightDto searchFlightDto){

        List<FlightResultDto> flightResultDtoList = transportService.searchForFlights(searchFlightDto);

        return new ResponseEntity(flightResultDtoList, HttpStatus.OK);
    }

}
