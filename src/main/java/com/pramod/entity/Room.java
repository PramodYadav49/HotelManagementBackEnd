package com.pramod.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookingConfirmationCode;
    private String roomType;
    private BigDecimal roomPrice; // Changed to BigDecimal
    @Lob
    private byte[] photo;  // Updated field name
    private String roomDescription;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    // Default constructor
    public Room() {}

    public Room(String bookingConfirmationCode, String roomType, BigDecimal roomPrice, byte[] photo, String roomDescription) {
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.photo = photo;
        this.roomDescription = roomDescription;
    }

   
}
