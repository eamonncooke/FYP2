<%-- 
    Document   : viewTestResultsList
    Created on : 27 Mar 2020, 17:36:24
    Author     : cooke
--%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Results</title>
    <sec:authorize access="hasRole('COACH')">
        <%@include file="navBarCoach.jsp"%>
    </sec:authorize>
    
    <sec:authorize access="hasRole('PLAYER')">
        <%@include file="navBarPlayer.jsp"%>
    </sec:authorize>
        <style>
            thead input {
                width: 100%;
                padding: 3px;
                box-sizing: border-box;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script> 
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/plug-ins/1.10.20/sorting/date-dd-MMM-yyyy.js"></script> 
        <script>
            $(document).ready(function() {
                $('#tableP').DataTable({
                    columnDefs: [
                        { type: 'date-dd-mmm-yyyy', targets: 3 }
                    ]
                });
            } );

            function checkDelete(){
            return confirm('Are you sure?');
            }
        </script>
        <center><h1>List of Players</h1>
            <sec:authorize access="hasRole('PLAYER')">
                <a href="\player\insertTest">Insert New Test</a>
                <br/>
                <a href="\player\viewFitnessTestChart">Chart</a>
            </sec:authorize>
        


        <table class="display compact hover stripe" id="tableP">
            <thead>
                <tr>
                    <th align="left">Name</th>
                    <th align="left">Position</th>
                    <th align="left">Time</th>
                    <th align="left">Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${testList}" var="test"> 
                    <tr>
                        <td>${test.playerID.authUserId.surname}, ${test.playerID.authUserId.firstName}</td>
                        <td>${test.playerID.postion}</td>
                        <td>${test.time} seconds</td>
                        <td><fmt:formatDate type="date"  pattern="dd-MMM-yyyy" value="${test.date}"/></td>
                       
                    </tr>
                </c:forEach>
            </tbody>
        </table></center>
    </body>
</html>