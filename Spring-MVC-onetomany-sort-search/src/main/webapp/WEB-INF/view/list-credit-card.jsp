<%-- 
    Document   : list-credit-card
    Created on : Apr 26, 2021, 8:00:39 PM
    Author     : Thang
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${person.firstName} ${person.lastName}</h1>
        <h2>List of Credit Card</h2>

        <c:url var="editImgUrl" value="/resources/img/edit.png" />
        <c:url var="deleteImgUrl" value="/resources/img/delete.png" />
        <c:url var="addUrl" value="/creditcard/showFormForAdd" />
        <c:url var="addUrl" value="/creditcard/add?personId=${person.id}" />
        <c:url var="personUrl" value="/person/list" />
        <c:url var="addCcUrl" value="/creditcard/showFormForAdd?personId=${person.id}" />
        <table style="border: 1px solid; width: 50%; text-align:center">
            <thead style="background:#d3dce3">
                <tr>
                    <th>CC Type</th>
                    <th>CC Number</th>
                    <th colspan="3"></th>
                </tr>
            </thead>
            <tbody style="background:#ccc">

                <c:if test="${!empty person.creditCards}">
                    <c:forEach items="${person.creditCards}" var="creditCard">

                        <tr>
                            <td><c:out value="${creditCard.type}" /></td>
                            <td><c:out value="${creditCard.number}" /></td>

                            <c:url var="editCcUrl" value="/creditcard/showFormForUpdate?personId=${person.id}&creditCardId=${creditCard.id}" />
                            <c:url var="deleteCcUrl" value="/creditcard/delete?personId=${person.id}&creditCardId=${creditCard.id}" />
                            <td><a href="${editCcUrl}"><img src="${editImgUrl}"/></a></td>
                            <td><a href="${deleteCcUrl}"><img src="${deleteImgUrl}"/></a></td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty person.creditCards}">
                    <tr>                       
                        <td>N/A</td>
                        <td>N/A</td>
                        <c:url var="addCcUrl" value="/creditcard/showFormForAdd?personId=${person.id}" />
                        <td><a href="${addCcUrl}">+</a></td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        <p><a href="${addCcUrl}"}">Add Credit Card</a></p>
        <p><a href="${personUrl}"}">Person List</a></p>
    </body>
</html>
