<%-- 
    Document   : stravaActivities
    Created on : 1 Apr 2020, 13:12:00
    Author     : cooke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
       

            <%@include file="navBarPlayer.jsp"%>
        
    <h1>Strava Activities</h1>
    <a href="${authUrl}">Connect your Strava account</a>
    <form:form action="${authUrl}">
        <input type="button"  value="Connect your Strava account">
            <!-- <input type="submit" name="submitBtn" value="Proceed"> -->
      </form:form>  
</body>
</html>
