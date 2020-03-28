<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <sec:authorize access="hasRole('COACH')">
        <%@include file="navBarCoach.jsp"%>
    </sec:authorize>
    
    <sec:authorize access="hasRole('PLAYER')">
        <%@include file="navBarPlayer.jsp"%>
    </sec:authorize>


<h1>Welcome Mr. ${user.surname}</h1>
    <sec:authorize access="hasRole('PLAYER')">
        This text is only visible to a user Player
        <br/>Player Name: ${user.firstName} ${user.surname}
        <br/>
        <a href="\player\viewResults"> View Tests </a>
        
        <br/> <br/>
    </sec:authorize>
	
    <sec:authorize access="hasRole('COACH')">
        This text is only visible to an Coach
        <a href="\coach">Insert</a>
        <br/>
        <a href="<c:url value="/coach/adminpage.html" />">Admin Page</a>
        <br/>
    </sec:authorize>

    <a href="<c:url value="/logout" />">Logout</a>

</body>
</html>