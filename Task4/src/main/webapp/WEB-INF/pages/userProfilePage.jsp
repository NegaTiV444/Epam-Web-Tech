<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="user" type="epam.webtech.model.user.User" scope="request"/>
<jsp:useBean id="bets" type="java.util.ArrayList<epam.webtech.model.bet.Bet>" scope="request"/>

<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/WEB-INF/styles/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <h2>Profile</h2>
    <div>Name: ${user.name}</div>
    <div>Total money: ${user.bank}</div>
    <div>
        <c:choose>
            <c:when test="${bets.size() eq 0}">
                <h1>You have no bets</h1>
            </c:when>
            <c:otherwise>
                <h1>Your bets:</h1>
                <c:forEach var="bet" items="${bets}">
                    <tags:bet bet="${bet}"/>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>