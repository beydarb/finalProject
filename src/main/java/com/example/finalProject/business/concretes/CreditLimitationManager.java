package com.example.finalProject.business.concretes;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalProject.business.abstracts.ICreditLimitationService;
import com.example.finalProject.dataaccess.concretes.CreditLimitationRepository;
import com.example.finalProject.dataaccess.concretes.CustomerRepository;
import com.example.finalProject.entities.concretes.CreditLimitation;
import com.example.finalProject.entities.concretes.Customer;

@Service
public class CreditLimitationManager implements ICreditLimitationService{
	
	@Autowired
	private CreditLimitationRepository creditLimitationRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public CreditLimitation add(CreditLimitation creditLimitation) throws Exception{
		Customer customer= customerRepository.findById(creditLimitation.getCustomerId())
				.orElseThrow(()->new Exception("No credit limitation with customer id: " + creditLimitation.getCustomerId()));
		
		Calendar today = Calendar.getInstance();
		Calendar membershipDate = Calendar.getInstance();
		membershipDate.setTime(customer.getDateOfSubscription());
		int yearsOfMembership = today.get(Calendar.YEAR) - membershipDate.get(Calendar.YEAR);
		creditLimitation.setCustomerId(customer.getCustomerId());
		creditLimitation.setYearsOfMembership(yearsOfMembership);
		
		//determine max credit amount of customer
		if(creditLimitation.getMaxCreditAmount() == 0) {
			double currentMaxCreditOfCustomer = creditLimitation.getMaxCreditAmount();
			if(creditLimitation.getPaymentLoyalty() == -1) {
				//if it is not a good customer in terms of payment loyalty, give a small credit amount
				creditLimitation.setMaxCreditAmount(currentMaxCreditOfCustomer + 2000);
			}
			else if(creditLimitation.getPaymentLoyalty() == 1) {
				//otherwise give extra credits
				creditLimitation.setMaxCreditAmount(currentMaxCreditOfCustomer + 10000);
			}
			else {
				//if it is not a postpaid customer
				creditLimitation.setMaxCreditAmount(currentMaxCreditOfCustomer + 2500);
			}
			creditLimitationRepository.save(creditLimitation);
			
			//update current maximum credit after checking loyalty
			//then check for years of membership
			currentMaxCreditOfCustomer = creditLimitation.getMaxCreditAmount();
			if(creditLimitation.getYearsOfMembership() <= 1) {
				//if it is a new customer, give small amount
				creditLimitation.setMaxCreditAmount(currentMaxCreditOfCustomer + 1500);
			}
			else if( 1 < creditLimitation.getYearsOfMembership() && creditLimitation.getYearsOfMembership() <= 5) {
				creditLimitation.setMaxCreditAmount(currentMaxCreditOfCustomer + 5000);
			}
			else if(creditLimitation.getYearsOfMembership() > 5) {
				creditLimitation.setMaxCreditAmount(currentMaxCreditOfCustomer + 10000);
			}
		}
		return creditLimitationRepository.save(creditLimitation);
	}

	@Override
	public Map<String, Boolean> delete(CreditLimitation creditLimitation) throws Exception {
			CreditLimitation creditLimitToDelete = creditLimitationRepository.findById(creditLimitation.getCustomerId())
					.orElseThrow(()->new Exception("No credit limitation with customer id: " + creditLimitation.getCustomerId()));
			
			creditLimitationRepository.delete(creditLimitToDelete);
			Map<String, Boolean> response = new HashMap<>();
			response.put("Credit limitation has been deleted", Boolean.TRUE);
			return response;
	}
}
