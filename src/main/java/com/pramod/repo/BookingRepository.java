package com.pramod.repo;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pramod.entity.Booking;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
	
	Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
	

}
