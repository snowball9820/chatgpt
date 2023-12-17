<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원목록</title>
</head>
<body>
<h1>회원목록</h1>
<table>
    <tr>
        <th>아이디</th>
        <th>비밀번호</th>
        <th>이름</th>
        <th>핸드폰 번호</th>
    </tr>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.fullName}</td>
            <td>${user.phoneNumber}</td>
        </tr>
    </c:forEach>
</table>
<a href="/home">홈페이지 이동</a>
</body>
</html>