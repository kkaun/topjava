<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Meal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<section>
    <h2><a href="${pageContext.request.contextPath}/meals/home">Home</a></h2>
    <hr>

    <%--<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>--%>
    <%--<form method="post" action="/meal/create">--%>
    <%--<input type="hidden" name="id" value="${meal.id}">--%>
    <%--<dl>--%>
    <%--<dt>DateTime:</dt>--%>
    <%--<dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>--%>
    <%--</dl>--%>
    <%--<dl>--%>
    <%--<dt>Description:</dt>--%>
    <%--<dd><input type="text" value="${meal.description}" size=40 name="description"></dd>--%>
    <%--</dl>--%>
    <%--<dl>--%>
    <%--<dt>Calories:</dt>--%>
    <%--<dd><input type="number" value="${meal.calories}" name="calories"></dd>--%>
    <%--</dl>--%>
    <%--<button type="submit">Save</button>--%>
    <%--<button onclick="window.history.back()">Cancel</button>--%>
    <%--</form>--%>


    <form:form method="POST" modelAttribute="meal" action="${pageContext.request.contextPath}/meals/edit/${meal.id}">
        <table>
            <tr>
                <td>DateTime : </td>
                <td><form:input path="dateTime" type="datetime-local"/></td>
            </tr>
            <tr>
                <td>Description :</td>
                <td><form:input path="description" type="text"/></td>
            </tr>
            <tr>
                <td>Calories :</td>
                <td><form:input path="calories" type="number" /></td>
            </tr>
            <tr>
                <td> </td>
                <td><input type="submit" value="Save" /></td>
            </tr>
        </table>
    </form:form>

    <button onclick="window.history.back()">Cancel</button>


</section>
</body>
</html>