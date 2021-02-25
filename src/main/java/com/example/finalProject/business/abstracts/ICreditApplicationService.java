package com.example.finalProject.business.abstracts;

import java.util.Map;

import com.example.finalProject.entities.concretes.Customer;

public interface ICreditApplicationService {
	public Map<String, Boolean> creditApplication(Customer customer) throws Exception;
}
