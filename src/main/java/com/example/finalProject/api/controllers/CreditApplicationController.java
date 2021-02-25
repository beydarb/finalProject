package com.example.finalProject.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalProject.business.abstracts.ICreditApplicationService;
import com.example.finalProject.entities.concretes.Customer;

@RestController
@RequestMapping("/api/v2")
public class CreditApplicationController {
	@Autowired
	ICreditApplicationService creditApplicationService;
	
	@PostMapping("/credit_applications")
	public Map<String, Boolean> creditApplication(@RequestBody Customer customer) throws Exception{
		return creditApplicationService.creditApplication(customer);
	}

}
