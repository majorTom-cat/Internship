package com.ojtsin.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojtsin.demo.domain.AdminSCMDTO;
import com.ojtsin.demo.domain.UserSCMDTO;

public interface AdminSCMRepository extends JpaRepository<AdminSCMDTO, String>{

	List<AdminSCMDTO> findAllByApproval(String approval);

	void deleteBySettlementNo(String settlementNo);

	void deleteAllBySettlementNo(String settlementNo);

}
