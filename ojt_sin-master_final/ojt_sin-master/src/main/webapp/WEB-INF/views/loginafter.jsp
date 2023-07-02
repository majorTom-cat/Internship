<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String business_number = (String)session.getAttribute("business_number"); %>    
<% String scm_id = (String)session.getAttribute("scm_id"); %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>초간편 통합선정산서비스 올라</title>
	<style>
    	body {
	    	background-color: black;
	    	color: white;
	    	text-align: center;
    	}
    	
    	.login_table {
    		text-align: center;
			margin: 10px auto 0 auto;
			width: 430px;
    	}
    	
    	.loginafter_viewscm_btn {
    		width: 180px;
    		height: 25px;
    		background-color: silver;
    		color: #252525;
    		cursor: pointer;
    		font-weight: bold;
    		margin : 10px auto 0px auto;
    		border-radius: 3px;
    		border: 1px solid white;
    	}
    
    </style>
</head>
<body>
	
	<% if(business_number.equals("000-0000")) { %>
	<h1>관리자님 안녕하세요.</h1>
	<div class="loginafter_viewscm_btn" onclick="viewRequest()">
		선정산 신청목록 조회
	</div>
	<% } else { %>
	<h1>사업자 번호 [<%= business_number %>]님 안녕하세요.</h1>
	<div class="loginafter_viewscm_btn" onclick="viewRequest()">
		판매 데이터 조회하기
	</div>
	<% } %>

	<script>

		const viewRequest = () => {
			
			const url = "/viewscm?scmid=" + '<%= scm_id %>' + "&businessnumber=" + '<%= business_number %>';
			
			fetch(url, {
					method: "GET"
					})
					.then(() => {
			       window.location.href = "/viewscm?scmid=" + '<%= scm_id %>' + "&businessnumber=" + '<%= business_number %>';
				});
		}
	</script>

</body>
</html>