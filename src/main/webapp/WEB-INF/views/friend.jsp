<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<link href="/resources/css/friends.css" rel="stylesheet" type="text/css" />

<script>
    function validateEmail(email) { 
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }

    function validate(){
      document.getElementById('invalidEmailAddress').style.display = "none";
      $("#result").text("");
      var email = $("#beFriendedEmail").val();
      if (validateEmail(email)) {
        return true;
      } else {
        document.getElementById('invalidEmailAddress').style.display = "block";
        return false;
      }
      
    }

    $("form").bind("submit", validate);

</script>
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
    <form:form modelAttribute="newFriend" action="addFriend" onSubmit='return validate()'>
      <label for="beFriendedEmail">Friend email address </label>
      <form:input path="beFriendedEmail" id="beFriendedEmail" class="input-block-level" style="height:60px;width:100%;" placeholder="enter new friend's Email address here"/>
      <div class="error-text">
          <span id="invalidEmailAddress" style="display: none;">Invalid Email Address</span>
      </div>

      <form:errors path="beFriendedEmail" cssclass="error"></form:errors>
      <br/>
      <input type="submit" class="btn-accept btn btn-large btn-block" value="Add Friend" />
    </form:form>


        <c:choose>
            <c:when test="${not empty friendRequestsMadeOnMe}">
    
              <img id="question-image" src="${context}/resources/img/question.png">    
              <hr>

              <div class="section-header">You have friend invitations</div>

              <div class="alert alert-success" align="left">
                      <table border="0" cellpadding="5">
                          <tr>
                              <th align="left">Requester</th>
                              <th><th>
                          </tr>
                          <c:forEach var="friend" items="${friendRequestsMadeOnMe}">
                              <tr>
                                  <td><c:out value="${friend.requesterEmail}" /></td>
                                  <td><input type=button class="btn-accept" onClick="location.href='acceptFriendRequest?id=${friend.friendId}'" value="Accept">
                       				  <input type=button class="btn-decline" onClick="location.href='declineFriendRequest?id=${friend.friendId}'" value="Decline">
                                  </td>
                              </tr>
                          </c:forEach>
                      </table>
              </div>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${not empty friendRequestsMadeByMe}">

              <img id="question-image" src="${context}/resources/img/question.png">
              <hr>

              <div class="section-header">You have outstanding friend requests</div>

              <div class="alert alert-success" align="left">
                      <table border="0" cellpadding="5">
                          <tr>
                              <th align="left">Requester</th>
                              <th><th>
                          </tr>
                          <c:forEach var="friend" items="${friendRequestsMadeByMe}">
                              <tr>
                                  <td><c:out value="${friend.beFriendedEmail}" /></td>
                              </tr>
                          </c:forEach>
                      </table>
              </div>
            </c:when>
        </c:choose>
  </div>

</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>