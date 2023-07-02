package com.ojtsin.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.service.AdminSCMService;
import com.ojtsin.demo.service.UserSCMService;

@Controller
@RequestMapping("/settlement_application")
public class AdminSCMController {
	
	private AdminSCMService service;
	private UserSCMService userScmService;
	
	public AdminSCMController(AdminSCMService service, UserSCMService userScmService) {
		this.service = service;
		this.userScmService = userScmService;
	}

	// 선정산 신청 승인
	@RequestMapping("/approve")
	public void approveApplication(@RequestBody UserSCMDTO userSCMDTO) {
		userScmService.approveUserSCMInfo(userSCMDTO);
	}
	
	// 선정산 신청 반려
	@RequestMapping("/reject")
	public void rejectApplication(@RequestBody UserSCMDTO userSCMDTO) {
		userScmService.rejectUserSCMInfo(userSCMDTO);
	}
}
