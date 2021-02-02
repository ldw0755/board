<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>관리자 - Admin</h1>
	<!-- member : CustomUser 객체의 필드명 -->
	<!-- principal : 사용자를 증명하는 user(CustomUser => MemberVO)를 직접 접근 -->
	<p>principal : <sec:authentication property="principal"/></p>
	<p>MemberVO : <sec:authentication property="principal.member"/></p>
	<p>사용자 아이디 : <sec:authentication property="principal.member.userid"/></p>
	<p>사용자 이름 : <sec:authentication property="principal.member.userName"/></p>
	<p>사용자 권한 리스트 : <sec:authentication property="principal.member.authList"/></p>
	<p>principal : <sec:authentication property="principal"/></p>
	<form action="/logout" method="post">
		<button>로그아웃</button>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</body>
</html>