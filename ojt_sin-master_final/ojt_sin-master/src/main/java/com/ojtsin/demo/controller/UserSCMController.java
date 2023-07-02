package com.ojtsin.demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.service.UserSCMService;

@Controller
@RequestMapping("/settlement_application")
public class UserSCMController {
	
	private UserSCMService service;
	
	public UserSCMController(UserSCMService service) {
		this.service = service;
	}
	
//	@GetMapping("/getscm")
//    public String getscm() throws ParseException  {
//		service.getscm();
//        return "viewscm";
//    }  
	
	// 선정산 신청
	@RequestMapping("/save")
	public void saveApplication(@RequestBody UserSCMDTO userSCMDTO) {
		service.saveUserSCMInfo(userSCMDTO);
	}
	
	// 선정산 입력값 수정
	@RequestMapping("/update")
	public void updateApplication(@RequestBody UserSCMDTO userSCMDTO) {
		service.updateUserSCMInfo(userSCMDTO);
	}
	
	// 선정산 신청 취소
	@RequestMapping("/delete")
	public void deleteApplication(@RequestBody UserSCMDTO userSCMDTO) {
		service.deleteUserSCMInfo(userSCMDTO);
	}
	
}
