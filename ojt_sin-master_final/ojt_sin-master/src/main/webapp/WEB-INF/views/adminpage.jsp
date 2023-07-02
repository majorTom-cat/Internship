<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.ojtsin.demo.dto.SCMinfoDTO" %>
<%@ page import = "com.ojtsin.demo.dto.SellerInfoDTO" %>
<%@ page import = "com.ojtsin.demo.domain.UserSCMDTO" %>
<%@ page import = "com.ojtsin.demo.domain.AdminSCMDTO" %>
<%@ page import = "java.util.*" %>

<%
	// 선정산 신청한 데이터 리스트(승인전)
	List<UserSCMDTO> applicationScmList = (List<UserSCMDTO>)request.getAttribute("ApplicationSCMinfoList");
%>
<%
	// 선정산 신청한 데이터 리스트(승인)
	List<AdminSCMDTO> ApprovedSCMinfoList = (List<AdminSCMDTO>)request.getAttribute("ApprovedSCMinfoList");
%>
<%
	// 선정산 신청한 데이터 리스트(반려)
	List<AdminSCMDTO> RejectedSCMinfoList = (List<AdminSCMDTO>)request.getAttribute("RejectedSCMinfoList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>초간편 선정산서비스 올라 관리자 페이지</title>

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
						승인
					</th>
					<th>
						승인 여부
					</th>
					<th>
						반려
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
						<%= temp.getApplication_amount() %> 원
					</td>
					<td>
						<input type="button" onclick="clickApproveBtn(`<%= temp.getSettlementNo() %>`, 
																	 `<%= temp.getScm() %>`,
																	 `<%= temp.getSettlement_amount() %>`,
																	 `<%= temp.getSettlement_date() %>`,
																	 `<%= temp.getBusinessNumber() %>`,
																	 `<%= temp.getName() %>`,
																	 `<%= temp.getBank_name() %>`,
																	 `<%= temp.getBank_account() %>`,
																	 `<%= temp.getEstimated_amount() %>`,
																	 `<%= temp.getApplication_amount() %>`
																	 )" value="승인" />
					</td>
					
					<td class="approve_gray">
						<%= temp.getApproval() %>
					</td>
					
					<td>
						<input type="button" onclick="clickRejectBtn(`<%= temp.getSettlementNo() %>`, 
																	 `<%= temp.getScm() %>`,
																	 `<%= temp.getSettlement_amount() %>`,
																	 `<%= temp.getSettlement_date() %>`,
																	 `<%= temp.getBusinessNumber() %>`,
																	 `<%= temp.getName() %>`,
																	 `<%= temp.getBank_name() %>`,
																	 `<%= temp.getBank_account() %>`,
																	 `<%= temp.getEstimated_amount() %>`,
																	 `<%= temp.getApproval() %>`,
																	 `<%= temp.getApplication_amount() %>`
																	 )" value="반려" />
					</td>
				</tr>
				
					<% } %>
				<% } %>
				
			</table>
		</div><br /><br />
		
		<div>
			<h2 class="h2_view_scm">선정산 반려 내역</h2>
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
						승인 여부
					</th>
				</tr>
				
				<% if(RejectedSCMinfoList.isEmpty()) { %>
				<tr>
					<td colspan="9" rowspan="2"> 반려된 데이터가 없습니다. </td>
				</tr> 
				<% } else { %>
				
				<% for(int i = 0; i < RejectedSCMinfoList.size(); i ++) { 
					AdminSCMDTO temp = RejectedSCMinfoList.get(i);
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
						<%= temp.getApplication_amount() %> 원
					</td>
					<td class="approve_red">
						<%= temp.getApproval() %>
					</td>
				</tr>
					<% } %>
				<% } %>
			</table>
		</div><br /><br />

		<div>
			<h2 class="h2_view_scm">선정산 승인 내역</h2>
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
						승인 여부
					</th>
				</tr>
				
				<% if(ApprovedSCMinfoList.isEmpty()) { %>
				<tr>
					<td colspan="9" rowspan="2"> 승인된 데이터가 없습니다. </td>
				</tr> 
				<% } else { %>
				
				<% for(int i = 0; i < ApprovedSCMinfoList.size(); i ++) { 
					AdminSCMDTO temp = ApprovedSCMinfoList.get(i);
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
						<%= temp.getApplication_amount() %> 원
					</td>
					<td class="approve_green">
						<%= temp.getApproval() %>
					</td>
				</tr>
					<% } %>
				<% } %>
			</table>
		</div>
		
		<script>

			// 선정산 신청한 데이터 승인 버튼 클릭
			const clickApproveBtn = (settlementNo, 
					scm,
					settlementAmount,
					settlementDate,
					businessNumber,
					name,
					bankName,
					bankAccount,
					estimatedAmount,
					applicationAmount
					) => 
			{
				if (window.confirm("선정산을 승인 하시겠습니까?\n\n" + "선정산 가능 한도 : " + estimatedAmount + " 원 \n선정산 신청 금액 : " + applicationAmount + " 원")) {
					fetch("/settlement_application/approve", {
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
							application_amount: applicationAmount
							})
						})
						.then(() => {window.location.reload()})
			        } else {
			          // 취소
			        }
			}
			
			// 선정산 신청 데이터 반려 버튼 클릭
			const clickRejectBtn = (settlementNo) => 
			{
				 if (window.confirm("선정산 요청을 반려하시겠습니까?")) {
					 fetch("/settlement_application/reject", {
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
			          // 취소
			        }
			}
	   </script>

</body>
</html>