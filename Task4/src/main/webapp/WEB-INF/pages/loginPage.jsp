<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login/Register</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/WEB-INF/styles/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <form id="loginForm" method="POST" action="">
        <section class="loginSection">
            <c:if test="${not empty param.errorMsg}">
                <c:choose>
                    <c:when test="${param.errorMsg == 'wrong.name.error'}">
                        <p style="color:red">User with such name doesn't exist</p>
                    </c:when>
                    <c:when test="${param.errorMsg == 'wrong.password.error'}">
                        <p style="color:red">Wrong password</p>
                    </c:when>
                    <c:when test="${param.errorMsg == 'wrong.user.error'}">
                        <p style="color:red">User with such name already exists</p>
                    </c:when>
                </c:choose>
            </c:if>
            <div class="element">
                <input type="email" name="email" placeholder="E-mail" required>
            </div>
            <div class="element">
                <input type="password" name="password" placeholder="Password" required>
            </div>
            <div class="element">
                <input class="loginButton" type="button" name="login" value="Log in"
                       onclick="loginBtnClick()">
            </div>
            <div class="element">
                <input class="registerButton" type="button" name="login" value="Sign up"
                       onclick="loginBtnClick()">
            </div>
            <script type="text/javascript">
                function loginBtnClick() {
                    document.getElementById("loginForm").action = <c:url value="/profile"/>
                        document.getElementById("loginForm").submit()
                }

                function registerBtnClick() {
                    document.getElementById("loginForm").action = <c:url value="/login"/>
                        document.getElementById("loginForm").submit()
                }
            </script>
        </section>
    </form>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>