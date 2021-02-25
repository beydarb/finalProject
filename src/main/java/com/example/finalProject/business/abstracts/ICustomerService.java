package com.example.finalProject.business.abstracts;

import java.util.List;
import java.util.Map;

import com.example.finalProject.entities.concretes.Customer;

public interface ICustomerService {
	List<Customer> getAll();
	Customer add(Customer customer);
	public Map<String, Boolean> delete(Customer customer) throws Exception;
}
