package com.example.finalProject.business.abstracts;

import java.util.List;
import java.util.Map;

import com.example.finalProject.entities.concretes.CreditAuthorization;
import com.example.finalProject.entities.concretes.Customer;

public interface ICreditAuthorizationService {
	public List<CreditAuthorization> getAll();
	public Map<String, Boolean> authorization(Customer customer) throws Exception;
}
