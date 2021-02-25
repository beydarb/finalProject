package com.example.finalProject.dataaccess.concretes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.finalProject.entities.concretes.CreditAuthorization;


public interface CreditAuthorizationRepository extends JpaRepository<CreditAuthorization, String>{

}
