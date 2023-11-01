package com.example.makeyourtrip.Services;

import com.example.makeyourtrip.Enums.ModeOfTransport;
import com.example.makeyourtrip.Exceptions.RouteNotFoundException;
import com.example.makeyourtrip.Models.Routes;
import com.example.makeyourtrip.Models.Transport;
import com.example.makeyourtrip.Repositories.RouteRepository;
import com.example.makeyourtrip.Repositories.TransportRepository;
import com.example.makeyourtrip.RequestDto.AddTransportDto;
import com.example.makeyourtrip.RequestDto.SearchFlightDto;
import com.example.makeyourtrip.ResponseDto.FlightResultDto;
import com.example.makeyourtrip.Transformers.TransportTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@Slf4j
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private RouteRepository routeRepository;
    public String addTransport(AddTransportDto addTransportDto)throws Exception {

        Transport transportObj = TransportTransformer.convertDtoToEntity(addTransportDto);

        Optional<Routes> optionalRoutes = routeRepository.findById(addTransportDto.getRouteId());

        if(!optionalRoutes.isPresent())
            throw new RouteNotFoundException("Route Id is incorrect");

        Routes route = optionalRoutes.get();

        //FK column that we are setting
        transportObj.setRoute(route);

        //Bidirectional mapping also needs to be taken care of
        route.getTransportList().add(transportObj);

        //Bcz of Bidirectional mapping I am only saving the parent entity
        //automatically the child entity will be saved

        routeRepository.save(route);

        return "Transport has been added successfully";
    }

    public List<FlightResultDto> searchForFlights(SearchFlightDto searchFlightDto){

        //Getting list of routes with given from and to cities in searchFlightDto
        List<Routes> routes = routeRepository.findRoutesByFromCityAndToCityAndModeOfTransport(searchFlightDto.getFromCity(),
                                searchFlightDto.getToCity(), ModeOfTransport.FLIGHT);

        //Creating new arrayList to store the list of response Dto's of Flights
        List<FlightResultDto> flightResultDtoList = new ArrayList<>();
        for(Routes route : routes){

            log.info("We are having the route",route.getFromCity(),route.getToCity());

            List<Transport> transportList = route.getTransportList(); //list of transports associated to route

            //I should put a filter (Date Filter)

            log.info("We are having the transport : {} ", transportList.size());
            log.info("Search flight getJourney Date {}", searchFlightDto.getJourneyDate());

            for(Transport transport : transportList){

                if(transport.getJourneyDate().equals(searchFlightDto.getJourneyDate())){ //if date equals journey date of dto

                    //We are creating response dto from the respective transport entity using transformer
                    FlightResultDto resultDto = TransportTransformer.convertEntityToFlightResultDto(transport);

                    // since stops list is in route entity, adding it to Dto separately apart from transformer
                    resultDto.setListOfStopsInBetween(route.getListOfStopsInBetween());

                    flightResultDtoList.add(resultDto); //adding dto to list of Dto's to be returned
                }
            }
        }
        return flightResultDtoList;
    }
}
