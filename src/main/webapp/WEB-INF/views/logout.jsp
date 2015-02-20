<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<br><br>
    <c:choose>
        <c:when test="${not empty message}">
        	<c:out value="${message}" />
        </c:when>
    </c:choose>

    <br><br>

	Login using Facebook          
	<a href="https://www.facebook.com/dialog/oauth? client_id=378183975657505&redirect_uri=http://facebook.derek.com:8080/facebook&auth_type=rerequest& scope=email">
        <img src="${context}/resources/img/facebook-connect.png" alt="Connect with Facebook" width="50%"/>
      </a>

	</div>
    </body>
</html>