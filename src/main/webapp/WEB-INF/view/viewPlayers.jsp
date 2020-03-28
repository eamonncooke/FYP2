<%-- 
    Document   : viewPlayers
    Created on : 22 Mar 2020, 12:48:41
    Author     : cooke
--%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Players</title>
    <%@include file="navBarCoach.jsp"%>
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
        <script>
            $(document).ready(function() {
                $('#tableP').DataTable();
            } );
        
            function checkDelete(){
                return confirm('Are you sure?');
            }
        </script>
        <center><h1>List of Players</h1>
        <a href="\coach\add">Insert</a>


        <table class="display compact hover stripe" id="tableP">
            <thead>
                <tr>
                    <th align="left">ID</th>
                    <th align="left">First Name</th>
                    <th align="left">Surname</th>
                    <th align="left">Position</th>
                    <th align="left">Date Of Birth</th>
                    <th align="left">Height</th>
                    <th align="left">Weight</th>
                    <th align="left">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${playerList}" var="player"> 
                    <tr>
                        <td>${player.playerId}</td>
                        <td>${player.authUserId.firstName}</td>
                        <td>${player.authUserId.surname}</td>
                        <td>${player.postion}</td>
                        <td><fmt:formatDate type="date"  pattern="dd-MMM-yyyy" value="${player.dob}"/></td>
                        <td>${player.height} cm</td>
                        <td>${player.weight} kg</td>
                        <td>
                            <a href="\coach\edit?id=${player.playerId}">Edit</a>
                            <a href="\coach\delete?id=${player.playerId}" onclick="return checkDelete()">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table></center>
    </body>
</html>
