package com.ojtsin.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.ojtsin.demo.domain.AccountDTO;
import com.ojtsin.demo.domain.AdminSCMDTO;
import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.repository.AccountRepository;
import com.ojtsin.demo.repository.AdminSCMRepository;
import com.ojtsin.demo.repository.ScmsRepository;
import com.ojtsin.demo.repository.UserSCMRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
    private AccountRepository accountRepository;
    
    @Mock
    private ScmsRepository scmsRepository;
    
    @Mock
    private UserSCMRepository userSCMRepository;
    
    @Mock
    private AdminSCMRepository adminSCMRepository;
    
    @Mock
    private UserSCMService userSCMService;
    
    @InjectMocks
    private AccountService accountService;
	
	@Test
	@DisplayName("회원가입시 중복 계정인 경우 테스트")
	// "계정 생성시 중복계정일 경우 반환하는 Optional객체의 get()을 해 얻은 객체의 사업자 번호가 null이 아님"
	void submitServiceTest() {
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
		
	    //Mockito.when(accountService.findByBusinessnumber("111-111")).thenReturn(Optional.of(accountDTO));
		// Mockito.when(가짜 객체의 로직 실행).thenReturn(실행되면 이것을 반환한다.)
	    given(accountService.findByBusinessnumber("111-111")).willReturn(Optional.of(accountDTO));
	 
	    // when then
	    assertEquals(accountService.findByBusinessnumber("111-111").get().getBusinessnumber(),
	    				accountDTO.getBusinessnumber());
	}

	@Test
	@DisplayName("회원가입 테스트")
	void submitAccountServiceTest() {
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
		
		// when
		given(accountRepository.save(accountDTO)).willReturn(accountDTO);
		
		// then
		assertThat(accountRepository.save(accountDTO))
			.isEqualTo(accountDTO);
	}
	
	//

    @Test
    @DisplayName("회원가입시 중복 계정이 아닌경우 테스트")
    public void testSubmitCheck_AccountDoesNotExist() {
        // Given
        AccountDTO dto = new AccountDTO();
        dto.setBusinessnumber("123456789");

        given(accountRepository.findByBusinessnumber(dto.getBusinessnumber())).willReturn(Optional.empty());

        // When
        AccountDTO result = accountService.submitCheck(dto);

        // Then
        assertThat(result.getBusinessnumber()).isEqualTo(new AccountDTO().getBusinessnumber());
    }

    @Test
    @DisplayName("로그인 테스트")
    public void testLoginAccount_AccountExists() {
        // Given
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

        given(accountRepository.findByBusinessnumberAndPassword(accountDTO.getBusinessnumber(), accountDTO.getPassword())).willReturn(Optional.of(accountDTO));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        given(request.getSession()).willReturn(session);

        // When
        AccountDTO result = accountService.loginAccount(accountDTO, request);

        // Then
        then(session).should().setAttribute("business_number", "111-1111");
        then(session).should().setAttribute("scm_id", "10");
        assertThat(result).isEqualTo(accountDTO);
    }

    @Test
    @DisplayName("로그인시 계정이 존재하지 않는 경우 테스트")
    public void testLoginAccount_AccountDoesNotExist() {
        // Given
    	LocalDateTime localDateTime = LocalDateTime.now();
		String parsedLocalDateTimeNow = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	
    	AccountDTO dto = AccountDTO.builder()
								  .agreement("N")
								  .bank_account("111-111111-11-111")
								  .bank_name("기업")
								  .businessnumber("111-1111")
								  .name("홍길동")
								  .password("!1qwerqwer")
								  .register_date(parsedLocalDateTimeNow)
								  .scmid("10")
								  .build();

        given(accountRepository.findByBusinessnumberAndPassword(dto.getBusinessnumber(), dto.getPassword())).willReturn(Optional.empty());

        HttpServletRequest request = mock(HttpServletRequest.class);

        // When
        AccountDTO result = accountService.loginAccount(dto, request);

        // Then
        assertThat(result.getBusinessnumber()).isEqualTo(new AccountDTO().getBusinessnumber());
    }

    @Test
    @DisplayName("정보제공 동의 여부 update 테스트")
    public void testSetAgreementToY() {
        // Given
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
    	
    	// 정보제공 동의
    	final String agreementY = "Y";
        accountDTO.setAgreement(agreementY);
    	
        given(accountRepository.findByBusinessnumberAndScmid(accountDTO.getBusinessnumber(), accountDTO.getScmid())).willReturn(accountDTO);

        // When
        accountService.setAgreementToY(accountDTO);

        // Then
        assertThat(accountDTO.getAgreement()).isEqualTo(agreementY);
    }

    @Test
    @DisplayName("viewAdminPage(관리자 페이지 리턴) 테스트")
    public void testViewAdminPage() {
        // Given
        String scmid = "1";
        String businessnumber = "123456789";
        Model model = mock(Model.class);

        List<UserSCMDTO> savedScmInfoList = Arrays.asList(new UserSCMDTO(), new UserSCMDTO());
        List<AdminSCMDTO> approvedScmInfoList = Arrays.asList(new AdminSCMDTO());
        List<AdminSCMDTO> rejectedScmInfoList = Arrays.asList(new AdminSCMDTO());

        given(userSCMRepository.findAllByApproval("승인전")).willReturn(savedScmInfoList);
        given(adminSCMRepository.findAllByApproval("승인")).willReturn(approvedScmInfoList);
        given(adminSCMRepository.findAllByApproval("반려")).willReturn(rejectedScmInfoList);

        // When
        accountService.viewAdminPage(scmid, businessnumber, model);

        // Then
        then(model).should().addAttribute("ApplicationSCMinfoList", savedScmInfoList);
        then(model).should().addAttribute("ApprovedSCMinfoList", approvedScmInfoList);
        then(model).should().addAttribute("RejectedSCMinfoList", rejectedScmInfoList);
    }

    @Mock
    private Model model;
    
    @Test
    @DisplayName("크롤링 데이터 페이지 리턴. 신청한 데이터 없을때 테스트")
    public void testViewScmPage_SavedScmInfoListIsEmpty() {
        // Given
        String scmid = "1";
        String businessnumber = "123456789";
        //Model model = mock(Model.class);

        given(userSCMRepository.findAllByBusinessNumber(businessnumber)).willReturn(new ArrayList<>());

        // When
        List<UserSCMDTO> resultSCM = userSCMRepository.findAllByBusinessNumber(businessnumber);

        // Then
        then(model).should().addAttribute("SCMinfoList", anyList());
        then(model).should().addAttribute("ApplicationSCMinfoList", anyList());
    }

//    @Test
//    @DisplayName("크롤링 데이터 페이지 리턴. 신청한 데이터 있을때 테스트")
//    public void testViewScmPage_SavedScmInfoListIsNotEmpty() {
//        // Given
//        String scmid = "1";
//        String businessnumber = "123456789";
//        Model model = mock(Model.class);
//
//        List<UserSCMDTO> savedScmInfoList = Arrays.asList(new UserSCMDTO(), new UserSCMDTO());
//        given(userSCMRepository.findAllByBusinessNumber(anyString())).willReturn(savedScmInfoList);
//
//        RestTemplate restTemplate = mock(RestTemplate.class);
//        URI uri = URI.create("http://222.122.235.25:3000/scm/info/1/123456789");
//        SCMinfoDTO[] scmInfoArray = {new SCMinfoDTO()};
//        given(restTemplate.getForObject(uri, SCMinfoDTO[].class)).willReturn(scmInfoArray);
//
//        AccountService accountServiceSpy = spy(accountService);
//        given(accountServiceSpy.getRestTemplate()).willReturn(restTemplate);
//
//        // When
//        accountServiceSpy.viewScmPage(scmid, businessnumber, model);
//
//        // Then
//        then(model).should().addAttribute("SCMinfoList", Arrays.asList(scmInfoArray));
//        then(model).should().addAttribute("ApplicationSCMinfoList", savedScmInfoList);
//    }
	
}
