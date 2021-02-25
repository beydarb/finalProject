package com.example.finalProject.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalProject.business.abstracts.ICreditAuthorizationService;
import com.example.finalProject.entities.concretes.CreditAuthorization;
import com.example.finalProject.entities.concretes.Customer;

@RestController
@RequestMapping("/api/v2")
public class CreditAuthorizationController {
	@Autowired
	ICreditAuthorizationService creditAuthorizationService;
	
	@GetMapping("/credit_authorizations")
	public List<CreditAuthorization> getAll() {
		return creditAuthorizationService.getAll();
	}
	@PostMapping("/credit_authorizations")
	public Map<String, Boolean> authorization(@RequestBody Customer customer) throws Exception {
		return creditAuthorizationService.authorization(customer);
	}
}
