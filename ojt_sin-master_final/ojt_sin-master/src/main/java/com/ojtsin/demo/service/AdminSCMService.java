package com.ojtsin.demo.service;

import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ojtsin.demo.domain.AdminSCMDTO;
import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.repository.AdminSCMRepository;
import com.ojtsin.demo.repository.UserSCMRepository;

@Service
public class AdminSCMService {
	
	private AdminSCMRepository repository;
	
	public AdminSCMService(AdminSCMRepository repository) {
		this.repository = repository;
	}

	public List<AdminSCMDTO> findAllByApproval(String approval) {
		return repository.findAllByApproval(approval);
	}

}
