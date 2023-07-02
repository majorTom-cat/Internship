package com.ojtsin.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.repository.AccountRepository;
import com.ojtsin.demo.repository.AdminSCMRepository;
import com.ojtsin.demo.repository.ScmsRepository;
import com.ojtsin.demo.repository.UserSCMRepository;

@RestClientTest(AccountService.class)
@ExtendWith(MockitoExtension.class)
public class AccountServiceRestTest {

	@MockBean
    private AccountRepository accountRepository;
	@MockBean
	private ScmsRepository scmsRepostory;
	@MockBean
	private UserSCMRepository userScmRepository;
	@MockBean
	private AdminSCMRepository adminScmRepository;
	@MockBean
	private UserSCMService userScmService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private AccountService accountService;

    @Test
    void getMember() {
        server.expect(requestTo("http://222.122.235.25:3000/scm/info/1/123-4567"))
                .andRespond(withSuccess(new ClassPathResource("/scm1.json", getClass()), MediaType.APPLICATION_JSON));
        
//        URI uri = UriComponentsBuilder
//                .fromUriString("http://222.122.235.25:3000")
//                .path("/scm/info/" + "1/123-4567")
//                .encode()
//                .build()
//                .toUri();
//        
//        RestTemplate restTemplate = new RestTemplate();
//		List<SCMinfoDTO> resultSCM  = Arrays.asList(restTemplate.getForObject(uri, SCMinfoDTO[].class));

        UserSCMDTO userSCM = UserSCMDTO.builder()
						        		.bank_account("111-111111-11-111")
									    .bank_name("기업")
									    .businessNumber("123-4567")
									    .estimated_amount("8000000")
									    .name("김판매")
									    .scm("coupang")
									    .settlement_amount("1000000")
									    .settlement_date("2023-01-01 10:00:00")
									    .settlementNo("1")
									    .approval("승인전")
									    .build();
        
        //assertThat(resultSCM.get(0).getScm()).isEqualTo(userSCM.getScm());
    }
}
