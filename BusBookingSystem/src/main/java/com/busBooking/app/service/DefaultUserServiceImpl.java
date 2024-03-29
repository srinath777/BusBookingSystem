package com.busBooking.app.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.busBooking.app.DTO.BookingsDTO;
import com.busBooking.app.DTO.UserRegisteredDTO;
import com.busBooking.app.model.Bookings;
import com.busBooking.app.model.Role;
import com.busBooking.app.model.User;
import com.busBooking.app.repository.BookingsRepository;
import com.busBooking.app.repository.RoleRepository;
import com.busBooking.app.repository.UserRepository;

@Service
public class DefaultUserServiceImpl implements DefaultUserService{
   @Autowired
	private UserRepository userRepo;
   
   @Autowired
	private BookingsRepository bookingRepository;
	
   @Autowired
  	private RoleRepository roleRepo;
  	
   
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	}

	@Override
	public User save(UserRegisteredDTO userRegisteredDTO) {
		Role role = roleRepo.findByRole("USER");
		
		User user = new User();
		user.setEmail(userRegisteredDTO.getEmail_id());
		user.setName(userRegisteredDTO.getName());
		user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
		user.setRole(role);
		
		return userRepo.save(user);
	}

	@Override
	public Bookings updateBookings(BookingsDTO bookingDTO,UserDetails user) {
		Bookings booking = new Bookings();
		String email = user.getUsername();
		User users = userRepo.findByEmail(email);
		booking.setBusName(bookingDTO.getBusName());
		booking.setFilterDate(bookingDTO.getFilterDate());
		booking.setFromDestination(bookingDTO.getFromDestination());
		booking.setToDestination(bookingDTO.getToDestination());
		booking.setNoOfPersons(bookingDTO.getNoOfPersons());
		booking.setTotalCalculated(bookingDTO.getTotalCalculated());
		booking.setTime(bookingDTO.getTime());
		booking.setUserId(users.getId());
		return bookingRepository.save(booking);
	}

}
