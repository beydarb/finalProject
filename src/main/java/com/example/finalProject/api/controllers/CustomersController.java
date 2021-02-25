package com.example.finalProject.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalProject.business.abstracts.ICustomerService;
import com.example.finalProject.entities.concretes.Customer;

@RestController
@RequestMapping("/api/v2")
public class CustomersController {
	@Autowired
	ICustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getAll(){
		return customerService.getAll();
	}
	
	@PostMapping("/customers")
	public Customer add(@RequestBody Customer customer) {
		return customerService.add(customer);
	}
	
	@DeleteMapping("/customers")
	public Map<String, Boolean> delete(@RequestBody Customer customer) throws Exception{
		return customerService.delete(customer);
	}
}
