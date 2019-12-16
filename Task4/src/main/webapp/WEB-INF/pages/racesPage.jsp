<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="authorityLvl" type="java.lang.Integer" scope="session"/>
<jsp:useBean id="races" type="java.util.ArrayList<epam.webtech.model.race.Race>" scope="request"/>

<!DOCTYPE html>
<html>
<head>
    <title>Races</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/WEB-INF/styles/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <h2>Races</h2>
    <c:forEach var="race" items="${races}">
        <tags:race race="${race}"/>
    </c:forEach>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>