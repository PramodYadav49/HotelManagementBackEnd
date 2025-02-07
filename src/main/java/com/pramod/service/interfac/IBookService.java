package com.pramod.service.interfac;

import com.pramod.dto.Response;
import com.pramod.entity.Booking;

public interface IBookService {

	 
		Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

	    Response findBookingByConfirmationCode(String confirmationCode);

	    Response getAllBookings();

	    Response cancelBooking(Long bookingId);
	
}
