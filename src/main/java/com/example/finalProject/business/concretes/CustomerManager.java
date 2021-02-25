package com.example.finalProject.business.concretes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalProject.business.abstracts.ICustomerService;
import com.example.finalProject.dataaccess.concretes.CustomerRepository;
import com.example.finalProject.entities.concretes.Customer;

@Service
public class CustomerManager implements ICustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer add(Customer customer) {
		if(customer.getCustomerId().length() != 11) {
			System.out.println("Invalid customer id length");
			return null;
		}
		else
			return customerRepository.save(customer);
	}

	@Override
	public Map<String, Boolean> delete(Customer customer) throws Exception {
		Customer customerToDelete = customerRepository.findById(customer.getCustomerId())
				.orElseThrow(()->new Exception("No customer with id: " + customer.getCustomerId()));
		
		customerRepository.delete(customerToDelete);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Customer has been deleted", Boolean.TRUE);
		return response;
	}

}
