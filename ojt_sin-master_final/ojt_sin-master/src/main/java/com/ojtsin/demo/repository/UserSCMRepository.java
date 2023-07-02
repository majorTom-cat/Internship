package com.ojtsin.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;


public interface UserSCMRepository extends JpaRepository<UserSCMDTO, String>{

	List<UserSCMDTO> findAllByBusinessNumber(String businessNumber);

	UserSCMDTO findBySettlementNo(String settlementNo);

	void deleteAllBySettlementNo(String settlementNo);

	List<UserSCMDTO> findAllByApproval(String approval);

}
