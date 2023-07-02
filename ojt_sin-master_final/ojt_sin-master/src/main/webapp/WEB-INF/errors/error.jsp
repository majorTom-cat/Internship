<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String statusCode = (String)request.getAttribute("statusCode"); %>
<!DOCTYPE html>
<html>
<head>

	<style>
		body {
			background-color: black;
			color: white;
			text-align: center;
		}
		

	</style>

<meta charset="UTF-8">
 <title>ERROR</title>
</head>
<body>
<div class="cover">
	<div><h1><%= statusCode %></h1></div>
    <h2><%= statusCode %> 에러가 발생하여 페이지를 표시할 수 없습니다.</h2>
    <h2>관리자에게 문의하시거나 잠시 후 다시 시도해 주세요.</h2>
</div>
</body>
</html>