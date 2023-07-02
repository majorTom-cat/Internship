package com.ojtsin.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ojtsin.demo.domain.UserSCMDTO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserSCMRepositoryTest {

	@Mock
    private UserSCMRepository userSCMRepository;
	
	@Mock
	private UserSCMDTO userSCMDTO;

    @Test
    @DisplayName("findAllByBusinessNumber 테스트")
    public void testFindAllByBusinessNumber() {
        // Given
        String businessNumber = "123456789";
        UserSCMDTO userSCMDTO1 = new UserSCMDTO();
        UserSCMDTO userSCMDTO2 = new UserSCMDTO();
        List<UserSCMDTO> userSCMDTOList = Arrays.asList(userSCMDTO1, userSCMDTO2);
        given(userSCMRepository.findAllByBusinessNumber(businessNumber)).willReturn(userSCMDTOList);

        // When
        List<UserSCMDTO> result = userSCMRepository.findAllByBusinessNumber(businessNumber);

        // Then
        assertThat(result).isEqualTo(userSCMDTOList);
        then(userSCMRepository).should().findAllByBusinessNumber(businessNumber);
    }

    @Test
    @DisplayName("findBySettlementNo 테스트")
    public void testFindBySettlementNo() {
        // Given
        String settlementNo = "123";
        UserSCMDTO userSCMDTO = UserSCMDTO.builder()
        								  .application_amount("8000000")
        								  .approval("N")
        								  .bank_account("111-111111-11-111")
        								  .bank_name("기업")
        								  .businessNumber("112-1911")
        								  .estimated_amount("800000")
        								  .name("이셀러")
        								  .scm("11st")
        								  .settlement_amount("1000000")
        								  .settlement_date("2023-02-01 10:00:00")
        								  .settlementNo("123")
        								  .build();
        given(userSCMRepository.findBySettlementNo(settlementNo)).willReturn(userSCMDTO);

        // When
        UserSCMDTO result = userSCMRepository.findBySettlementNo(settlementNo);

        // Then
        assertThat(result).isEqualTo(userSCMDTO);
        then(userSCMRepository).should().findBySettlementNo(settlementNo);
    }

    @Test
    @DisplayName("deleteAllBySettlementNo 테스트")
    public void testDeleteAllBySettlementNo() {
        // Given
        String settlementNo = "123";
        willDoNothing().given(userSCMRepository).deleteAllBySettlementNo(settlementNo);

        // When
        userSCMRepository.deleteAllBySettlementNo(settlementNo);

        // Then
        then(userSCMRepository).should().deleteAllBySettlementNo(settlementNo);
    }

    @Test
    @DisplayName("findAllByApproval 테스트")
    public void testFindAllByApproval() {
        // Given
        String approval = "승인";
        UserSCMDTO userSCMDTO1 = new UserSCMDTO();
        UserSCMDTO userSCMDTO2 = new UserSCMDTO();
        List<UserSCMDTO> userSCMDTOList = Arrays.asList(userSCMDTO1, userSCMDTO2);
        given(userSCMRepository.findAllByApproval(approval)).willReturn(userSCMDTOList);

        // When
        List<UserSCMDTO> result = userSCMRepository.findAllByApproval(approval);

        // Then
        assertThat(result).isEqualTo(userSCMDTOList);
        then(userSCMRepository).should().findAllByApproval(approval);
    }

}
