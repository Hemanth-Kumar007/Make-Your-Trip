package com.example.makeyourtrip.RequestDto;

import com.example.makeyourtrip.Models.TicketEntity;
import com.example.makeyourtrip.Models.Transport;
import com.example.makeyourtrip.Models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BookingRequestDto {

    private String seatNos; //Comma separated values like 1A,1B  since one user can book multiple seats at a time
    private LocalDate journeyDate;
    private Integer transportId;

    private Integer userId;
}
