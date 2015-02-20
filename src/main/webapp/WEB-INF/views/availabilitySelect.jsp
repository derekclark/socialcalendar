<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<br><br><br><br><br>
	<div class="section-text confirm-availability">
	    <div class="alert alert-success" role="alert">
			<table class="section-format">
				<tr>
					<td><c:out value="${availability.ownerName}"/></td>
				</tr>
				<tr>
					<td>from <c:out value="${availability.startDate}"/></td>
				</tr>
				<tr>
					<td>to <c:out value="${availability.endDate}"/></td>
				</tr>
			</table>
			<table class="section-format">
				<tr>
					<td colspan="3">Confirm you are available too?</td>
				</tr>			
				<tr>
					<td>
						<a class="confirm-availability-yes" style="color:#07dc06;" href="joinAvailability?availabilityId=${availability.availabilityId}">Yes</a>
					</td>
					<td>
						<a class="confirm-availability-maybe" style="color:orange;" href="tentativeAvailability?availabilityId=${availability.availabilityId}">Maybe</a>
					</td>
					<td>
						<a class="confirm-availability-no" style="color:red;" href="declineAvailability?availabilityId=${availability.availabilityId}">No</a>
					</td>
				</tr>
			</table>				
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/views/footer.jsp" %>