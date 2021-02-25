package com.example.finalProject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalProject.business.abstracts.IBlacklistService;
import com.example.finalProject.dataaccess.concretes.BlacklistRepository;
import com.example.finalProject.entities.concretes.Blacklist;

@Service
public class BlacklistManager implements IBlacklistService {

	@Autowired
	private BlacklistRepository blacklistRepository;
	
	@Override
	public Blacklist add(Blacklist blacklist) {
		return blacklistRepository.save(blacklist);
	}

}
