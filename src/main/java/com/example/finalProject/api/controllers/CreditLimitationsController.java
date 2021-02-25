package com.example.finalProject.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalProject.business.abstracts.ICreditLimitationService;
import com.example.finalProject.entities.concretes.CreditLimitation;

@RestController
@RequestMapping("/api/v2")
public class CreditLimitationsController {
	@Autowired
	ICreditLimitationService creditLimitationService;
	
	@PostMapping("/credit_limitations")
	public CreditLimitation add(@RequestBody CreditLimitation creditLimitation) throws Exception {
		return creditLimitationService.add(creditLimitation);
	}
	
	@DeleteMapping("/credit_limitations")
	public Map<String, Boolean> delete(@RequestBody CreditLimitation creditLimitation) throws Exception{
		return creditLimitationService.delete(creditLimitation);
	}
	
}
