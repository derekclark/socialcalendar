<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="myfn" uri="http://samplefn"%>

	<c:choose>
	    <c:when test="${not empty alertMessage}">
			<br>
			<div class="alert alert-success">
				<c:out value="${alertMessage}"/>
			</div>
	    </c:when>
	</c:choose>

	<br>
	<div align="center" class="section-header">
		Messages
	</div>
	<br>
	<div class="section-text">
		 <table class="section-format">
			<td>
				<tr>

	        	<c:set var="currentDate" value="0" />
		        <c:forEach var="message" items="${messages}">
				    <fmt:parseDate value="${message.messageDate}" pattern="yyyy-MM-dd HH:mm" var="strDate" />
				    <fmt:formatDate value="${strDate}" pattern="dd/MM/yy" var="strDate2"/>
		        	<c:if test = "${strDate2!=currentDate }">
		             	</tr>
		             	<tr>
		             		<td>
		             			<b><fmt:formatDate value="${strDate}" type="date" /></b>
	             			</td> 
						<c:set var="currentDate" value="${strDate2}" />
		            </c:if>
		            <tr>
		            	<td>
				            <c:out value="${message.senderName}" />
				            <br>
				            <c:out value="${message.message}" />
				            <c:out value="${message.availability.startDate}"/>
				            to
				            <c:out value="${message.availability.endDate}"/>

						</td>
		        </c:forEach>
			</tr>
		</td>
		</table>
	</div>


</body>
<%@ include file="/WEB-INF/views/footer.jsp" %>