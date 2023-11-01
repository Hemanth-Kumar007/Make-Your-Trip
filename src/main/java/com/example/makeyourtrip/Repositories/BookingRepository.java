package com.example.makeyourtrip.Repositories;

import com.example.makeyourtrip.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    //1st method to get the entries from repository: Query
    @Query(value = "select * from bookings where journeyDate =: journeyDateInput and transportId =: transportIdInput", nativeQuery = true)
    List<Booking> findBookings(LocalDate journeyDateInput, Integer transportIdInput);

    //2nd method: find by parameters
    //List<Booking> findBookingsByJourneyDateAndTransportId(LocalDate journeyDate, Integer transportId);
}
