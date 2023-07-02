<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.ojtsin.demo.dto.SCMinfoDTO" %>
<%@ page import = "com.ojtsin.demo.dto.SellerInfoDTO" %>
<%@ page import = "com.ojtsin.demo.domain.UserSCMDTO" %>
<%@ page import = "java.util.*" %>

<%
	// 판매자 이용데이터중 이미 선정산 신청한 데이터를 제외한 전체 데이터 리스트
	List<UserSCMDTO> scmList = (List<UserSCMDTO>)request.getAttribute("SCMinfoList");
%>
<%
	// 선정산 신청한 데이터 리스트(승인전, 승인, 반려)
	List<UserSCMDTO> applicationScmList = (List<UserSCMDTO>)request.getAttribute("ApplicationSCMinfoList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>초간편 선정산서비스 올라</title>

	<style>
    	body {
    	background-color: black;
    	color: white;
    	}
    	
    	.div_view_scm_table {
			margin: 30px 0 0 0;
		}   
		    	
		.view_scm_table {
			text-align: center;
			margin: 0 auto 0 auto;
			width: 1000px;
		}
		
		.view_approve_table {
			text-align: center;
			margin: 0 auto 0 auto;
			width: 900px;
		}
		
		.application_input {
			text-align: right;
		}
		
		.h2_view_scm {
			text-align: center;
		}
		
		/* adminpage --------------------------------------------------------------------------------------*/
		.approve_gray {
			background-color: gray;
		}
		.approve_red {
			background-color: red;
		}
		.approve_green {
			background-color: green;
		}
    
    </style>

</head>
<body>

		<div class="div_view_scm_table">
			<h2 class="h2_view_scm">판매 데이터 조회</h2>
			<table border=1 class="view_scm_table">
				<tr>
					<th>
						사업자 등록번호
					</th>
					<th>
						사업장
					</th>
					<th>
						판매일
					</th>
					<th>
						판매 금액 [원]
					</th>
					<th>
						선정산 가능 한도 [원]
					</th>
					<th>
						선정산 신청 금액 [원]
					</th>
					<th>
						
					</th>

				</tr>
				
				
					<% if(scmList.isEmpty()) { %>
				<tr>
					<td colspan="7" rowspan="2"> 조회할 데이터가 없습니다. </td>
				</tr> 
				<% } else { %>
				
				 
			
				
				<% for(int i = 0; i < scmList.size(); i ++) { 
					UserSCMDTO temp = scmList.get(i);
				%>		
				
				
				<tr>
					<td>
						<%= temp.getBusinessNumber() %>
					</td>
					<td>
						<%= temp.getScm() %>
					</td>
					<td>
						<%= temp.getSettlement_date() %>
					</td>
					<td>
						<%= temp.getSettlement_amount() %>
					</td>
					<td>
						<%= temp.getEstimated_amount() %>
					</td>
					<td>
						<input type="number" id="<%= temp.getSettlementNo() %>" pattern="[0-9]"
							 placeholder="<%= temp.getEstimated_amount() %> 원 이내" class="application_input" 
						/>
					</td>
					<td>
						<input type="button" onclick="clickSubmitBtn(`<%= temp.getSettlementNo() %>`, 
																	 `<%= temp.getScm() %>`,
																	 `<%= temp.getSettlement_amount() %>`,
																	 `<%= temp.getSettlement_date() %>`,
																	 `<%= temp.getBusinessNumber() %>`,
																	 `<%= temp.getName() %>`,
																	 `<%= temp.getBank_name() %>`,
																	 `<%= temp.getBank_account() %>`,
																	 `<%= temp.getEstimated_amount() %>`,
																	 `<%= temp.getApproval() %>`
																	 )" value="신청" />
					</td>
					
				</tr>
				
					<% } %>
				<% } %>
				
			</table>
		</div>
		<br /><br />
		
		<div>
			<h2 class="h2_view_scm">선정산 신청 내역</h2>
			<table border=1 class="view_scm_table">
				<tr>
					<th colspan="1" align="center">
						사업자 등록번호
					</th>
					<th>
						사업장
					</th>
					<th>
						판매일
					</th>
					<th>
						판매 금액 [원]
					</th>
					<th>
						선정산 가능 한도 [원]
					</th>
					<th>
						신청한 금액 [원]
					</th>
					<th>
						수정
					</th>
					<th>
						승인 여부
					</th>
					<th>
						취소
					</th>
					
				</tr>
				
				<% if(applicationScmList.isEmpty()) { %>
				<tr>
					<td colspan="9" rowspan="2"> 신청한 데이터가 없습니다. </td>
				</tr> 
				<% } else { %>
				
				<% for(int i = 0; i < applicationScmList.size(); i ++) { 
					UserSCMDTO temp = applicationScmList.get(i);
				%>		
				
				<tr>
					<td>
						<%= temp.getBusinessNumber() %>
					</td>
					<td>
						<%= temp.getScm() %>
					</td>
					<td>
						<%= temp.getSettlement_date() %>
					</td>
					<td>
						<%= temp.getSettlement_amount() %>
					</td>
					<td>
						<%= temp.getEstimated_amount() %>
					</td>
					<td>
						<% 
							String approved = "승인";
							if(approved.equals(temp.getApproval())) { %>
						<input type="number" id="<%= temp.getSettlementNo() %>" pattern="[0-9]"
							 placeholder="<%= temp.getApplication_amount() %> 원" class="application_input" readonly="readonly"/>
						<% } else { %>
						<input type="number" id="<%= temp.getSettlementNo() %>" pattern="[0-9]"
							 placeholder="<%= temp.getApplication_amount() %> 원" class="application_input" />
						<% } %>
						
					</td>
					<td>
						<% 
							if(approved.equals(temp.getApproval())) { %>
						<input type="button" onclick="clickUpdateBtn(`<%= temp.getSettlementNo() %>`, 
																	 `<%= temp.getScm() %>`,
																	 `<%= temp.getSettlement_amount() %>`,
																	 `<%= temp.getSettlement_date() %>`,
																	 `<%= temp.getBusinessNumber() %>`,
																	 `<%= temp.getName() %>`,
																	 `<%= temp.getBank_name() %>`,
																	 `<%= temp.getBank_account() %>`,
																	 `<%= temp.getEstimated_amount() %>`,
																	 `<%= temp.getApproval() %>`
																	 )" value="수정" disabled="disabled"/>
						<% } else { %>
						<input type="button" onclick="clickUpdateBtn(`<%= temp.getSettlementNo() %>`, 
																	 `<%= temp.getScm() %>`,
																	 `<%= temp.getSettlement_amount() %>`,
																	 `<%= temp.getSettlement_date() %>`,
																	 `<%= temp.getBusinessNumber() %>`,
																	 `<%= temp.getName() %>`,
																	 `<%= temp.getBank_name() %>`,
																	 `<%= temp.getBank_account() %>`,
																	 `<%= temp.getEstimated_amount() %>`,
																	 `<%= temp.getApproval() %>`
																	 )" value="수정" />
						<% } %>
						
					</td>
					
					<% 
						String approvalBefore = "승인전";
						String rejected = "반려";
						
						if(approvalBefore.equals(temp.getApproval())) { %>
					<td class="approve_gray">
					<% } else if(approved.equals(temp.getApproval())) { %>
					<td class="approve_green">
					<% } else { %>
					<td class="approve_red">
					<% } %>
						<%= temp.getApproval() %>
					</td>
					
					<td>
						<% 
							if(approved.equals(temp.getApproval())) { %>
						<input type="button" onclick="clickDeleteBtn(`<%= temp.getSettlementNo() %>`, 
																	 `<%= temp.getScm() %>`,
																	 `<%= temp.getSettlement_amount() %>`,
																	 `<%= temp.getSettlement_date() %>`,
																	 `<%= temp.getBusinessNumber() %>`,
																	 `<%= temp.getName() %>`,
																	 `<%= temp.getBank_name() %>`,
																	 `<%= temp.getBank_account() %>`,
																	 `<%= temp.getEstimated_amount() %>`,
																	 `<%= temp.getApproval() %>`
																	 )" value="취소" disabled="disabled" />
						<% } else { %>
						<input type="button" onclick="clickDeleteBtn(`<%= temp.getSettlementNo() %>`, 
																	 `<%= temp.getScm() %>`,
																	 `<%= temp.getSettlement_amount() %>`,
																	 `<%= temp.getSettlement_date() %>`,
																	 `<%= temp.getBusinessNumber() %>`,
																	 `<%= temp.getName() %>`,
																	 `<%= temp.getBank_name() %>`,
																	 `<%= temp.getBank_account() %>`,
																	 `<%= temp.getEstimated_amount() %>`,
																	 `<%= temp.getApproval() %>`
																	 )" value="취소" />
						<% } %>
						
					</td>

				</tr>
				
					<% } %>
				<% } %>
				
			</table>
		</div><br /><br /><br />
		
		<div>
			<table border=1 class="view_approve_table">
				<tr>
					<td class="approve_gray">
						승인전
					</td>
					<td>
						관리자가 승인하기 전 상태입니다. 선정산 신청금액 수정이 가능합니다.
					</td>
				</tr>
				
				<tr>
					<td class="approve_green">
						승인
					</td>
					<td>
						관리자가 승인한 상태입니다. 선정산 신청금액 수정이 불가능하며 신청하신 선정산 금액이 계좌로 지급됩니다.
					</td>
				</tr>
				
				<tr>
					<td class="approve_red">
						반려
					</td>
					<td>
						관리자가 승인 반려한 상태입니다. 선정산 신청금액을 수정하여 재 신청이 가능합니다.
					</td>
				</tr>
			</table>
		</div>
		
		<script>

			// 선정산 신청 버튼 클릭시
			const clickSubmitBtn = (settlementNo, 
									scm,
									settlementAmount,
									settlementDate,
									businessNumber,
									name,
									bankName,
									bankAccount,
									estimatedAmount,
									approval
									) => 
			{
				// 값 비교를 위해 문자열 => 숫자로 변환
				let settlement_amount = Number(settlementAmount);
				let estimated_amount = Number(estimatedAmount);
				let application_amount = Number(
												document.getElementById(settlementNo).value
												);
				
				// 신청금액 유효성 확인
				if (application_amount <= estimated_amount && application_amount > 0) {

					fetch("/settlement_application/save", {
						method: "POST",
						headers: {
				            "Content-Type": "application/json"
				        },
				        body: JSON.stringify({
							settlementNo: settlementNo,
							scm: scm,
							settlement_amount: settlementAmount,
							settlement_date: settlementDate,
							businessNumber: businessNumber,
							name: name,
							bank_name: bankName,
							bank_account: bankAccount,
							estimated_amount: estimatedAmount,
							application_amount: application_amount,
							approval: approval
							})
						})
						.then(alert("선정산 신청 되었습니다."))
						.then(() => {window.location.reload()})
						
				} else {
					alert("신청 금액이 유효한지 확인해 주세요.\n" + "[ " + application_amount + " 원 ]");
				}
			}
			
			// 신청한 선정산 데이터 수정 버튼 클릭
			const clickUpdateBtn = (settlementNo, 
					scm,
					settlementAmount,
					settlementDate,
					businessNumber,
					name,
					bankName,
					bankAccount,
					estimatedAmount,
					approval
					) => 
			{
				// 값 비교를 위해 문자열 => 숫자로 변환
				let settlement_amount = Number(settlementAmount);
				let estimated_amount = Number(estimatedAmount);
				let application_amount = Number(
												document.getElementById(settlementNo).value
												);
				
				// 신청금액 유효성 확인
				if (application_amount <= estimated_amount && application_amount > 0) {
				
					// 이미 승인 상태일 경우 수정 불가
					if(approval !== "승인") {
						fetch("/settlement_application/update", {
							method: "POST",
							headers: {
					            "Content-Type": "application/json"
					        },
					        body: JSON.stringify({
								settlementNo: settlementNo,
								scm: scm,
								settlement_amount: settlementAmount,
								settlement_date: settlementDate,
								businessNumber: businessNumber,
								name: name,
								bank_name: bankName,
								bank_account: bankAccount,
								estimated_amount: estimatedAmount,
								application_amount: application_amount,
								approval: approval
								})
							})
							.then(alert("신청 금액을 수정하였습니다."))
							.then(() => {window.location.reload()})
					} else {
						alert("이미 승인된 신청건 입니다.")
					}
					
						
				} else {
					alert("신청 금액이 유효한지 확인해 주세요.\n" + "[ " + application_amount + " 원 ]");
				}
			}
			
			// 신청한 선정산 취소 버튼 클릭
			const clickDeleteBtn = (settlementNo, 
									scm,
									settlementAmount,
									settlementDate,
									businessNumber,
									name,
									bankName,
									bankAccount,
									estimatedAmount,
									approval) => 
			{
				
				 if (window.confirm("선정산을 취소 하시겠습니까?")) {

					 // 이미 승인된 선정산인 경우 취소 불가
					 if(approval !== "승인") {
						 fetch("/settlement_application/delete", {
								method: "POST",
								headers: {
						            "Content-Type": "application/json"
						        },
						        body: JSON.stringify({
									settlementNo: settlementNo
									})
								})
								.then(() => {window.location.reload()})

						} else {
							alert("이미 승인된 신청건 입니다.")
						}
						 
		        } else {
		          // 취소
		        }
			}
			
	   </script>

</body>
</html>