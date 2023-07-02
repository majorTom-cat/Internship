package com.ojtsin.demo.domain;

import java.time.LocalDateTime;

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
@Table(name = "account")
public class AccountDTO {
	// 사업자 등록번호
	@Id
	@Column(name = "business_number")
    private String businessnumber;
	
	// scm
	@Column(name ="scm_name")
	private String scmName;
	
	// scm 아이디
	@Column(name = "scm_id")
	private String scmid;
	
	// 사업자 이름
	@Column(name = "name", nullable = false)
	private String name;
	
	//비밀번호
	@Column(name = "password", nullable = false)
	private String password;
	
	//은행명
	@Column(name = "bank_name")
	private String bank_name;
	
	//계좌 번호
	@Column(name = "bank_account")
	private String bank_account;
	
	// 가입일
//	@Column(name = "registerdate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	private LocalDateTime register_date;
	@Column(name = "registerdate")
	private String register_date;

	// 정보제공 동의여부
	@Column(name = "agreement")
	private String agreement;
	
	
	@Builder
	public AccountDTO(String businessnumber, String scmName, String scmid, String name, String password, String bank_name, 
			String bank_account, String register_date, String agreement) {
		this.businessnumber = businessnumber;
		this.scmName = scmName;
		this.scmid = scmid;
		this.name = name;
		this.password = password;
		this.bank_name = bank_name;
		this.bank_account = bank_account;
		this.register_date = register_date;
		this.agreement = agreement;
	}
	
}
