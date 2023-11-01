package com.example.makeyourtrip.Controllers;

import com.example.makeyourtrip.RequestDto.AddFlightSeatDto;
import com.example.makeyourtrip.Services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/addFightSeats")
    public String addFlightSeats(@RequestBody AddFlightSeatDto addFlightSeatDto){

        return seatService.addFlightSeats(addFlightSeatDto);
    }
}
