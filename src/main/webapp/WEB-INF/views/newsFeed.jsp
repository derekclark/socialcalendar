<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="myfn" uri="http://samplefn"%>
<link rel="stylesheet" href="resources/css/newsfeed.css" type="text/css" media="screen">

	<div class="section-text black-link">
	        <c:forEach var="newsFeedItem" items="${newsFeedLines}">
	        	<a href="<c:out value="${newsFeedItem.url}"/>">

	        	<c:if test="${newsFeedItem.hasResponded}">

					<table width="100%" frame="box" class="newsfeed-item">
		    			<tr>
		             		<td class="newsfeed-date newsfeed-fixed-text" colspan="3">
								    <b>
								    	<c:out value="${newsFeedItem.dateLine}" />
				   				    </b>
		         			</td> 
		         			<td></td>
		         			<td></td>		         					         			
	    	       		</tr>

			            <tr>
			            	<td class="newsfeed-fixed-text">Available:</td>
			            	<td class="newsfeed-variable-text">${newsFeedItem.ownerName}</td>
			            	<td class="newsfeed-spacer"></td>
			            	<td><div class="newsfeed-status-<c:out value="${newsFeedItem.myAcceptanceStatus}"/>">Status</div></td>
			            	<td class="newsfeed-end-of-display"></td>
						</tr>

			        	<c:set var="haveDisplayedFriend" value = "false"/>

				        <c:forEach var="invitedFriend" items="${newsFeedItem.invitedFriendsStatus}">						
				     		<tr>
				     			<c:choose>
					     			<c:when test="${haveDisplayedFriend eq false}">
						     			<c:set var="haveDisplayedFriend" value = "true"/>
						     			<td class="newsfeed-fixed-text">Invited:</td>
						     		</c:when>
						    		<c:otherwise> 		
										<td></td>
									</c:otherwise>
								</c:choose>

				     			<td class="newsfeed-variable-text">
					     				${invitedFriend.userName}
					     		</td>
					     		<td>
										<img class="newsfeed-indicator" src="${context}/resources/img/<c:out value="${invitedFriend.acceptanceStatus}"/>-indicator.png">
					     		</td>
					     		<td></td>
					     		<td></td>
				     		</tr>
			     		</c:forEach>

			     		<tr class="latest-message">
			     			<td class="message-viewnews"  colspan="5"> 
			     				<c:if test="${not empty newsFeedItem.latestMessage.sender}">
      				                <img id="message-viewnews-image" src="${context}/resources/img/message.png">    
									<div class="latest-message-sender"><c:out value="${newsFeedItem.latestMessage.sender}"/></div> 
									<div class="latest-message-datetime">wrote on <c:out value="${newsFeedItem.latestMessage.dateTime}"/></div>
					     			<div class="latest-message-text"><c:out value="${newsFeedItem.latestMessage.text}"/></div>
					     		</c:if>
			     			</td>
			     		</tr>
					</table>
				</c:if>
	        	<c:if test="${!newsFeedItem.hasResponded}">
					<table width="100%" frame="box" class="newsfeed-item">
						<tr>
							<td class="newsfeed-image">
				                <img src="https://graph.facebook.com/<c:out value="${newsFeedItem.ownerFacebookId}"/>/picture?type=square">
							</td>
							<td class="unresponded-availability">
						        	<c:out value="${newsFeedItem.ownerName}"/> 
						        	is available <br>
						        	<c:out value="${newsFeedItem.startDateTime}"/>
						        	to <br>
						        	<c:out value="${newsFeedItem.endDateTime}"/>
							</td>

						</tr>
					</table>	        	
				</c:if>
				</a>
			  <br>
	        </c:forEach>
	</div>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
