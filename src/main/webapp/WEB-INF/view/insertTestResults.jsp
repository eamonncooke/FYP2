<%-- 
    Document   : insertTestResults
    Created on : 22 Mar 2020, 17:01:42
    Author     : cooke
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
    <%@include file="navBarPlayer.jsp"%>
    <center>
        

    <h3>Enter Test Results</h3>

    <form:form method="POST" action="/player/insertNewTest" modelAttribute="testResult">
        
        <form:input path="testId" type="hidden" value="${testingID}" readonly="true"/>
        
        
        <p><form:label path="time">Time test was completed in (sec)</form:label>
            <form:input path="time"/></p>
        
        <p><form:label path="date">Insert Date</form:label>
            <form:input type="date" path="date"/></p>
        
        <p><input type="submit" value="Submit"/></p>
    </form:form>

 </center></body>
</html>