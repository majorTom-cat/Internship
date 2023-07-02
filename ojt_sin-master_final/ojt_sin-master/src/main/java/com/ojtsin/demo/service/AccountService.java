package com.ojtsin.demo.service;



import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ojtsin.demo.domain.AccountDTO;
import com.ojtsin.demo.domain.AdminSCMDTO;
import com.ojtsin.demo.domain.UserSCMDTO;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.dto.ScmsDTO;
import com.ojtsin.demo.repository.AccountRepository;
import com.ojtsin.demo.repository.AdminSCMRepository;
import com.ojtsin.demo.repository.ScmsRepository;
import com.ojtsin.demo.repository.UserSCMRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {

	private AccountRepository repository;
	private ScmsRepository scmsRepostory;
	private UserSCMRepository userScmRepository;
	private AdminSCMRepository adminScmRepository;
	
	private UserSCMService userScmService;
	
	public AccountService(AccountRepository repository, ScmsRepository scmsRepostory, UserSCMRepository userScmRepository, 
							AdminSCMRepository adminScmRepository, UserSCMService userScmService) {
		this.repository = repository;
		this.scmsRepostory = scmsRepostory;
		this.userScmRepository = userScmRepository;
		this.adminScmRepository = adminScmRepository;
		
		this.userScmService = userScmService;
	}
	
	@Transactional
	//회원가입
	public void submitAccount(AccountDTO dto) {
		String coupang_eng = "coupang";
		String submitScm = null;
		log.info("scm name : {}", dto.getScmName());
		
		// 사업장 이름에 해당하는 scm 아이디 얻기. 'coupang' 일 경우 '쿠팡' 으로 변경
		if(coupang_eng.equals(dto.getScmName())) {
			submitScm = "쿠팡";
		} else {
			submitScm = dto.getScmName();
		}
		
		ScmsDTO scmsDTO = new ScmsDTO();
		Optional<ScmsDTO> scmsDTOOptional = scmsRepostory.findByScmName(submitScm);
		if(scmsDTOOptional.isPresent()) {
			scmsDTO = scmsDTOOptional.get(); 
		} else {
			scmsDTO.setScmName("");
			scmsDTO.setId(0L);
		}
		
		LocalDateTime localDateTime = LocalDateTime.now();
		String parsedLocalDateTimeNow = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		log.info("회원가입 시간(LocalDateTime to String) : {}", parsedLocalDateTimeNow);
		
		AccountDTO account = AccountDTO.builder()
									   .businessnumber(dto.getBusinessnumber())
									   .scmid(scmsDTO.getId().toString())
									   .scmName(dto.getScmName())
									   .name(dto.getName())
									   .password(dto.getPassword())
									   .bank_name(dto.getBank_name())
									   .bank_account(dto.getBank_account())
									   .register_date(parsedLocalDateTimeNow)
									   .agreement("N")
									   .build();
		repository.save(account);
	}
	
	//계정 중복 체크
	public AccountDTO submitCheck(AccountDTO dto) {
		
		Optional<AccountDTO> responseDTO = repository.findByBusinessnumber(dto.getBusinessnumber());
		if(responseDTO.isPresent()) {
			return responseDTO.get();
		} else {
			return new AccountDTO();
		}
		//return repository.findByBusinessnumber(dto.getBusinessnumber());
	}
	
	//로그인
	public AccountDTO loginAccount(AccountDTO dto, HttpServletRequest request) {
		Optional<AccountDTO> responseDTO = repository.findByBusinessnumberAndPassword(dto.getBusinessnumber(), dto.getPassword());
		if(responseDTO.isPresent()) {
			HttpSession session = request.getSession();

			session.setAttribute("business_number", responseDTO.get().getBusinessnumber());
			session.setAttribute("scm_id", responseDTO.get().getScmid());

			return responseDTO.get();
		} else {
			return new AccountDTO();
		}

	}
	
	//계정 정보 리턴
	public AccountDTO getAccount(AccountDTO dto) {
		Optional<AccountDTO> responseDTO = repository.findByBusinessnumber(dto.getBusinessnumber());
		if(responseDTO.isPresent()) {
			return responseDTO.get();
		} else {
			return new AccountDTO();
		}
	}

	@Transactional(readOnly = true)
	public Optional<AccountDTO> findByBusinessnumber(String businessnumber) {
		return repository.findByBusinessnumber(businessnumber);
	}

	public AccountDTO findByBusinessnumberAndScmid(String businessnumber, String scmid) {
		return repository.findByBusinessnumberAndScmid(businessnumber, scmid);
	}

	@Transactional
	// 정보제공 동의 여부 "N" → "Y" 로 수정
	public void setAgreementToY(AccountDTO dto) {
		AccountDTO account = repository.findByBusinessnumberAndScmid(dto.getBusinessnumber(), dto.getScmid());
		account.setAgreement("Y");
	}
	
	// 승인전
	final String approveBeforeString = "승인전";
	// 승인
	final String approveString = "승인";
	// 반려
	final String rejectString = "반려";
	// 정보제공 동의
	final String agreementY = "Y";

	// 관리자 로그인시 관리자 페이지 리턴
	public void viewAdminPage(String scmid, String businessnumber, Model model) {
		
		// 선정산 신청한 리스트
		List<UserSCMDTO> savedScmInfoList = userScmRepository.findAllByApproval(approveBeforeString);
		
		// 선정산 신청 승인 리스트
		List<AdminSCMDTO> approvedScmInfoList = adminScmRepository.findAllByApproval(approveString);

		// 선정산 신청 반려 리스트
		List<AdminSCMDTO> rejectedScmInfoList = adminScmRepository.findAllByApproval(rejectString);

		model.addAttribute("ApplicationSCMinfoList", savedScmInfoList);
		model.addAttribute("ApprovedSCMinfoList", approvedScmInfoList);
		model.addAttribute("RejectedSCMinfoList", rejectedScmInfoList);
	}

	// 크롤링 데이터를 담은 페이지 리턴
	public void viewScmPage(String scmid, String businessnumber, Model model) {
		String path = scmid + "/" + businessnumber;
		
		// 가상 크롤링 데이터 주소
		URI uri = UriComponentsBuilder
                .fromUriString("http://222.122.235.25:3000")
                .path("/scm/info/" + path)
                .encode()
                .build()
                .toUri();
		
		// 선정산 신청한 데이터 리스트
		Boolean isSavedScmInfoListEmpty = false;
		List<UserSCMDTO> savedScmInfoList = userScmRepository.findAllByBusinessNumber(businessnumber);
		if(savedScmInfoList.isEmpty()) {
			isSavedScmInfoListEmpty = true; // 빈 리스트인지 체크
		}

        // 사업자 번호에 해당하는 전체 scm 데이터 조회
		RestTemplate restTemplate = new RestTemplate();
		List<SCMinfoDTO> resultSCM  = Arrays.asList(restTemplate.getForObject(uri, SCMinfoDTO[].class));
		
		// 뷰에 반환할 scm 데이터 리스트 생성
		List<UserSCMDTO> userScmDTOList = new ArrayList<>();
		
		int resultSCMsize = resultSCM.size();
		SCMinfoDTO temp;
		
		for(int i = 0; i < resultSCMsize; i++) {
			temp = resultSCM.get(i);
			
			// 이미 선정산 신청한 데이터는 조회에서 제외. 신청 전 데이터만 조회 리스트에 추가
			if(isSavedScmInfoListEmpty) { // 신청한 리스트가 빈 값일 경우 모두 조회 리스트에 추가
				userScmDTOList.add(userScmService.checkUserSCMInfo(temp));
			} else {
				Boolean isSaved = false;
				for(int j = 0; j < savedScmInfoList.size(); j++) {
					if(savedScmInfoList.get(j).getSettlementNo().equals(temp.getSettlement_no())) {
						isSaved = true; // 해당 데이터가 이미 저장되어 있다면 true 반환. 조회할 리스트에 이미 저장된 데이터는 추가하지 않음
					}
				}
				
				if(isSaved == false) { // 해당 데이터가 저장되어있지 않다면 조회 리스트에 추가
					userScmDTOList.add(userScmService.checkUserSCMInfo(temp));
					isSaved = false; // 판별 초기화
				}
			}
		}

		model.addAttribute("SCMinfoList", userScmDTOList);
		model.addAttribute("ApplicationSCMinfoList", savedScmInfoList);
	}
	
}
