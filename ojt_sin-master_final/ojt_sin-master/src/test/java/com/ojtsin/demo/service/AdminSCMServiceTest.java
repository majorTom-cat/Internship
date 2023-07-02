package com.ojtsin.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ojtsin.demo.domain.AdminSCMDTO;
import com.ojtsin.demo.repository.AdminSCMRepository;

@ExtendWith(MockitoExtension.class)
class AdminSCMServiceTest {

	@Mock
    private AdminSCMRepository repository;

    @InjectMocks
    private AdminSCMService adminSCMService;

    @Test
    @DisplayName("findAllByApproval 테스트")
    public void testFindAllByApproval() {
        // Given
        String approval = "승인";
        
        List<AdminSCMDTO> expectedList = Arrays.asList(
                new AdminSCMDTO(),
                new AdminSCMDTO()
        );
        given(repository.findAllByApproval(approval)).willReturn(expectedList);

        // When
        List<AdminSCMDTO> resultList = adminSCMService.findAllByApproval(approval);

        // Then
        assertThat(resultList).isEqualTo(expectedList);
    }

}
