package com.ojtsin.demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ojtsin.demo.domain.AccountDTO;
import com.ojtsin.demo.domain.AdminSCMDTO;
import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.service.AccountService;
import com.ojtsin.demo.service.AdminSCMService;
import com.ojtsin.demo.service.UserSCMService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AccountController {

	// 승인전
	final String approveBeforeString = "승인전";
	// 승인
	final String approveString = "승인";
	// 반려
	final String rejectString = "반려";
	// 정보제공 동의
	final String agreementY = "Y";
	
	private AccountService service;
	
	public AccountController(AccountService service) {
		this.service = service;
	}
	
	// 첫 화면. 로그인 폼 리턴
	@GetMapping("/")
    public String hello() {
        return "/views/index";
    }
	
	// 회원가입 폼 리턴
	@GetMapping("/submitform")
	public String submitform() {
		return "/views/submitform";
	}
	
	// 회원가입 유효성 검사. 사업자 번호에 해당하는 계정이 있을경우 계정 정보 리턴.  없을경우 null인 AccountDTO 객체 리턴
	@RequestMapping("/checkaccount")
	@ResponseBody
	public AccountDTO checkAccount(@RequestBody AccountDTO dto) {
		return service.submitCheck(dto);
	}
	
	// 회원가입
	@RequestMapping("/submitaccount")
	public void submitAccount(@RequestBody AccountDTO dto) {
		service.submitAccount(dto);
	}
	
	// 로그인. JSON 데이터 리턴
	@PostMapping("/loginaccount")
	@ResponseBody
	public AccountDTO loginAccount(@RequestBody AccountDTO dto, HttpServletRequest request) throws Exception {
		return service.loginAccount(dto, request);
	}
	
	// 로그인 성공시 보여주는 페이지 리턴
	@GetMapping("/loginafter")
	public String loginAfterPage() {
		return "/views/loginafter";
	}
	
	//로그인 성공시 보여주는 viewscm 페이지 리턴
	@RequestMapping(value = "/viewscm", method = RequestMethod.GET)
	public String viewSCMpage(@RequestParam(value = "scmid") String scmid, @RequestParam(value = "businessnumber") String businessnumber, Model model) {

		// 관리자 로그인시 관리자 페이지 리턴
		String admin_scmid = "0";
		String admin_businessnumber = "000-0000";
		if(admin_scmid.equals(scmid) 
				&& admin_businessnumber.equals(admin_businessnumber)) {
			service.viewAdminPage(scmid, businessnumber, model);
			return "/views/adminpage";
		}
		
		// 유저의 동의 여부가 "N" 일 경우 동의 페이지 리턴
		String agreementN = "N";
		Optional<AccountDTO> accountOptional = service.findByBusinessnumber(businessnumber);
		if(accountOptional.isPresent()) {
			AccountDTO account = accountOptional.get();
			if(agreementN.equals(account.getAgreement())) {
				model.addAttribute("BusinessNumber", businessnumber);
				model.addAttribute("ScmId", scmid);
				return "/views/agreementpage";
			}
		}
		
		// 판매 데이터를 model에 set 이후 viewscm 페이지 리턴
		service.viewScmPage(scmid, businessnumber, model);
		return "/views/viewscm";
	}

	// id에 해당하는 계정 정보. JSON 데이터로 리턴
	@PostMapping("/getaccount")
	@ResponseBody
	public AccountDTO getAccount(@RequestBody AccountDTO dto) {
		return service.getAccount(dto);
	}

	// 정보제공 동의했을때
	@PostMapping("/useragree")
	public String useragree(@RequestBody AccountDTO dto) {
		service.setAgreementToY(dto); // dto의 agreement를 "Y" 로 update
		return "/views/viewscm";
	}
	
}
