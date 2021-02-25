package com.example.finalProject.entities.concretes;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.finalProject.entities.abstracts.IEntity;

import lombok.Data;

@Data
@Entity
@Table(name="credit_authorization")
public class CreditAuthorization implements IEntity{
	@Id
	@Column(name="customer_id")
	private String customerId; //TCK number
	
	@Column(name="credit_wanted")
	private double creditWanted;
	
	@Column(name="credit_given")
	private double creditGiven;
	
	@Column(name="credit_situation")
	private String creditSituation;
	
	@Column(name="is_new_customer")
	private boolean isNewCustomer;
}
