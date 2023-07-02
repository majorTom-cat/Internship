package com.ojtsin.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SellerInfoDTO {

	// 사업자 번호
	@Id
	@Column(name = "business_number")
    private String business_number;
	
	// 사업자 이름
	@Column(name = "name", nullable = false)
	private String name;
	
	//은행명
	@Column(name = "bank_name")
	private String bank_name;
	
	//계좌 번호
	@Column(name = "bank_account")
	private String bank_account;
}
