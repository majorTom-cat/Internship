package com.ojtsin.demo.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ojtsin.demo.domain.AdminSCMDTO;
import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.repository.AdminSCMRepository;
import com.ojtsin.demo.repository.UserSCMRepository;

//import com.ojtsin.demo.userscm.SCMinfoDTO.Seller_info;

@Service
public class UserSCMService {
	
	// 정산 비율
	public static final String SETTLEMENT_RATIO = "0.8";
	
	// 승인전
	String approveBeforeString = "승인전";
	// 승인
	String approveString = "승인";
	// 반려
	String rejectString = "반려";
	
	private UserSCMRepository repository;
	private AdminSCMRepository adminSCMRepository;
	
	public UserSCMService(UserSCMRepository repository, AdminSCMRepository adminSCMRepository) {
		this.repository = repository;
		this.adminSCMRepository = adminSCMRepository;
	}
	
//	public void getscm() throws ParseException {
//		StringBuffer result = new StringBuffer();
//        String jsonPrintString = null;
//        try {
//            String apiUrl = "http://222.122.235.25:3000/scm/info/" + "1" + "/" + "123-4567";
//            URL url = new URL(apiUrl);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.connect();
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
//            String returnLine;
//            while((returnLine = bufferedReader.readLine()) != null) {
//                result.append(returnLine);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//       
//        String tempstr = result.toString();
//	        
//        JSONParser parser = new JSONParser();
//		ArrayList<String> obj = (ArrayList<String>) parser.parse(tempstr);
//        
//        JSONArray jsonArray = new JSONArray();
//        
//        try {
//            // JSON 문자열을 JSONArray로 파싱
//            jsonArray = (JSONArray) JSONValue.parse(tempstr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}

	// userscm 조회후 판매 데이터 리턴
	public UserSCMDTO checkUserSCMInfo(SCMinfoDTO scminfodto) {
		
		// 정산 비율 계산
		BigDecimal tempSettlement_amount = new BigDecimal(scminfodto.getSettlement_amount());
		BigDecimal ratio = new BigDecimal(SETTLEMENT_RATIO); // SETTLEMENT_RATIO = "0.8"
		BigDecimal estimatedAmount = tempSettlement_amount.multiply(ratio);
		
		UserSCMDTO userSCM = UserSCMDTO.builder()
									   .bank_account(scminfodto.getSeller_info().getBank_account())
									   .bank_name(scminfodto.getSeller_info().getBank_name())
									   .businessNumber(scminfodto.getSeller_info().getBusiness_number())
									   .estimated_amount(estimatedAmount.setScale(0, RoundingMode.HALF_EVEN).toString())
									   .name(scminfodto.getSeller_info().getName())
									   .scm(scminfodto.getScm())
									   .settlement_amount(scminfodto.getSettlement_amount())
									   .settlement_date(scminfodto.getSettlement_date())
									   .settlementNo(scminfodto.getSettlement_no())
									   .approval("승인전")
									   .build();
		return userSCM;
	}
	
	@Transactional
	// userscm 테이블에 저장
	public void saveUserSCMInfo(UserSCMDTO userSCMDTO) {
		repository.save(userSCMDTO);
	}

	// 사업자 번호를 받아 해당 선정산 요청한 데이터 리턴
	public List<UserSCMDTO> findAllByBusiness_number(String businessNumber) {
		return repository.findAllByBusinessNumber(businessNumber);
	}

	@Transactional
	// 신청금 수정
	public void updateUserSCMInfo(UserSCMDTO userSCMDTO) {
		UserSCMDTO temp = repository.findBySettlementNo(userSCMDTO.getSettlementNo());
		temp.setApplication_amount(userSCMDTO.getApplication_amount());
		temp.setApproval(approveBeforeString);
		
		adminSCMRepository.deleteBySettlementNo(userSCMDTO.getSettlementNo());
	}

	@Transactional
	// 선정산 취소
	public void deleteUserSCMInfo(UserSCMDTO userSCMDTO) {
		repository.deleteAllBySettlementNo(userSCMDTO.getSettlementNo());
		adminSCMRepository.deleteAllBySettlementNo(userSCMDTO.getSettlementNo());
	}

	// 선정산 신청 전체 조회
	public List<UserSCMDTO> findAll() {
		return repository.findAll();
	}

	@Transactional
	// 선정산 신청 승인
	public void approveUserSCMInfo(UserSCMDTO userSCMDTO) {
		// userscm 테이블의 approval 데이터 "승인전" -> "승인" 으로 수정
		UserSCMDTO temp = repository.findBySettlementNo(userSCMDTO.getSettlementNo());
		temp.setApproval(approveString);
		
		// adminscm 테이블에 temp 데이터 insert
		AdminSCMDTO adminSCM = AdminSCMDTO.builder()
									      .bank_account(temp.getBank_account())
									      .bank_name(temp.getBank_name())
									      .businessNumber(temp.getBusinessNumber())
									      .estimated_amount(temp.getEstimated_amount())
									      .name(temp.getName())
									      .scm(temp.getScm())
									      .settlement_amount(temp.getSettlement_amount())
									      .settlement_date(temp.getSettlement_date())
									      .settlementNo(temp.getSettlementNo())
									      .approval(approveString)
									      .application_amount(temp.getApplication_amount())
									       .build();
		adminSCMRepository.save(adminSCM);
	}

	@Transactional
	// 선정산 신청 반려
	public void rejectUserSCMInfo(UserSCMDTO userSCMDTO) {
		// userscm 테이블의 approval 데이터 "승인전" -> "반려" 로 수정
		UserSCMDTO temp = repository.findBySettlementNo(userSCMDTO.getSettlementNo());
		temp.setApproval(rejectString);
		
		// adminscm 테이블에 temp 데이터 insert
		AdminSCMDTO adminSCM = AdminSCMDTO.builder()
									      .bank_account(temp.getBank_account())
									      .bank_name(temp.getBank_name())
									      .businessNumber(temp.getBusinessNumber())
									      .estimated_amount(temp.getEstimated_amount())
									      .name(temp.getName())
									      .scm(temp.getScm())
									      .settlement_amount(temp.getSettlement_amount())
									      .settlement_date(temp.getSettlement_date())
									      .settlementNo(temp.getSettlementNo())
									      .approval(rejectString)
									      .application_amount(temp.getApplication_amount())
									      .build();
		adminSCMRepository.save(adminSCM);
	}

	public List<UserSCMDTO> findAllByApproval(String approval) {
		return repository.findAllByApproval(approval);
	}
	
}
