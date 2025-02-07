package com.pramod.dto;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomDescription;
    private String roomPhoto;
    private List<BookingDTO> bookings;

    // Convert byte[] to Base64 and set as a data URI
    public void setRoomPhoto(byte[] photoBytes) {
        if (photoBytes != null && photoBytes.length > 0) {
            String base64String = Base64.getEncoder().encodeToString(photoBytes);
            this.roomPhoto = "data:image/png;base64," + base64String; // Defaulting to PNG
        } else {
            this.roomPhoto = null;
        }
    }
}
