<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>초간편 통합선정산서비스 올라</title>
	<style>
    	body {
	    	background-color: black;
	    	color: white;
    	}
    	
    	.submit_table {
    		text-align: center;
    		margin: 10px auto 0 auto;
			width: 430px;
    	}
    </style>
    
    <script>
    	// 비밀번호 유효성 검사
    	const Check_AlphaNumericSpecialUpperLower = (userpassword) => {
			let chk_num     = userpassword.search(/[0-9]/g);
			let chk_eng = userpassword.search(/[A-z]/g);
			//let chk_upp_eng = userpassword.search(/[A-Z]/g);
			let strSpecial  = userpassword.search(/[`~!@#$%^&*|\\\'\";:\/?]/gi);
		 
			let returnValue = 0;
		 
			if(chk_num < 0 || chk_eng < 0 || strSpecial < 0) {
		 		returnValue = 1;
		 	return returnValue;
		 	}
		 return returnValue;
		}
    
    	const clickSubmitBtn = () => {
    		let inputId = document.getElementById("id").value;
    		let inputName = document.getElementById("name").value;
    		let inputPw1 = document.getElementById("pw1").value;
    		let inputPw2 = document.getElementById("pw2").value;
    		
    		// 비밀번호 일치 확인
    		if(inputId.length <= 0 || inputPw1.length <= 0 || inputPw2.length <= 0) {
    			alert("빈칸 없이 입력해 주세요.")
    		} else if(inputPw1 !== inputPw2) {
    			alert("비밀번호가 일치하지 않습니다.")
    		} else if(inputPw1.length <= 7 || inputPw1.length >= 16) {
    			alert("8~15자리 사이의 비밀번호를 입력해 주세요.")
    		} else if(Check_AlphaNumericSpecialUpperLower(inputPw1) === 1) {
    			alert("비밀번호는 숫자, 영문자, 특수문자를 혼용하여야 합니다.")
    		} else {
    			fetch("/checkaccount", {
    				method: "POST",
    				headers: {
                        "Content-Type": "application/json",
                        'Accept': 'application/json'
                    },
    				body: JSON.stringify({
    					businessnumber: inputId,
    					name: inputName,
    					password: inputPw1
    					})
    				})
    				.then(response => response.json())
    				.then(data => signupCheck(data))
    		}
    	}
    	
    	const submitAccount = () => {
    		let inputId = document.getElementById("id").value;
    		let inputName = document.getElementById("name").value;
    		let inputPw1 = document.getElementById("pw1").value;
    		let inputScm = document.getElementById("scm").value;
    		
    		fetch("/submitaccount", {
				method: "POST",
				headers: {
                    "Content-Type": "application/json"
                },
				body: JSON.stringify({
					businessnumber: inputId,
					name: inputName,
					password: inputPw1,
					scmName: inputScm
					})
				})
				.then(alert("회원가입을 환영합니다."))
    	}
    	
    	const signupCheck = (data) => {
    		if(data === undefined || data.businessnumber === null) {
    			submitAccount();
    			window.location.href = '/';
    		} else {
    			alert("이미 가입한 회원입니다.")
    		}
    	}
    </script>
</head>
<body>

<form name="submitform" action="" method="post">

		<table border=1 class="submit_table">
			<tr>
				<td colspan="2" align="center">
					<b>회원 가입</b>
				</td>
			</tr>
			
			<tr>
				<td>사업자 등록번호(ID)</td>
				<td>
					<input type="text" id="id" placeholder="사업자 등록번호를 입력해 주세요." size="28">
				</td>
			</tr>
			
			<tr>
				<td>사업자 이름</td>
				<td>
					<input type="text" id="name" placeholder="이름을 입력해 주세요." size="28">
				</td>
			</tr>
			
			<tr>
				<td>사업장</td>
				<td>
					<input type="text" id="scm" placeholder="ex) 쿠팡, 11st" size="28">
				</td>
			</tr>
			
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" id="pw1" placeholder="영문, 숫자, 특수문자 혼합 8~15자리" size="28">
				</td>
			</tr>
			
			<tr>
				<td>비밀번호 확인</td>
				<td>
					<input type="password" id="pw2" placeholder="비밀번호를 한번 더 입력해 주세요." size="28">
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="회원가입" onclick="clickSubmitBtn();" /> &nbsp;&nbsp;
					<a href="javascript:location.href='./'">로그인</a>
				</td>
			</tr>
		</table>
		
	</form>

</body>
</html>