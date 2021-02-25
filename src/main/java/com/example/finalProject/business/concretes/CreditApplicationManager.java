package com.example.finalProject.business.concretes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalProject.business.abstracts.ICreditApplicationService;
import com.example.finalProject.dataaccess.concretes.BlacklistRepository;
import com.example.finalProject.dataaccess.concretes.CreditAuthorizationRepository;
import com.example.finalProject.dataaccess.concretes.CreditLimitationRepository;
import com.example.finalProject.dataaccess.concretes.CustomerRepository;
import com.example.finalProject.entities.concretes.CreditAuthorization;
import com.example.finalProject.entities.concretes.CreditLimitation;
import com.example.finalProject.entities.concretes.Customer;

@Service
public class CreditApplicationManager implements ICreditApplicationService {
	@Autowired
	private CustomerRepository customerRepository;
		
	@Autowired
	private CreditAuthorizationRepository creditAuthorizationRepository;
	
	@Autowired
	private BlacklistRepository blacklistRepository;
	
	@Autowired
	private CreditLimitationRepository creditLimitationRepository;
	
	@Override
	public Map<String, Boolean> creditApplication(Customer customer) throws Exception{
		if(customerRepository.existsByCustomerId(customer.getCustomerId()) == false) { 
			//if it is not a turkcell customer, add it customers
			Map<String, Boolean> response = new HashMap<>();
			if(customer.getCustomerId().length() != 11) {
				response.put("Invalid customer id length", Boolean.FALSE);
			}
			else {
				Date today = new Date();
				customer.setDateOfSubscription(today);
				customerRepository.save(customer);
				//create its credit
				CreditAuthorization creditAuthorization = new CreditAuthorization();
				creditAuthorization.setCreditWanted(customer.getCreditWanted());
				creditAuthorization.setCustomerId(customer.getCustomerId());
				creditAuthorization.setCreditSituation("In progress");
				creditAuthorization.setNewCustomer(true);
				creditAuthorizationRepository.save(creditAuthorization); 	//send it to approval
				
				response.put("Customer has been added to the customer system and credit system.", Boolean.TRUE);
			}
			
			return response;
		}
		else { //if it is a turkcell customer
			
			Date today = new Date();
			Customer customerToProcess = customerRepository.findById(customer.getCustomerId())
					.orElseThrow(()->new Exception("No customer with id: " + customer.getCustomerId()));
			if(blacklistRepository.existsById(customer.getCustomerId()) == true) { //if the customer is in blacklist
				//no need to authorization, reject directly
				customerToProcess.setCreditWanted(customer.getCreditWanted());
				customerToProcess.setCreditGiven(0);
				customerRepository.save(customerToProcess);
				Map<String, Boolean> response = new HashMap<>();
				response.put("Credit application has been rejected. Reason: customer is in blacklist." , Boolean.FALSE);
				return response;
				
			}
			else
			{
				CreditLimitation creditLimitationToProcess =  creditLimitationRepository.findById(customer.getCustomerId())
						.orElseThrow(()->new Exception("No customer with id: " + customer.getCustomerId()));
							
				//if customer has a legal pursuit, reject directly again
				if(creditLimitationToProcess.isLegallyPursuit() == true) {
					customerToProcess.setCreditWanted(customer.getCreditWanted());
					customerToProcess.setCreditGiven(0);
					customerToProcess.setDateOfCredit(today);
					customerRepository.save(customerToProcess);
					Map<String, Boolean> response = new HashMap<>();
					response.put("Credit application has been rejected. Reason: customer has legal pursuit.", Boolean.FALSE);
					return response;
				}
				
				else { //otherwise needs to be sent to authorization		
					//after limitation process, send credit application to authorization
					CreditAuthorization newCredit = new CreditAuthorization();
					newCredit.setCustomerId(customer.getCustomerId());
					newCredit.setCreditWanted(customer.getCreditWanted());
					newCredit.setCreditSituation("In progress");
					newCredit.setNewCustomer(false);
					creditAuthorizationRepository.save(newCredit);
					Map<String, Boolean> response = new HashMap<>();
					response.put("Credit application has been received.", Boolean.TRUE);
					return response;
				}
				
			}
		}
	}
}
