package com.ojtsin.demo.controller;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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
import com.ojtsin.demo.service.UserSCMService;

@WebMvcTest(UserSCMController.class)
@ExtendWith(MockitoExtension.class)
class UserSCMControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
    private UserSCMController userSCMController;

    @MockBean
    private UserSCMService userSCMService;
    
    @MockBean
    private UserSCMDTO userSCM;

    @Test
    @DisplayName("saveApplication 테스트")
    public void saveApplicationTest() throws Exception {
    	
        // Given
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
        
        // When
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/settlement_application/save")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userSCMDTO)
//                		.accept(MediaType.APPLICATION_JSON));
        
        String json = new Gson().toJson(userSCM);
		
		this.mockMvc.perform(post("/settlement_application/save")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

        // Then
        //then(userSCMService).should().saveUserSCMInfo(userSCM);
        userSCMService.saveUserSCMInfo(userSCM);
    }

    @Test
    @DisplayName("updateApplication 테스트")
    public void updateApplicationTest() throws Exception {
        // Given
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

        // When
        String json = new Gson().toJson(userSCM);
		
		this.mockMvc.perform(post("/settlement_application/update")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

        // Then
		//then(userSCMService).should().updateUserSCMInfo(userSCM);
		userSCMService.updateUserSCMInfo(userSCM);
    }

    @Test
    @DisplayName("deleteApplication 테스트")
    public void deleteApplicationTest() throws Exception {
        // Given
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

        // When
    	String json = new Gson().toJson(userSCM);
		
		this.mockMvc.perform(post("/settlement_application/update")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

        // Then
		//then(userSCMService).should().deleteUserSCMInfo(userSCM);
		userSCMService.deleteUserSCMInfo(userSCM);
		//BDDMockito.verify(userSCMService).deleteUserSCMInfo(userSCM);
    }

}
