<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="myfn" uri="http://samplefn"%>

    <script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <!--Load Script and Stylesheet -->
      <script src="${context}/js/mobiscroll.core.js"></script>
    <script src="${context}/js/mobiscroll.scroller.js" type="text/javascript"></script>
    <script src="${context}/js/mobiscroll.datetime.js" type="text/javascript"></script>
    <script src="${context}/js/mobiscroll.scroller.ios.js" type="text/javascript"></script>

    <link href="/resources/css/mobiscroll.scroller.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/mobiscroll.scroller.ios.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/availability.css" rel="stylesheet" type="text/css" />


    <SCRIPT LANGUAGE="JavaScript" SRC="${context}/js/date.js"></SCRIPT>

    <script type="text/javascript">
        $(function () {
            var curr = new Date().getFullYear();
            var opt = {
                'datetime': {
                    preset: 'datetime',
                    minDate: new Date(2014, 1, 1, 0, 0),
                    maxDate: new Date(2299, 12, 31, 0, 0),
                    stepMinute: 10,
                    dateFormat: 'yy-mm-dd',
                    timeFormat: 'HH:ii',
                    timeWheels: 'HHii'
                }
            }
            $('.startDateSection input').bind('change', function() {
                $('.datetimeField').scroller('destroy').scroller($.extend(opt['datetime'], {
                    theme: "ios", mode: "scroller", lang: "",animate: ""}));
            });
            $('#startDate').trigger('change');
        });
    </script>

<script language="JavaScript">
    function toggle(source) {
        checkboxes = document.getElementsByName('friendSharedList');
        for(var i=0, n=checkboxes.length;i<n;i++) {
          checkboxes[i].checked = source.checked;
        }
      }



function validateForm() {
    var errorCount = 0;
      document.getElementById('emptyStartDateError').style.display = "none";
      document.getElementById('emptyEndDateError').style.display = "none";        
      document.getElementById('dateRangeError').style.display = "none";        

    var startDate = document.forms["amendAvailability"]["startDate"].value;
    var endDate = document.forms["amendAvailability"]["endDate"].value;

    if (startDate == null || startDate == "") {
        document.getElementById('emptyStartDateError').style.display = "block";
        errorCount++;
    }
    if (endDate == null || endDate == "") {
        document.getElementById('emptyEndDateError').style.display = "block";        
        errorCount++;
    }

    if (compareDates(startDate,'yyyy-MM-dd HH:mm',endDate,'yyyy-MM-dd HH:mm') == 1){
        document.getElementById('dateRangeError').style.display = "block";
        errorCount++;
    }

    if (errorCount>0){
      alert("There are some errors which need addressing")
      return false;
    }

    return true;


}
  </script>
  <script>
      $(document).ready(function(){
        $(".showHideMessage").click(function(){
          $(".messages-block").toggle(1000);
          if ($(this).val() == "Show Messages") {
             $("#showHideMessage").prop('value', 'Hide Messages');
          }
          else{
             $("#showHideMessage").prop('value', 'Show Messages');
          }
        });
      });
  </script>
  

  <div class="section-text black-link">

    <!--SELECT AVAILABILITY - NOT OWNER-->
    <c:if test="${!isThisMyAvailability}">
      <div id="friend-availability-summary">
        <c:out value="${amendAvailability.ownerName}"/> is available

          <c:set var="dateTimeReversed" value="${amendAvailability.startDate}" />
          <fmt:parseDate value="${dateTimeReversed}" var="dateTimePretty" pattern="yyyy-MM-dd'T'HH:mm:ss.S" />
          <fmt:formatDate pattern="EEE dd MMM yyyy HH:mm" value="${dateTimePretty}" />

        <br> to 

          <c:set var="dateTimeReversed" value="${amendAvailability.endDate}" />
          <fmt:parseDate value="${dateTimeReversed}" var="dateTimePretty" pattern="yyyy-MM-dd'T'HH:mm:ss.S" />
          <fmt:formatDate pattern="EEE dd MMM yyyy HH:mm" value="${dateTimePretty}" />

      </div>

      <img id="question-image" src="${context}/resources/img/question.png">    
      <hr>

      <div id="acceptAvailability">
        <form:form modelAttribute="amendAvailability" action="/joinAvailability?id=${amendAvailability.id}">
          <input type="submit" class="btn-accept btn btn-large btn-block" value="Accept Availability" />
        </form:form>

        <form:form modelAttribute="amendAvailability" action="/declineAvailability?id=${amendAvailability.id}">
          <input type="submit" class="btn-decline btn btn-large btn-block" value="Decline Availability" />
        </form:form>

        <form:form modelAttribute="amendAvailability" action="/tentativeAvailability?id=${amendAvailability.id}">
          <input type="submit" class="btn-tentative btn btn-large btn-block" value="Tentative Availability" />
        </form:form>
      </div>
    </c:if>

    <br>
    <!--MESSAGES SECTION-->
    <img id="message-image" src="${context}/resources/img/message.png">    
    <hr>

    <input type="submit" id="showHideMessage" class="showHideMessage btn-accept btn btn-large btn-block" value="Show Messages" />
    <div class="messages-block">
      <table border="0" cellpadding="10" width="100%">
        <c:forEach var="message" items='${messagesForAvailability}'>
          <c:set var="dateTimeReversed" value="${message.dateTime}" />
          <fmt:parseDate value="${dateTimeReversed}" var="dateTimePretty" pattern="yyyy-MM-dd HH:mm" />




          <tr>
            <c:if test="${userName == message.sender}">
              <td></td>
              <td class="bubble-righthand">
                <div class="bubble-right">      
                  <div class="bubble-sender">
                    Me
                  </div>
                  <br>
                  <div class="bubble-message">
                    <c:out value="${message.text}"/>
                  </div>
                  <br>
                  <div class="bubble-datetime">
                    <fmt:formatDate pattern="EEE dd MMM yyyy HH:mm" value="${dateTimePretty}" />
                  </div>
                </div>
              </td>
            </c:if>
            <c:if test="${userName != message.sender}">
              <td class="bubble-lefthand">
                <div class="bubble-left">      
                  <div class="bubble-sender">
                    <c:out value="${message.sender}"/>
                  </div>
                  <br>
                  <div class="bubble-message">
                    <c:out value="${message.text}"/>
                  </div>
                  <br>
                  <div class="bubble-datetime">                
                    <fmt:formatDate pattern="EEE dd MMM yyyy HH:mm" value="${dateTimePretty}" />
                  </div>
                </div>
              </td>
              <td></td>
            </c:if>
          </tr>
        </c:forEach>

      </table>
    </div>

    <form:form modelAttribute="newMessage" action="/addMessage?id=${amendAvailability.id}">
      <form:input path="text" id="text" class="input-block-level" style="height:60px;width:100%;" placeholder="add your message here"/>
      <input type="submit" class="btn-accept btn btn-large btn-block" value="Add Message" />
    </form:form>

    <c:if test="${isThisMyAvailability}">
    <!--AMEND AVAILABILITY - OWNER ONLY-->
        <br>
        <img id="edit-image" src="${context}/resources/img/edit.png">    
        <hr>

        <form:form modelAttribute="amendAvailability" action="/amendAvailability?id=${amendAvailability.id}" onsubmit="return validateForm()">
        <table border="0" cellpadding="10" width="100%">
          <tr>
            <td align="top">
                  <div class="startDateSection">
                          <div data-role="fieldcontain" class="demo-datetime">
                              <h4><label for="startDate">From: </label></h4>
                              <form:input style="width:100%;" type="text" path="startDate" id="startDate" class="datetimeField" value="${amendAvailability.startDate}"/>
                              <font color="red">
                                <span id="emptyStartDateError" style="display: none;">Field cannot be left blank!</span>
                                <span id="dateRangeError" style="display: none;">Start date cannot be after end date!</span>
                              </font>
                          </div>
                  </div>
            </td>
          </tr>
          <tr>
            <td align="top">
                    <div class="endDateSection">
                            <div data-role="fieldcontain" class="demo-datetime">
                                <h4><label for="endDate">To: </label></h4>
                                <form:input style="width:100%;" type="text" path="endDate" id="endDate" class="datetimeField" value="${amendAvailability.endDate}"/>
                                <font color="red">
                                  <span id="emptyEndDateError" style="display: none;">Field cannot be left blank!</span>
                                </font>
                            </div>
                    </div>
            </td>
          </tr>
      </table>



          <b>Choose Friends</b>
          <br>
          <c:forEach var="friend" items='${friendsSharedAvailability}'>
            <c:choose>
              <c:when test="${friend.joined}">
                  <input type="checkbox" name="friendSharedList" value="${friend.email}" checked>
              </c:when>
              <c:otherwise>
                  <input type="checkbox" name="friendSharedList" value="${friend.email}">
              </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${not empty friend.facebookId}">
                  <img src="https://graph.facebook.com/<c:out value="${friend.facebookId}"/>/picture?type=square">    
                </c:when>
                <c:when test="${empty friend.facebookId}">
                  <img src="${context}/resources/img/avatar.jpg">    
                </c:when>
            </c:choose>
            <c:out value="${friend.name}"/>
            (<c:out value="${friend.availabilityStatus}"/>)

              <br>
          </c:forEach>

          <c:forEach var="friend" items='${unsharedFriends}'>
              <input type="checkbox" name="friendSharedList" value="${friend.emailAddress}">        
              <c:choose>
                <c:when test="${not empty friend.facebookId}">
                  <img src="https://graph.facebook.com/<c:out value="${friend.facebookId}"/>/picture?type=square">    
                </c:when>
                <c:when test="${empty friend.facebookId}">
                  <img src="${context}/resources/img/avatar.jpg">    
                </c:when>
              </c:choose>
              <c:out value="${friend.name}"/>
              (not shared with)
              <br>
          </c:forEach>


          <br>
          <br>
          <input type="checkbox" onClick="toggle(this)" /> Toggle All Selections<br/>
       	  <form:errors path="*" cssClass="errorblock" element="div" />
      	  <br>
          <input type="submit" class="btn-accept btn btn-large btn-block" value="Update Availability" />

          
        </form:form>
        <form:form modelAttribute="amendAvailability" action="/deleteAvailability?id=${amendAvailability.id}">

          <input type="submit" class="btn-decline btn btn-large btn-block" value="Delete Availability" />
        </form:form>

    </div>
</c:if>
</body>
<%@ include file="/WEB-INF/views/footer.jsp" %>