<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>

<% 
	String businessNumber = (String)request.getAttribute("BusinessNumber"); 
	String ScmId = (String)request.getAttribute("ScmId"); 
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
		}
		
		.application_input {
			text-align: right;
		}
		
		.h2_view_scm {
			text-align: center;
		}
		
		.approve_gray {
			background-color: gray;
		}
		.approve_red {
			background-color: red;
		}
		.approve_green {
			background-color: green;
		}
		
		.agreementpage_td {
			width: 320px;
		}
		
		.agreementpage_inputbtn_div {
			text-align: center;
			align-items: center;
		}
    
    </style>

</head>
<body>

		<div class="div_view_scm_table">
			<h2 class="h2_view_scm">선정산 정보제공 동의</h2>
			<table border=1 class="view_scm_table">
				<tr>
					<td colspan="2">
						올라는 선정산을 위하여 정보 제공 동의 및 채권양도 동의 여부를 확인하고 있습니다.
					</td>

				</tr>
				
				<tr>
					<td class="agreementpage_td">사업자 번호</td>
					<td class="agreementpage_td"><%= businessNumber %></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="checkbox" id='agreementCheckbox' /> 내용을 확인하였으며 관련 사항에 동의합니다. &nbsp;&nbsp;
					</td>
				</tr>
				
				<!-- 
				<tr>
					<td colspan="2" align="center">
						<input type="button" value="확인" onclick='checkAgreement(`<%= businessNumber %>`, `<%= ScmId %>`)'/> &nbsp;&nbsp;
					</td>
				</tr>
				 -->
				
			</table>
			
			<br />
			<div class="agreementpage_inputbtn_div">
				<input type="button" value="확인" onclick='checkAgreement(`<%= businessNumber %>`, `<%= ScmId %>`)'/>
			</div>
				
		</div>
		
	
		<script>
			
			const checkAgreement = (businessNumber, ScmId) => {
				  const checkbox = document.getElementById('agreementCheckbox');
				  const is_checked = checkbox.checked;
	
				  if(is_checked) {
					  clickAgreementBtn(businessNumber, ScmId);
				  } else {
					  alert("정보 제공 및 채권양도에 동의해 주세요.");
				  }
			}
			
			const clickAgreementBtn = (businessNumber, ScmId) => 
			{
				 fetch("/useragree", {
					method: "POST",
					headers: {
			            "Content-Type": "application/json"
			        },
			        body: JSON.stringify({
						businessnumber: businessNumber,
						scmid: ScmId
						})
					})
					.then(() => {window.location.href = "/viewscm?scmid=" + ScmId + "&businessnumber=" + businessNumber;})
			}
			
	   </script>

</body>
</html>