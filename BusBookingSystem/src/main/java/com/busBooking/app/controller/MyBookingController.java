package com.busBooking.app.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.busBooking.app.DTO.BookingsDTO;
import com.busBooking.app.model.Bookings;
import com.busBooking.app.model.User;
import com.busBooking.app.repository.BookingsRepository;
import com.busBooking.app.repository.UserRepository;

@Controller
@RequestMapping("/myBooking")
public class MyBookingController {

	
	@Autowired
	BookingsRepository bookingsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	 @ModelAttribute("bookings")
	    public BookingsDTO bookingDto() {
	        return new BookingsDTO();
	    }
	    
		@GetMapping
		public String login(Model model) {
			SecurityContext securityContext = SecurityContextHolder.getContext();
	        UserDetails users = (UserDetails) securityContext.getAuthentication().getPrincipal();
	        User user =userRepository.findByEmail(users.getUsername());
			List<Bookings> bs = bookingsRepository.findByUserId(user.getId());
			model.addAttribute("userDetails", user.getName());
			Collections.reverse(bs);
			model.addAttribute("bookings",bs);
			return "myBookings";
		}
}
