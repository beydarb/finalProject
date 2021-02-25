package com.example.finalProject.business.concretes;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalProject.business.abstracts.ICreditAuthorizationService;
import com.example.finalProject.dataaccess.concretes.CreditAuthorizationRepository;
import com.example.finalProject.dataaccess.concretes.CreditLimitationRepository;
import com.example.finalProject.dataaccess.concretes.CustomerRepository;
import com.example.finalProject.entities.concretes.CreditAuthorization;
import com.example.finalProject.entities.concretes.CreditLimitation;
import com.example.finalProject.entities.concretes.Customer;

@Service
public class CreditAuthorizationManager implements ICreditAuthorizationService{

	@Autowired
	private CreditLimitationRepository creditLimitationRepository;
	
	@Autowired
	private CreditAuthorizationRepository creditAuthorizationRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<CreditAuthorization> getAll() {
		return creditAuthorizationRepository.findAll();
	}
	
	@Override
	public Map<String, Boolean> authorization(Customer customer) throws Exception {
		Map<String, Boolean> response = new HashMap<>();
		if(creditAuthorizationRepository.existsById(customer.getCustomerId()) == false ) {
			response.put("There is no credit enterance for this customer.", Boolean.FALSE);
			return response;
		}
		else {
			
			CreditAuthorization creditToAuthorize = creditAuthorizationRepository.findById(customer.getCustomerId())
					.orElseThrow(()->new Exception("No customer with id: " + customer.getCustomerId()));
			
			Customer customerToProcess = customerRepository.findById(customer.getCustomerId())
					.orElseThrow(()->new Exception("No customer with id: " + customer.getCustomerId()));
			
			Date creditApplicationDate = new Date();
			
			//if customer is a new customer
			if(creditToAuthorize.isNewCustomer() == true) {
				
				//maximum credit amount that new customers can get is 2000, don't give credit
				if(creditToAuthorize.getCreditWanted() > 2000) {
					customerToProcess.setCreditWanted(creditToAuthorize.getCreditWanted());
					customerToProcess.setCreditGiven(0);
					customerToProcess.setDateOfCredit(creditApplicationDate);
					customerRepository.save(customerToProcess);
					
					creditToAuthorize.setCreditGiven(0);
					creditToAuthorize.setCreditSituation("Rejected");
					creditAuthorizationRepository.save(creditToAuthorize);
					
					response.put("Credit application has been rejected. Reason: wanted credit amount is "
							+ "more than maximum credit that this customer can get.", Boolean.FALSE);
					return response;
				}
				else {
					customerToProcess.setCreditWanted(creditToAuthorize.getCreditWanted());
					customerToProcess.setCreditGiven(creditToAuthorize.getCreditWanted());
					customerToProcess.setDateOfCredit(creditApplicationDate);
					customerRepository.save(customerToProcess);
					
					creditToAuthorize.setCreditGiven(creditToAuthorize.getCreditWanted());
					creditToAuthorize.setCreditSituation("Approved");
					creditAuthorizationRepository.save(creditToAuthorize);

					response.put("Credit application has been approved.", Boolean.TRUE);
					return response;
				}
			}
			
			//if customer is already a turkcell customer
			else {
				CreditLimitation customersLimitations = creditLimitationRepository.findById(customer.getCustomerId())
						.orElseThrow(()->new Exception("No customer with id: " + customer.getCustomerId()));
				Calendar today = Calendar.getInstance();
				Calendar lastTakenCreditDate = Calendar.getInstance();
				if(customersLimitations.getLastTakenCreditDate() != null) {
					lastTakenCreditDate.setTime(customersLimitations.getLastTakenCreditDate());
					//if customer has already taken a credit in this month, reject
					
					if(today.get(Calendar.YEAR) == lastTakenCreditDate.get(Calendar.YEAR)
							&& (Math.abs(today.get(Calendar.MONTH) - lastTakenCreditDate.get(Calendar.MONTH)) <= 1)) {
						customerToProcess.setCreditWanted(creditToAuthorize.getCreditWanted());
						customerToProcess.setCreditGiven(0);
						customerToProcess.setDateOfCredit(creditApplicationDate);
						customerRepository.save(customerToProcess);

						creditToAuthorize.setCreditGiven(0);
						creditToAuthorize.setCreditSituation("Rejected");
						creditAuthorizationRepository.save(creditToAuthorize);

						response.put("Credit application has been rejected. Reason: customer has already taken credit in this month.", Boolean.FALSE);
					}
					//if total amount of credits (already taken + wanted) is more than max credit 
					//that can be taken in 1 year (50000) reject again
					else if(today.get(Calendar.YEAR) == lastTakenCreditDate.get(Calendar.YEAR)
							&& customersLimitations.getCreditTakenByNow() + customerToProcess.getCreditWanted() > 30000) {
						customerToProcess.setCreditWanted(creditToAuthorize.getCreditWanted());
						customerToProcess.setCreditGiven(0);
						customerToProcess.setDateOfCredit(creditApplicationDate);
						customerRepository.save(customerToProcess);

						creditToAuthorize.setCreditGiven(0);
						creditToAuthorize.setCreditSituation("Rejected");
						creditAuthorizationRepository.save(creditToAuthorize);

						response.put("Credit application has been rejected. Reason: cannot take more than 50000 in the same year.", Boolean.FALSE);
						
					}
					return response;
				}
					
				else if(creditToAuthorize.getCreditWanted() > customersLimitations.getMaxCreditAmount()) {
					response.put("Credit application has been rejected. Reason: wanted credit is bigger"
							+ " than maximum amount that can this customer get.", Boolean.FALSE);
					customerToProcess.setCreditWanted(creditToAuthorize.getCreditWanted());
					customerToProcess.setCreditGiven(0);
					customerToProcess.setDateOfCredit(creditApplicationDate);
					customerRepository.save(customerToProcess);

					creditToAuthorize.setCreditGiven(0);
					creditToAuthorize.setCreditSituation("Rejected");
					creditAuthorizationRepository.save(creditToAuthorize);

					return response;
				}
				
				else {
					//otherwise give credit
					response.put("Credit application has been approved.", Boolean.TRUE);
					customerToProcess.setCreditWanted(creditToAuthorize.getCreditWanted());
					customerToProcess.setCreditGiven(creditToAuthorize.getCreditWanted());
					customerToProcess.setDateOfCredit(creditApplicationDate);
					customerRepository.save(customerToProcess);

					creditToAuthorize.setCreditGiven(creditToAuthorize.getCreditWanted());
					creditToAuthorize.setCreditSituation("Approved");
					creditAuthorizationRepository.save(creditToAuthorize);
					
					//lastly update the last taken credit data in limitations table
					customersLimitations.setLastTakenCreditDate(creditApplicationDate);
					customersLimitations.setCreditTakenByNow(creditToAuthorize.getCreditWanted());
					creditLimitationRepository.save(customersLimitations);
					
					return response;
				}
			}
		}
	}

}
