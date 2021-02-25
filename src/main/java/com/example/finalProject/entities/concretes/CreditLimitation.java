package com.example.finalProject.entities.concretes;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.finalProject.entities.abstracts.IEntity;

import lombok.Data;

@Data
@Entity
@Table(name="credit_limitations")
public class CreditLimitation implements IEntity{
	@Id
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="credit_taken_by_now")
	private double creditTakenByNow;
	
	@Column(name="maximum_credit_amount")
	private double maxCreditAmount;
	
	@Column(name="years_of_membership")
	private int yearsOfMembership;
	
	@Column(name="payment_loyalt")
	private int paymentLoyalty;
	
	@Column(name="legally_pursuit")
	private boolean legallyPursuit;
	
	@Column(name="last_taken_credit_date")
	private Date lastTakenCreditDate;

}
