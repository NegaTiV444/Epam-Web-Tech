
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/style.css">


<tags:error errorCode="404" errorMessage="${requestScope.errorMessage}"/>