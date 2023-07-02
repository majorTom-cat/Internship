package com.ojtsin.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.ojtsin.demo.domain.AccountDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.service.AccountService;
import com.ojtsin.demo.service.AdminSCMService;
import com.ojtsin.demo.service.UserSCMService;

@WebMvcTest(AccountController.class)
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
	@Autowired
	private MockMvc mockMvc;
	 
	@MockBean
	private AccountService accountService;

	@MockBean
	private UserSCMService userSCMService;
	
	@MockBean
	private AdminSCMService adminSCMService;

	private static MediaType HTML_CONTENT_TYPE = new MediaType(MediaType.TEXT_HTML.getType(), MediaType.TEXT_HTML.getSubtype(), Charset.forName("utf8"));
	private static MediaType JSON_CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
 
	@Test
	@DisplayName("처음 접속시 로그인 폼 리턴하는지 확인")
	void loginformTest() throws Exception {
		
		this.mockMvc.perform(get("/"))
					.andDo(print())                                    
			        .andExpect(status().isOk())                           
					.andExpect(view().name("/views/index"));
	}
	
	@Test
	@DisplayName("Post 회원 가입 컨트롤러가 회원가입 뷰를 리턴하는지 확인")
	void submitformTest() throws Exception {
		
		this.mockMvc.perform(get("/submitform"))
					.andDo(print())                                    
			        .andExpect(status().isOk())                           
					.andExpect(view().name("/views/submitform"));
	}
	
	@Test
	@DisplayName("로그인성 공시 loginafter 페이지를 리턴하는지 확인")
	void loginAfterPageTest() throws Exception {
		
		this.mockMvc.perform(get("/loginafter"))
					.andDo(print())                                    
			        .andExpect(status().isOk())                           
					.andExpect(view().name("/views/loginafter"));
	}

	@Test
	@DisplayName("AccountDTO builder 객체 생성 테스트")
	void accountDTOTest() {
		// given
		LocalDateTime localDateTime = LocalDateTime.now();
		String parsedLocalDateTimeNow = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		AccountDTO accountDTO = AccountDTO.builder()
										  .agreement("N")
										  .bank_account("111-111111-11-111")
										  .bank_name("기업")
										  .businessnumber("111-1111")
										  .name("홍길동")
										  .password("!1qwerqwer")
										  .register_date(parsedLocalDateTimeNow)
										  .scmid("10")
										  .build();
		// when, then
		assertEquals(accountDTO.getAgreement(), "N");
		assertEquals(accountDTO.getBank_account(), "111-111111-11-111");
		assertEquals(accountDTO.getBank_name(), "기업");
		assertEquals(accountDTO.getBusinessnumber(), "111-1111");
		assertEquals(accountDTO.getName(), "홍길동");
		assertEquals(accountDTO.getPassword(), "!1qwerqwer");
		assertEquals(accountDTO.getRegister_date(), parsedLocalDateTimeNow);
		assertEquals(accountDTO.getScmid(), "10");
	}
	
	@Test
	@DisplayName("관리자 로그인시 컨트롤러가 관리자 페이지 뷰를 리턴하는지 확인")
	void viewScmAdminPageTest() throws Exception {
		
		// given
		LocalDateTime localDateTime = LocalDateTime.now();
		String parsedLocalDateTimeNow = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		AccountDTO adminAccountDTO = AccountDTO.builder()
											   .agreement("N")
											   .bank_account("000-000000-00-000")
											   .bank_name("하나")
											   .businessnumber("000-0000")
											   .name("관리자")
											   .password("!1qwerqwer")
											   .register_date(parsedLocalDateTimeNow)
											   .scmid("0")
											   .build();
		
		// when then
		String admin_scmid = "0";
		String admin_businessnumber = "000-0000";
		if(admin_scmid.equals(adminAccountDTO.getScmid()) 
				&& admin_businessnumber.equals(adminAccountDTO.getBusinessnumber())) {
			this.mockMvc.perform(get("/viewscm")
							.param("scmid", adminAccountDTO.getScmid())
							.param("businessnumber", adminAccountDTO.getBusinessnumber()))
						.andDo(print())
				        .andExpect(status().isOk())
						.andExpect(view().name("/views/adminpage"));
		}
	}
	
	@Test
	@DisplayName("계정의 동의 여부가 'N' 일 경우 동의 페이지를 리턴하는지 확인")
	// 유저의 동의 여부가 "N" 일 경우 동의 페이지 리턴
	void agreementPageTest() throws Exception {
		
		String agreementN = "N";
		
		// given
		LocalDateTime localDateTime = LocalDateTime.now();
		String parsedLocalDateTimeNow = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		AccountDTO accountDTO = AccountDTO.builder()
										  .agreement("N")
										  .bank_account("111-111111-11-111")
										  .bank_name("기업")
										  .businessnumber("111-1111")
										  .name("홍길동")
										  .password("!1qwerqwer")
										  .register_date(parsedLocalDateTimeNow)
										  .scmid("10")
										  .build();
		Optional<AccountDTO> accountOptional = accountService.findByBusinessnumber(accountDTO.getBusinessnumber());
		
		// when then
		if(accountOptional.isPresent()) {
			AccountDTO account = accountOptional.get();
			if(agreementN.equals(account.getAgreement())) {
				
				this.mockMvc.perform(get("/viewscm")
						.param("scmid", accountDTO.getScmid())
						.param("businessnumber", accountDTO.getBusinessnumber()))
					.andDo(print())
			        .andExpect(status().isOk())
					.andExpect(view().name("/views/agreementpage"));
			}
		}
	}
	
	@Test
	@DisplayName("가상 크롤링 데이터 (scmid=1) 확인")
	void crawlingTest1() {
		// 가상 크롤링 데이터 주소
		URI uri = UriComponentsBuilder
                .fromUriString("http://222.122.235.25:3000")
                .path("/scm/info/1/123-4567")
                .encode()
                .build()
                .toUri();
		
		RestTemplate restTemplate = new RestTemplate();
		List<SCMinfoDTO> resultSCM  = Arrays.asList(restTemplate.getForObject(uri, SCMinfoDTO[].class));
		
		assertEquals(resultSCM.size(), 3);
	}
	
	@Test
	@DisplayName("가상 크롤링 데이터 (scmid=2) 확인")
	void crawlingTest2() {
		// 가상 크롤링 데이터 주소
		URI uri = UriComponentsBuilder
                .fromUriString("http://222.122.235.25:3000")
                .path("/scm/info/2/112-1911")
                .encode()
                .build()
                .toUri();
		
		RestTemplate restTemplate = new RestTemplate();
		List<SCMinfoDTO> resultSCM  = Arrays.asList(restTemplate.getForObject(uri, SCMinfoDTO[].class));
		
		assertEquals(resultSCM.size(), 2);
	}
	
	@Test
	@DisplayName("유저가 정보제공 동의했을 경우 정산 데이터 뷰 페이지를 리턴하는지 확인")
	void userAgreeThenPageTest() throws Exception {
		
		final String AGREEMENT_N = "N";
		final String AGREEMENT_Y = "Y";
		
		// given
		LocalDateTime localDateTime = LocalDateTime.now();
		String parsedLocalDateTimeNow = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		AccountDTO accountDTO = AccountDTO.builder()
										  .agreement(AGREEMENT_N) // 처음 가입시 동의 여부 "N"
										  .bank_account("111-111111-11-111")
										  .bank_name("기업")
										  .businessnumber("111-1111")
										  .name("홍길동")
										  .password("!1qwerqwer")
										  .register_date(parsedLocalDateTimeNow)
										  .scmid("10")
										  .build();

		// when then
		accountService.setAgreementToY(accountDTO);
		accountDTO.setAgreement(AGREEMENT_Y); // 정보제공 동의시 "N" -> "Y"
		
		assertEquals(AGREEMENT_Y, accountDTO.getAgreement());
		
		String json = new Gson().toJson(accountDTO);
		
		this.mockMvc.perform(post("/useragree")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
	        .andExpect(status().isOk())
			.andExpect(view().name("/views/viewscm"));
	}
}
