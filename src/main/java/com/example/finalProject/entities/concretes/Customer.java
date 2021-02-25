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
@Table(name="customers")
public class Customer implements IEntity{
	@Id
	@Column(name="customer_id", unique = true)
	private String customerId; //TCK number
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="customer_lastname")
	private String lastName;
	
	@Column(name="phone_number")
	private String phoneNumber; //MSISDN
	
	@Column(name="address")
	private String address;

	@Column(name="customer_type")
	private String customerType;
	
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	@Column(name="date_of_subscription")
	private Date dateOfSubscription;
	
	@Column(name="date_of_credit")
	private Date dateOfCredit;
	
	@Column(name="credit_wanted")
	private double creditWanted;
	
	@Column(name="credit_given") 
	private double creditGiven;

}
