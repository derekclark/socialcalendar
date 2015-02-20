<!DOCTYPE HTML>
<html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<link href="/resources/css/friends.css" rel="stylesheet" type="text/css" />

  <div class="section-text black-link">

<img id="friends-image" src="${context}/resources/img/friends.png">    
<hr>

<div class="section-header">Your Friend List</div>
	<c:choose>
	    <c:when test="${not empty friendsList}">
			<br>
			<div class="alert alert-success">
				<c:forEach var="friend" items='${friendsList}'>
					<c:out value="${friend}"/>
					<br>
				</c:forEach>
			</div>
	    </c:when>
	</c:choose>

<br>
        <c:forEach var="friend" items='${friendList}'>
          <c:choose>
            <c:when test="${not empty friend.facebookId}">
              <img src="https://graph.facebook.com/<c:out value="${friend.facebookId}"/>/picture?type=square">    
            </c:when>
            <c:when test="${empty friend.facebookId}">
              <img src="${context}/resources/img/avatar.jpg">    
            </c:when>
          </c:choose>
          <c:out value="${friend.name}"/>
          <br>
        </c:forEach>
<br><br>
<img id="add-friend-image" src="${context}/resources/img/add-friend.png">    
<hr>
    <form:form modelAttribute="friend" action="addFriend" onSubmit='return validate()'>
      <label for="email">Friend email address </label>
      <form:input path="email" id="email" class="input-block-level" style="height:60px;width:100%;" placeholder="enter new friend's Email address here"/>
      <div class="error-text">
          <span id="invalidEmailAddress" style="display: none;">Invalid Email Address</span>
      </div>

      <form:errors path="email" cssclass="error"></form:errors>
      <br/>
      <input type="submit" class="btn-accept btn btn-large btn-block" value="Add Friend" />
    </form:form>


        <c:choose>
            <c:when test="${not empty friendRequestList}">
    
              <img id="question-image" src="${context}/resources/img/question.png">    
              <hr>

              <div class="section-header">You have friend requests</div>

              <div class="alert alert-success" align="left">
                      <table border="0" cellpadding="5">
                          <tr>
                              <th align="left">Requester</th>
                              <th><th>
                          </tr>
                          <c:forEach var="friend1" items="${friendRequests}">
                              <tr>
                                  <td><c:out value="${friend1.requesterEmail}" /></td>
                                  <td><input type=button class="btn-accept" onClick="location.href='acceptFriendRequest?id=${friend1.id}'" value="Accept">
                       						<input type=button class="btn-decline" onClick="location.href='declineFriendRequest?id=${friend1.id}'" value="Decline">
                                  </td>
                              </tr>
                          </c:forEach>
                      </table>
              </div>
            </c:when>
        </c:choose>
  </div>

</div>

