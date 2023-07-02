package com.ojtsin.demo.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.ojtsin.demo.domain.AdminSCMDTO;
import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.dto.SellerInfoDTO;
import com.ojtsin.demo.repository.AdminSCMRepository;
import com.ojtsin.demo.repository.UserSCMRepository;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserSCMServiceTest {

	@Mock
    private UserSCMRepository repository;

    @Mock
    private AdminSCMRepository adminSCMRepository;
      
    @Mock
    UserSCMDTO userSCM;
    
    @Mock
    AdminSCMDTO adminSCM;

    @InjectMocks
    private UserSCMService userSCMService;

    @Test
    @DisplayName("checkUserSCMInfo 테스트")
    @Transactional
    public void testCheckUserSCMInfo() {
        // Given
    	String expectedEstimatedAmount = "800000";
    	
    	SCMinfoDTO scmInfoDTO = new SCMinfoDTO();
        scmInfoDTO.setSettlement_amount("1000000");
        scmInfoDTO.setScm("11st");
        scmInfoDTO.setSettlement_date("2023-01-01 10:00:00");
        scmInfoDTO.setSettlement_no("123");
        
        SellerInfoDTO sellerInfo = new SellerInfoDTO();
        sellerInfo.setBank_account("111-111111-11-111");
        sellerInfo.setBank_name("기업");
        sellerInfo.setBusiness_number("123-4567");
        sellerInfo.setName("홍길동");
        
        scmInfoDTO.setSeller_info(sellerInfo);

        userSCM = UserSCMDTO.builder()
							    .bank_account(scmInfoDTO.getSeller_info().getBank_account())
							    .bank_name(scmInfoDTO.getSeller_info().getBank_name())
							    .businessNumber(scmInfoDTO.getSeller_info().getBusiness_number())
							    .estimated_amount(expectedEstimatedAmount)
							    .name(scmInfoDTO.getSeller_info().getName())
							    .scm(scmInfoDTO.getScm())
							    .settlement_amount(scmInfoDTO.getSettlement_amount())
							    .settlement_date(scmInfoDTO.getSettlement_date())
							    .settlementNo(scmInfoDTO.getSettlement_no())
							    .approval("승인전")
						    .build();

        // When
        given(userSCMService.checkUserSCMInfo(scmInfoDTO)).willReturn(userSCM);
        
        UserSCMDTO result = userSCMService.checkUserSCMInfo(scmInfoDTO);

        // Then
        assertThat(result.getBank_account()).isEqualTo("111-111111-11-111");
        assertThat(result.getBank_name()).isEqualTo("기업");
        assertThat(result.getBusinessNumber()).isEqualTo("123-4567");
        assertThat(result.getEstimated_amount()).isEqualTo(expectedEstimatedAmount);
        assertThat(result.getName()).isEqualTo("홍길동");
        assertThat(result.getScm()).isEqualTo("11st");
        assertThat(result.getSettlement_amount()).isEqualTo("1000000");
        assertThat(result.getSettlement_date()).isEqualTo("2023-01-01 10:00:00");
        assertThat(result.getSettlementNo()).isEqualTo("123");
        assertThat(result.getApproval()).isEqualTo("승인전");
    }

    @Test
    @DisplayName("saveUserSCMInfo 테스트")
    public void testSaveUserSCMInfo() {
        // Given
        UserSCMDTO userSCMDTO = new UserSCMDTO();

        // When
        userSCMService.saveUserSCMInfo(userSCMDTO);

        // Then
        BDDMockito.verify(repository).save(userSCMDTO);
    }

    @Test
    @DisplayName("findAllByBusiness_number 테스트")
    public void testFindAllByBusinessNumber() {
        // Given
        String businessNumber = "1234567890";
        List<UserSCMDTO> expectedUserSCMList = new ArrayList<>();
        expectedUserSCMList.add(new UserSCMDTO());
        expectedUserSCMList.add(new UserSCMDTO());
        expectedUserSCMList.add(new UserSCMDTO());

        given(repository.findAllByBusinessNumber(businessNumber)).willReturn(expectedUserSCMList);

        // When
        List<UserSCMDTO> result = userSCMService.findAllByBusiness_number(businessNumber);

        // Then
        assertThat(result).isEqualTo(expectedUserSCMList);
    }

    @Test
    @DisplayName("updateUserSCMInfo 테스트")
    public void testUpdateUserSCMInfo() {
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
    	
        given(repository.findBySettlementNo(userSCM.getSettlementNo())).willReturn(userSCM);

        String applicationAmount = "200000";
        userSCM.setApplication_amount(applicationAmount);

        // When
        userSCMService.updateUserSCMInfo(userSCM);

        // Then
        assertThat(userSCM.getApplication_amount()).isEqualTo(applicationAmount);
    }

    @Test
    @DisplayName("deleteUserSCMInfo 테스트")
    public void testDeleteUserSCMInfo() {
        // Given
        UserSCMDTO userSCMDTO = new UserSCMDTO();
        userSCMDTO.setSettlementNo("123");

        // When
        userSCMService.deleteUserSCMInfo(userSCMDTO);

        // Then
        BDDMockito.verify(repository).deleteAllBySettlementNo("123");
        BDDMockito.verify(adminSCMRepository).deleteAllBySettlementNo("123");
    }

    @Test
    @DisplayName("findAll 테스트")
    public void testFindAll() {
        // Given
        List<UserSCMDTO> expectedUserSCMList = new ArrayList<>();
        expectedUserSCMList.add(new UserSCMDTO());
        expectedUserSCMList.add(new UserSCMDTO());
        expectedUserSCMList.add(new UserSCMDTO());

        given(repository.findAll()).willReturn(expectedUserSCMList);

        // When
        List<UserSCMDTO> result = userSCMService.findAll();

        // Then
        assertThat(result).isEqualTo(expectedUserSCMList);
    }

    @Test
    @DisplayName("approveUserSCMInfo 테스트")
    public void testApproveUserSCMInfo() {
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
    	
    	userSCM.setApproval("승인");
    	
    	adminSCM = AdminSCMDTO.builder()
							      .bank_account(userSCM.getBank_account())
							      .bank_name(userSCM.getBank_name())
							      .businessNumber(userSCM.getBusinessNumber())
							      .estimated_amount(userSCM.getEstimated_amount())
							      .name(userSCM.getName())
							      .scm(userSCM.getScm())
							      .settlement_amount(userSCM.getSettlement_amount())
							      .settlement_date(userSCM.getSettlement_date())
							      .settlementNo(userSCM.getSettlementNo())
							      .approval(userSCM.getApproval())
							      .application_amount(userSCM.getApplication_amount())
						      .build();

        given(repository.findBySettlementNo("1")).willReturn(userSCM);
        given(adminSCMRepository.save(adminSCM)).willReturn(adminSCM);

        // When
        UserSCMDTO tempuser = repository.findBySettlementNo("1");
        AdminSCMDTO tempadmin = adminSCMRepository.save(adminSCM);

        // Then
        assertThat(tempuser.getApproval()).isEqualTo(tempadmin.getApproval());
    }

    @Test
    @DisplayName("rejectUserSCMInfo 테스트")
    public void testRejectUserSCMInfo() {
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
    	
    	userSCM.setApproval("반려");
    	
    	adminSCM = AdminSCMDTO.builder()
							      .bank_account(userSCM.getBank_account())
							      .bank_name(userSCM.getBank_name())
							      .businessNumber(userSCM.getBusinessNumber())
							      .estimated_amount(userSCM.getEstimated_amount())
							      .name(userSCM.getName())
							      .scm(userSCM.getScm())
							      .settlement_amount(userSCM.getSettlement_amount())
							      .settlement_date(userSCM.getSettlement_date())
							      .settlementNo(userSCM.getSettlementNo())
							      .approval(userSCM.getApproval())
							      .application_amount(userSCM.getApplication_amount())
						      .build();

        given(repository.findBySettlementNo("1")).willReturn(userSCM);
        given(adminSCMRepository.save(adminSCM)).willReturn(adminSCM);

        // When
        UserSCMDTO tempuser = repository.findBySettlementNo("1");
        AdminSCMDTO tempadmin = adminSCMRepository.save(adminSCM);

        // Then
        assertThat(tempuser.getApproval()).isEqualTo(tempadmin.getApproval());
    }
}
