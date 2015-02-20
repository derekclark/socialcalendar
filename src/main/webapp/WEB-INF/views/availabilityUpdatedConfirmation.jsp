<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<br><br><br><br><br>
	<div class="section-text black-link">

        <div class="alert alert-success" role="alert">

		<table class="section-format">
			<tr>
				<td><c:out value="${updateMessage}" /></td> 
			</tr>
		</table>
		<br><br>
		<table class="section-format">

			<c:choose>
	            <c:when test="${not empty friendNameList}">

						<tr>
							<td>A message has been sent to the following friends:</td> 
						</tr>

					<c:forEach var="friendName" items="${friendNameList}">
						<tr>
							<td><c:out value="${friendName}"/></td> 
						</tr>
					</c:forEach>
				</c:when>
	            <c:when test="${empty friendNameList}">
						<tr>
							<td>You did not select any friends to send the message to.</td> 
						</tr>
				</c:when>
			</c:choose>
		</table>
		<br><br>
		<table class="section-format">

			<tr>
				<td><a href="viewNewsFeed">OK</a></td> 
			</tr>

		</table>
	</div>
		<br><br>

	</div>
</body>
<%@ include file="/WEB-INF/views/footer.jsp" %>