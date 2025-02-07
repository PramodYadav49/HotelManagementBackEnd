package com.pramod.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pramod.dto.BookingDTO;
import com.pramod.dto.Response;
import com.pramod.entity.Booking;
import com.pramod.entity.Room;
import com.pramod.entity.User;
import com.pramod.exception.OurException;
import com.pramod.repo.BookingRepository;
import com.pramod.repo.RoomRepository;
import com.pramod.repo.UserRepository;
import com.pramod.service.interfac.IBookService;
import com.pramod.service.interfac.IRoomService;
import com.pramod.utils.Utils;
@Service
public class BookingService implements IBookService{


	

	    @Autowired
	    private BookingRepository bookingRepository;
	    @Autowired
	    private IRoomService roomService;
	    @Autowired
	    private RoomRepository roomRepository;
	    @Autowired
	    private UserRepository userRepository;


	    @Override
	    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {

	        Response response = new Response();

	        try {
	            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
	                throw new IllegalArgumentException("Check in date must come after check out date");
	            }
	            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
	            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

	            List<Booking> existingBookings = room.getBookings();

	            if (!roomIsAvailable(bookingRequest, existingBookings)) {
	                throw new OurException("Room not Available for selected date range");
	            }

	            bookingRequest.setRoom(room);
	            bookingRequest.setUser(user);
	            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
	            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
	            bookingRepository.save(bookingRequest);
	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setBookingConfirmationCode(bookingConfirmationCode);

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());

	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error Saving a booking: " + e.getMessage());

	        }
	        return response;
	    }


	    @Override
	    public Response findBookingByConfirmationCode(String confirmationCode) {

	        Response response = new Response();

	        try {
	            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new OurException("Booking Not Found"));
	            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);
	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setBooking(bookingDTO);

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());

	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error Finding a booking: " + e.getMessage());

	        }
	        return response;
	    }

	    @Override
	    public Response getAllBookings() {

	        Response response = new Response();

	        try {
	            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setBookingList(bookingDTOList);

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());

	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error Getting all bookings: " + e.getMessage());

	        }
	        return response;
	    }

	    @Override
	    public Response cancelBooking(Long bookingId) {

	        Response response = new Response();

	        try {
	            bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking Does Not Exist"));
	            bookingRepository.deleteById(bookingId);
	            response.setStatusCode(200);
	            response.setMessage("successful");

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());

	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error Cancelling a booking: " + e.getMessage());

	        }
	        return response;
	    }


	    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
	        return existingBookings.stream()
	                .noneMatch(existingBooking ->
	                        // Check if the requested booking overlaps with any existing booking
	                        !bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckOutDate()) &&
	                        !bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckInDate())
	                );
	    }
}
	    
