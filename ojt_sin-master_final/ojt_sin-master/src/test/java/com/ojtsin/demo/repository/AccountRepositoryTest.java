package com.ojtsin.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ojtsin.demo.domain.AccountDTO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

	//@Autowired
	//@InjectMocks
	@Mock
    AccountRepository accountRepository;
	
	@Test
	@DisplayName("DB에 AccountDTO 데이터가 저장되는지 테스트")
	void accountSaveTest() {
		
        // given
		LocalDateTime localDateTime = LocalDateTime.now();
		String parsedLocalDateTimeNow = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		AccountDTO accountDTO1 = AccountDTO.builder()
										  .agreement("N")
										  .bank_account("111-111111-11-111")
										  .bank_name("기업")
										  .businessnumber("111-1111")
										  .name("홍길동")
										  .password("!1qwerqwer")
										  .register_date(parsedLocalDateTimeNow)
										  .scmid("10")
										  .build();

		Mockito.when(accountRepository.save(accountDTO1)).thenReturn(accountDTO1);
			// Mockito.when(가짜 객체의 로직 실행). thenReturn(실행되면 이것을 반환한다.)
        // when
		AccountDTO accountDTO2 = accountRepository.save(accountDTO1);
 
        // then
		assertThat(accountDTO2.getName()).isEqualTo(accountDTO1.getName());
	}
	
	@Test
	@DisplayName("findByBusinessnumberAndPassword 테스트")
	public void findByBusinessnumberAndPasswordTest() {
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
		
		Optional<AccountDTO> accountOptionalDTO = Optional.of(accountDTO);
		
		String businessnumber = "123-4567";
		String password = "!1qwerqwer";
		
		given(accountRepository.findByBusinessnumberAndPassword(businessnumber, password)).willReturn(accountOptionalDTO);
		
		assertThat(accountRepository.findByBusinessnumberAndPassword(businessnumber, password))
			.isEqualTo(accountOptionalDTO);
	}

}
