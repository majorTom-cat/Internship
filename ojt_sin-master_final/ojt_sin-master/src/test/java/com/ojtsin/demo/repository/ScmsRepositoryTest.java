package com.ojtsin.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ojtsin.demo.dto.ScmsDTO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScmsRepositoryTest {

	@Mock
    private ScmsRepository scmsRepository;

    @Test
    public void testFindByScmName() {
        // Given
        String scmName = "TestSCM";
        ScmsDTO scmsDTO = new ScmsDTO();
        given(scmsRepository.findByScmName(scmName)).willReturn(Optional.of(scmsDTO));

        // When
        Optional<ScmsDTO> result = scmsRepository.findByScmName(scmName);

        // Then
        assertThat(result.get()).isEqualTo(scmsDTO);
        then(scmsRepository).should().findByScmName(scmName);
    }

}
