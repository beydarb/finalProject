package com.example.finalProject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="blacklist")
public class Blacklist {
	@Id
	@Column(name="customer_id")
	private String customerId;
}
