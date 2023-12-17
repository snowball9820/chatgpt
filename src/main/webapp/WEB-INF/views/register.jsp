<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
</head>
<body>
<h1>회원가입</h1>
<form:form method="POST" modelAttribute="user">
    <table>
        <tr>
            <td>아이디:</td>
            <td><form:input path="username" /></td>
        </tr>
        <tr>
            <td>비밀번호:</td>
            <td><form:password path="password" /></td>
        </tr>
        <tr>
            <td>이름:</td>
            <td><form:input path="fullName" /></td>
        </tr>
        <tr>
            <td>핸드폰 번호:</td>
            <td><form:input path="phoneNumber" /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="회원가입" /></td>
        </tr>
    </table>
</form:form>
</body>
</html>