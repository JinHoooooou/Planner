<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.ArrayList, com.kh.model.vo.Plan, java.util.Collections, java.util.Comparator, java.sql.Date"%>

<%
ArrayList<Plan> list = (ArrayList<Plan>) session.getAttribute("planList");

ArrayList<Plan> listKeyWord = (ArrayList<Plan>) session.getAttribute("planListKeyWord");
String userId = (String) session.getAttribute("userId");
	String contextPath = request.getContextPath();
	int count = 0;
	int nCount = 0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="css/plan.css">
<script src="js/plan.js"></script>
<style>
	.plan-complete {
		text-decoration: line-through;
		text-decoration-thickness: 3px; 
	}
	a {
		text-decoration: none;
	}
</style>
</head>
<body>

	<header>
		<h1>
			<b>Plan 작성하기</b>
		</h1>
    	<form action="/plan/list" method="GET">
    	<input type="hidden" name="userId" value="validUserId0">
    	<input type="submit" value="로그인" style="width: 80px;">
    	</form>


</body>
</html>