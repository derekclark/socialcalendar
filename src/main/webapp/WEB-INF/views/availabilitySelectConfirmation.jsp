<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<div class="section-text black-link">
   	    <img id="ok-image" src="${context}/resources/img/ok.png">    
   	    <hr>
    	<br><br><br>
	    <div class="alert alert-success" role="alert">
			<table class="section-format confirm-availability">
				<tr>
					<td><c:out value="${message}"/>
				</tr>
				<tr>
					<td><c:out value="${friend}"/></td> 
				</tr>

			</table>
			<form action="viewNewsFeed">
            	<input type="submit" class="btn-accept btn btn-large btn-block" value="OK" />
            </form>

		</div>
	</div>
</body>
<%@ include file="/WEB-INF/views/footer.jsp" %>