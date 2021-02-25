package com.example.finalProject.business.abstracts;

import java.util.Map;

import com.example.finalProject.entities.concretes.CreditLimitation;

public interface ICreditLimitationService {
	CreditLimitation add(CreditLimitation creditLimitation) throws Exception;
	public Map<String, Boolean> delete(CreditLimitation creditLimitation) throws Exception;

}
