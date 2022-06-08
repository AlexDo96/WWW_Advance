<%-- 
    Document   : person
    Created on : Mar 13, 2021, 7:43:39 PM
    Author     : TriPham
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="com.se.onetomany.sortutil.SortUtils"%>

<!DOCTYPE html >
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
    </head>
    <body>
        <h1>Records</h1>

        <c:url var="editImgUrl" value="/resources/img/edit.png" />
        <c:url var="deleteImgUrl" value="/resources/img/delete.png" />
        <c:url var="viewImgUrl" value="/resources/img/eye.png" />
        <c:url var="addUrl" value="/person/showFormForAdd" />
        <c:url var="viewUrl" value="/creditcard/list"/>

        <c:url var="Url" value="/person/search"/>

        <form:form action="${Url}" method="GET">
            Search customer: <input type="text" name="theSearchName" />

            <input type="submit" value="Search" class="add-button" />
        </form:form>
        <table style="border: 1px solid; width: 100%; text-align:center">
            
            <thead style="background:#d3dce3">
                <tr>
                    <th>Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Money</th>
                    <th colspan="2"></th>
                    <th>CC view</th>
                </tr>
            </thead>
            <tbody style="background:#ccc">
                <c:forEach items="${persons}" var="person">
                    <%--<c:url var="editUrl" value="/person/showFormForUpdate?personId=${person.id}" />
                    <c:url var="deleteUrl" value="/person/delete?personId=${person.id}" />--%>
                    <c:url var="editUrl" value="/person/showFormForUpdate">
                        <c:param name="personId" value="${person.id}" />
                    </c:url>					
                    <!-- construct an "delete" link with customer id -->
                    <c:url var="deleteUrl" value="/person/delete">
                        <c:param name="personId" value="${person.id}" />
                    </c:url>
                    <c:url var="viewUrl" value="/creditcard/list">
                        <c:param name="personId" value="${person.id}" />
                    </c:url>
                    <tr>
                        <td><c:out value="${person.id}" /></td>
                        <td><c:out value="${person.firstName}" /></td>
                        <td><c:out value="${person.lastName}" /></td>
                        <td><c:out value="${person.money}" /></td>
                        <td><a href="${editUrl}"><img src="${editImgUrl}"/> </a></td>
                        <td><a href="${deleteUrl}"><img src="${deleteImgUrl}"/></a></td>       
                        <td><a href="${viewUrl}"><img src="${viewImgUrl}"/></a></td>
                    </tr>


                </c:forEach>
            </tbody>
        </table>

        <c:if test="${empty persons}">
            No records found. 
        </c:if>

        <p><a href="${addUrl}">Create new record</a></p>

    </body>
</html>
