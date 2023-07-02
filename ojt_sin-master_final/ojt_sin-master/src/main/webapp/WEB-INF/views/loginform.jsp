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
    <title>초간편dd 통합선정산서비스 올라</title>
    
    <style>
    	body {
    	background-color: black;
    	color: white;
    	}
    
    </style>

	<script>
	
    	const clickLoginBtn = () => {
    		let inputId = document.getElementById("id").value;
    		let inputPw1 = document.getElementById("pw1").value;
    		
    		// 아이디와 비밀번호에 해당하는 계정 확인
    		fetch("/loginaccount", {
   				method: "POST",
   				headers: {
                       "Content-Type": "application/json"
                   },
   				body: JSON.stringify({id: inputId, password: inputPw1})
   				})
    		.then(response => response.json())
    		.then(data => loginCheck(data))
    		.catch(error => console.log(error))

    	}
    	
    	const loginCheck = (data) => {
    		if(data === undefined || data.id === null) {
    			alert("존재하지 않는 회원입니다.")
    		} else {
    			//let userid = ${id};
    			//window.location.href = "/viewscm";
    		}
    	}
    </script>
</head>
<body>
	<form name="loginform" action="loginaccount" method="post">
		<center>
			<table border=1>
				<tr>
					<td colspan="2" align="center">
						<b>로그인</b>
					</td>
				</tr>
				
				<tr>
					<td>사업자 등록번호(ID)</td>
					<td>
						<input type="text" id="id" placeholder="-없이 입력해 주세요.">
					</td>
				</tr>
				
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" id="pw1" placeholder="영문/숫자/특수문자 혼합 8~15자리">
					</td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="button" value="로그인" onclick="clickLoginBtn();"/> &nbsp;&nbsp;
						<a href="javascript:location.href='./submitform'">회원가입</a>
					</td>
				</tr>

			</table>
		</center>
	</form>
	
	
</body>
</html>