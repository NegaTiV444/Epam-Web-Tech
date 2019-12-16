<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="race" type="epam.webtech.model.race.Race" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="authorityLvl" type="java.lang.Integer" scope="session"/>

<div class="card">
    <div class="row-between">
        <span>DATE: ${race.date}</span>
        <span>STATUS: ${race.status}</span>
    </div>
    <div>
        <ul>
            <c:forEach var="horse" items="${race.horses}">
                <li>${horse.name}</li>
            </c:forEach>
        </ul>
    </div>
    <c:choose>
        <c:when test="${race.status.priority eq 2}">
            <h3 class="race-action">WINNER: ${race.winnerHorse.name}!</h3>
        </c:when>
        <c:when test="${race.status.priority eq 1}">
            <c:choose>
                <c:when test="${authorityLvl eq 2}">
                    <div>
                        <form action="<c:url value="/races/finish"/>" method="post">
                            <select id="winnerHorse" name="winnerHorse">
                                <c:forEach var="horse" items="${race.horses}">
                                    <option>${horse.name}</option>
                                </c:forEach>
                                <input type="submit" value="Finish race">
                            </select>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div>
                        <a href="<c:url value="/makebet?raceid=${race.id}"/>">
                            <button class="race-action">Make bet</button>
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <h3 class="race-action">Race already started</h3>
        </c:otherwise>
    </c:choose>
</div>
