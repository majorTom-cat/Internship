package com.ojtsin.demo.controller;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.service.AdminSCMService;
import com.ojtsin.demo.service.UserSCMService;

@WebMvcTest(AdminSCMController.class)
@ExtendWith(MockitoExtension.class)
class AdminSCMControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private AdminSCMController adminSCMController;
	
	@MockBean
	private AdminSCMService adminSCMService;

	@MockBean
	private UserSCMService userSCMService;
	
	@Mock
	private UserSCMDTO userSCM;
	
	@Test
	@DisplayName("approveApplication 테스트")
	void approveApplicationTest() throws Exception {
		// given
		userSCM = UserSCMDTO.builder()
							    .bank_account("111-111111-11-111")
							    .bank_name("기업")
							    .businessNumber("123-4567")
							    .estimated_amount("8000000")
							    .name("김판매")
							    .scm("쿠팡")
							    .settlement_amount("1000000")
							    .settlement_date("2023-01-01 10:00:00")
							    .settlementNo("1")
							    .approval("승인전")
						    .build();
		
		// when
		String json = new Gson().toJson(userSCM);
		
		this.mockMvc.perform(post("/approve")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		willDoNothing().given(userSCMService).approveUserSCMInfo(userSCM);
	}
	
	@Test
	@DisplayName("rejectApplication 테스트")
	void rejectApplicationTest() throws Exception {
		// given
		userSCM = UserSCMDTO.builder()
							    .bank_account("111-111111-11-111")
							    .bank_name("기업")
							    .businessNumber("123-4567")
							    .estimated_amount("8000000")
							    .name("김판매")
							    .scm("쿠팡")
							    .settlement_amount("1000000")
							    .settlement_date("2023-01-01 10:00:00")
							    .settlementNo("1")
							    .approval("승인전")
						    .build();
		
		// when
		String json = new Gson().toJson(userSCM);
		
		this.mockMvc.perform(post("/reject")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		willDoNothing().given(userSCMService).rejectUserSCMInfo(userSCM);
	}

}
