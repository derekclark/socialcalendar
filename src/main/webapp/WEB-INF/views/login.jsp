<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>



        <br><br>
        <fmt:bundle basename="propertyPlaceholder">
        	Login using Facebook          
        	<a href="<fmt:message key="facebookLoginUrl"/> ">
                <img src="${context}/resources/img/facebook-connect.png" alt="Connect with Facebook" width="50%"/>
            </a>
        </fmt:bundle>
	</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
