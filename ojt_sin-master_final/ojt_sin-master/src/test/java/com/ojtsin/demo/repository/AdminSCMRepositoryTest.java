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

import com.ojtsin.demo.domain.AdminSCMDTO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminSCMRepositoryTest {

	@Mock
    private AdminSCMRepository adminSCMRepository;

    @Test
    @DisplayName("findAllByApproval 테스트")
    // 승인된 신청건 데이터만 리턴하는것
    public void testFindAllByApproval() {
        // Given
        String approval = "승인";
        AdminSCMDTO adminSCMDTO1 = new AdminSCMDTO();
        AdminSCMDTO adminSCMDTO2 = new AdminSCMDTO();
        List<AdminSCMDTO> adminSCMDTOList = Arrays.asList(adminSCMDTO1, adminSCMDTO2);
        given(adminSCMRepository.findAllByApproval(approval)).willReturn(adminSCMDTOList);

        // When
        List<AdminSCMDTO> result = adminSCMRepository.findAllByApproval(approval);

        // Then
        assertThat(result).isEqualTo(adminSCMDTOList);
        then(adminSCMRepository).should().findAllByApproval(approval);
    }

    @Test
    @DisplayName("deleteBySettlementNo 테스트")
    public void testDeleteBySettlementNo() {
        // Given
        String settlementNo = "123";
        willDoNothing().given(adminSCMRepository).deleteBySettlementNo(settlementNo);

        // When
        adminSCMRepository.deleteBySettlementNo(settlementNo);

        // Then
        then(adminSCMRepository).should().deleteBySettlementNo(settlementNo);
    }

    @Test
    @DisplayName("deleteAllBySettlementNo 테스트")
    public void testDeleteAllBySettlementNo() {
        // Given
        String settlementNo = "123";
        willDoNothing().given(adminSCMRepository).deleteAllBySettlementNo(settlementNo);

        // When
        adminSCMRepository.deleteAllBySettlementNo(settlementNo);

        // Then
        then(adminSCMRepository).should().deleteAllBySettlementNo(settlementNo);
    }
}
