<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Calories management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<section>

    <%--<hr>--%>
        <%--<c:if test="${!empty updated}">--%>
            <%--<p>Meal successfully updated!</p>--%>
        <%--</c:if>--%>

        <%--<c:if test="${!empty created}">--%>
            <%--<p>Meal successfully created!</p>--%>
        <%--</c:if>--%>

        <%--<c:if test="${!empty deleted}">--%>
            <%--<p>Meal successfully deleted!</p>--%>
        <%--</c:if>--%>
    <%--<hr>--%>

    <%--<form:form method="post" action="save">--%>
        <%--<table >--%>
            <%--<tr>--%>
                <%--<td>DateTime : </td>--%>
                <%--<td><form:input path="dateTime" type="datetime-local" name="dateTime"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>Description :</td>--%>
                <%--<td><form:input path="description" type="text" name="description"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>Calories :</td>--%>
                <%--<td><form:input path="calories" type="number" name="calories"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td> </td>--%>
                <%--<td><input type="submit" value="Save" /></td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</form:form>--%>

    <br>
    <br>

    <hr>
        <a href="${pageContext.request.contextPath}/meals/add"><button>Add Meal</button></a>
    <hr>

    <h3>Meals</h3>
    <%--<form method="post" action="${pageContext.request.contextPath}/meal/filter">--%>
        <%--<dl>--%>
            <%--<dt>From Date:</dt>--%>
            <%--<dd><input type="date" name="startDate" value="${param.startDate}"></dd>--%>
        <%--</dl>--%>
        <%--<dl>--%>
            <%--<dt>To Date:</dt>--%>
            <%--<dd><input type="date" name="endDate" value="${param.endDate}"></dd>--%>
        <%--</dl>--%>
        <%--<dl>--%>
            <%--<dt>From Time:</dt>--%>
            <%--<dd><input type="time" name="startTime" value="${param.startTime}"></dd>--%>
        <%--</dl>--%>
        <%--<dl>--%>
            <%--<dt>To Time:</dt>--%>
            <%--<dd><input type="time" name="endTime" value="${param.endTime}"></dd>--%>
        <%--</dl>--%>
        <%--<button type="submit">Filter</button>--%>
    <%--</form>--%>

        <form:form method="POST" modelAttribute="allMeals" action="${pageContext.request.contextPath}/meals/filteredlist">
        <dl>
            <dt>From Date:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">Filter</button>
        </form:form>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${allMeals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="${pageContext.request.contextPath}/meals/edit/${meal.id}">Update</a></td>
                <td><a href="${pageContext.request.contextPath}/meals/delete/${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>

        <c:if test="${!empty filteredMeals}">
            <c:forEach items="${filteredMeals}" var="filteredMeal">
                <jsp:useBean id="filteredMeal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                <tr class="${filteredMeal.exceed ? 'exceeded' : 'normal'}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            ${fn:formatDateTime(filteredMeal.dateTime)}
                    </td>
                    <td>${filteredMeal.description}</td>
                    <td>${filteredMeal.calories}</td>
                    <td><a href="${pageContext.request.contextPath}/meals/edit/${filteredMeal.id}">Update</a></td>
                    <td><a href="${pageContext.request.contextPath}/meals/delete/${filteredMeal.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </c:if>

    </table>
</section>
</body>
</html>