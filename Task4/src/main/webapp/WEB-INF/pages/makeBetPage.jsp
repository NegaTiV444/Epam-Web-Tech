<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="race" type="epam.webtech.model.race.Race" scope="request"/>

<!DOCTYPE html>
<html>
<head>
    <title>Make bet</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/WEB-INF/styles/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="row-between">
        <span>DATE: ${race.date}</span>
        <span>STATUS: ${race.status}</span>
    </div>
    <div>
        <form method="post" action="<c:url value="/makebet?raceid=${race.id}"/>">
            <h2>Choose horse</h2>
            <select id="horse" name="horse">
                <c:forEach var="horse" items="${race.horses}">
                    <option value="${horse.id}">${horse.name}</option>
                </c:forEach>
            </select>
            <span>Amount of money:</span>
            <c:if test="${not empty param.errorMsg}">
                <c:if test="${param.errorMsg == 'notenoughmoney.error'}">
                    <p style="color:red">Not enough money</p>
                </c:if>
            </c:if>
            <input type="number" name="amount">
            <input type="submit" value="Make bet">
        </form>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>