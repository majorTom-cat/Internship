<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import = "com.ojtsin.demo.domain.AccountDTO" %>

<%
   AccountDTO dto = (AccountDTO)request.getAttribute("data");
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
    <title>초간편 통합선정산서비스 올라</title>
    
    <style>
    	body {
	    	background-color: black;
	    	color: white;
    	}
    	
    	.login_table {
    		text-align: center;
			margin: 10px auto 0 auto;
			width: 430px;
    	}
    
    </style>

	<script>
		
	
    	const clickLoginBtn = () => {
    		let inputBusinessNumber = document.getElementById("business_number").value;
    		let inputPassword = document.getElementById("pw1").value;
    		
    		// 아이디와 비밀번호에 해당하는 계정 확인
    		fetch("/loginaccount", {
   				method: "POST",
   				headers: {
                       "Content-Type": "application/json"
                   },
   				body: JSON.stringify({
   					businessnumber: inputBusinessNumber, 
   					password: inputPassword
   					})
   				})
    		.then(response => response.json())
    		.then(data => {loginCheck(data)}
    		)
    	}
    	
    	const viewRequest = (data) => {
    		
    		const url = "/viewscm?scmid=" + data.scmid + "&businessnumber=" + data.businessnumber;
    		
    		fetch(url, {
   				method: "GET"
   				})
   				.then(() => {
			       window.location.href = "/viewscm?scmid=" + data.scmid + "&businessnumber=" + data.businessnumber;
				});
    	}
    	
    	const loginCheck = (data) => {
    		
    		const loginAfterUrl = "/loginafter";
    		
    		if(data === undefined || data.businessnumber === null) {
    			alert("존재하지 않는 회원입니다.")
    		} 
    		else {
    			//viewRequest(data);
   				
    			fetch(loginAfterUrl, {
       				method: "GET"
       				})
       				.then(() => {
    			       window.location.href = "/loginafter";
    				});
    		}
    	}
    </script>
</head>
<body>
	<form name="loginform" action="loginaccount" method="post">

		<table border=1 class="login_table">
			<tr>
				<td colspan="2" align="center">
					<b>로그인</b>
				</td>
			</tr>
			
			<tr>
				<td>사업자 등록번호(ID)</td>
				<td>
					<input type="text" id="business_number" placeholder="사업자 등록번호를 입력해 주세요." size="28">
				</td>
			</tr>
			
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" id="pw1" placeholder="영문, 숫자, 특수문자 혼합 8~15자리" size="28">
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="로그인" onclick="clickLoginBtn();"/> &nbsp;&nbsp;
					<a href="javascript:location.href='./submitform'">회원가입</a>
				</td>
			</tr>

		</table>
	</form>
	
	
</body>
</html>