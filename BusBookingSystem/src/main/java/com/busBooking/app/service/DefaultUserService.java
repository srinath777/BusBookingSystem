package com.busBooking.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.busBooking.app.DTO.BookingsDTO;
import com.busBooking.app.DTO.UserRegisteredDTO;
import com.busBooking.app.model.Bookings;
import com.busBooking.app.model.User;

public interface DefaultUserService extends UserDetailsService{

	User save(UserRegisteredDTO userRegisteredDTO);

	Bookings updateBookings(BookingsDTO bookingDTO,UserDetails user);


	
}
