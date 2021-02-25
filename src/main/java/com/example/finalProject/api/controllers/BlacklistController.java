package com.example.finalProject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalProject.business.abstracts.IBlacklistService;
import com.example.finalProject.entities.concretes.Blacklist;

@RestController
@RequestMapping("/api/v2")
public class BlacklistController {
	@Autowired
	IBlacklistService blacklistService;
	
	@PostMapping("/blacklists")
	public Blacklist add(@RequestBody Blacklist blacklist) {
		return blacklistService.add(blacklist);
	}
}
