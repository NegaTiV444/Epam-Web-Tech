<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login/Register</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <h2>Login</h2>
    <form id="loginForm" method="POST" action="<c:url value="/profile"/>">
        <section class="loginSection">
            <c:if test="${not empty param.errorMsg}">
                <p style="color:red">${param.errorMsg}</p>
            </c:if>
            <div class="element">
                <label>Name</label>
                <input type="text" name="name" placeholder="Name" required>
            </div>
            <div class="element">
                <label>Password</label>
                <input type="password" name="password" placeholder="Password" required>
            </div>
            <input type="submit" value="submit">
        </section>
    </form>
    <h2>Registration</h2>
    <form id="registerForm" method="POST" action="<c:url value="/login"/>">
        <section class="loginSection">
            <c:if test="${not empty param.errorMsg}">
                <p style="color:red">${param.errorMsg}</p>
            </c:if>
            <div class="element">
                <label>Name</label>
                <input type="text" name="name" placeholder="Name" required>
            </div>
            <div class="element">
                <label>Password</label>
                <input type="password" name="password" placeholder="Password" required>
            </div>
            <input type="submit" value="submit">
        </section>
    </form>
</main>
<jsp:include page="footer.jsp"/>
</body>
<script type="text/javascript">

    function loginBtnClick() {
        alert("log");
        document.getElementById("loginForm").action = <c:url value="/profile"/>
        document.getElementById("loginForm").submit()
    }

    function registerBtnClick() {
        alert("reg");
        document.getElementById("loginForm").action = <c:url value="/login"/>
        document.getElementById("loginForm").submit()
    }
</script>
</html>