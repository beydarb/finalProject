package com.example.finalProject.dataaccess.concretes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.finalProject.entities.concretes.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{
	Customer findByCustomerId(String string);
	boolean existsByCustomerId(String string);
}
