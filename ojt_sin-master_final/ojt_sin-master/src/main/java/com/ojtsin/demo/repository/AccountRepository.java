package com.ojtsin.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojtsin.demo.domain.AccountDTO;

public interface AccountRepository extends JpaRepository<AccountDTO, String>{

	Optional<AccountDTO> findByBusinessnumberAndPassword(String businessnumber, String password);

	Optional<AccountDTO> findByBusinessnumber(String businessnumber);

	AccountDTO findByBusinessnumberAndScmid(String businessnumber, String scmid);
	
}
