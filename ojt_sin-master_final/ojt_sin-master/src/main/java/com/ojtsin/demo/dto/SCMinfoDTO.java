package com.ojtsin.demo.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "scminfo")
public class SCMinfoDTO { // DTO, DAO, VO, DDD 패턴
	
		// 판매 번호
		@Id
		@Column(name = "settlement_no")
	    private String settlement_no;
		
		// 사업장 이름
		@Column(name = "scm")
		private String scm;
		
		// 판매 금액
		@Column(name = "settlement_amount")
		private String settlement_amount;
		
		// 판매일
		@Column(name = "settlement_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
		private String settlement_date;
		
		// 판매자 정보
//		@Transient
//		@Column(name = "seller_info")
//		private Object seller_info;
		
		// 사업자 정보 
		@ManyToOne
		@JoinColumn(name = "seller_info", referencedColumnName = "business_number", foreignKey = @ForeignKey(name = "FK_scminfo_seller_info"))
		private SellerInfoDTO seller_info;
		
//		@Getter
//		@Setter
//		public static class Seller_info {
//			
//			@Id
//			@Column(name = "business_number")
//		    private String business_number;
//			
//			// 사업자 이름
//			@Column(name = "name", nullable = false)
//			private String name;
//			
//			//은행명
//			@Column(name = "bank_name")
//			private String bank_name;
//			
//			//계좌 번호
//			@Column(name = "bank_account")
//			private String bank_account;
//		}

}
