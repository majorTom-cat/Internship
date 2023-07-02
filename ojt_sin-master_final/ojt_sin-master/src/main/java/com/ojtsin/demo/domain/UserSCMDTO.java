package com.ojtsin.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "userscm")
public class UserSCMDTO {
	
	// 사업자 등록번호
	@Id
	@Column(name = "settlement_no", nullable=false)
    private String settlementNo;
	
	// 사업장 이름
	@Column(name = "scm", nullable=false)
	private String scm;
	
	// 판매 금액
	@Column(name = "settlement_amount", nullable=false)
	private String settlement_amount;
	
	// 판매일
	@Column(name = "settlement_date", nullable=false)
	private String settlement_date;
	
	
	// 사업자 등록번호
	@Column(name = "business_number", nullable=false)
	private String businessNumber;
	
	// 판매자 이름
	@Column(name = "name", nullable=false)
	private String name;
	
	// 은행명
	@Column(name = "bank_name", nullable=false)
	private String bank_name;
	
	// 계좌 번호
	@Column(name = "bank_account", nullable=false)
	private String bank_account;
	
	
	// 선정산 가능 금액
	@Column(name = "estimated_amount", nullable=false)
	private String estimated_amount;
	
	// 선정산 신청 금액
	@Column(name = "application_amount")
	private String application_amount;
	
	// 선정산 신청 승인 여부
	@Column(name = "approval")
	private String approval;
	

	@Builder
	public UserSCMDTO(String settlementNo, String scm, String settlement_amount, String settlement_date, String businessNumber, 
			String name, String bank_name, String bank_account, String estimated_amount, String application_amount, String approval) {
		this.settlementNo = settlementNo;
		this.scm = scm;
		this.settlement_amount = settlement_amount;
		this.settlement_date = settlement_date;
		this.businessNumber = businessNumber;
		this.name = name;
		this.bank_name = bank_name;
		this.bank_account = bank_account;
		this.estimated_amount = estimated_amount;
		this.application_amount = application_amount;
		this.approval = approval;
	}
	
}
