package com.busBooking.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busBooking.app.model.Bookings;




public interface BookingsRepository extends JpaRepository<Bookings, Integer> {

	List<Bookings> findByUserId(int userId);
	
}
