<!DOCTYPE HTML>
<html>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <!--Load Script and Stylesheet -->
    <script src="${context}/js/mobiscroll.core.js"></script>
    <script src="${context}/js/mobiscroll.scroller.js" type="text/javascript"></script>
    <script src="${context}/js/mobiscroll.datetime.js" type="text/javascript"></script>
    <script src="${context}/js/mobiscroll.scroller.android.js" type="text/javascript"></script>
    <script src="${context}/js/mobiscroll.scroller.ios.js" type="text/javascript"></script>
    <script src="${context}/js/mobiscroll.scroller.ios7.js" type="text/javascript"></script>

    <link href="/resources/css/mobiscroll.scroller.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/mobiscroll.scroller.ios.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/mobiscroll.scroller.ios7.css" rel="stylesheet" type="text/css" />
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
                    theme: "ios", mode: "scroller", lang: "", display: "bottom",animate: ""}));
            });
            $('#startDate').trigger('change');
        });
    </script>

<script language="JavaScript">
    function toggle(source) {
        checkboxes = document.getElementsByName('selectedFriends');
        for(var i=0, n=checkboxes.length;i<n;i++) {
          checkboxes[i].checked = source.checked;
        }
      }



function validateForm() {
    var errorCount = 0;
      document.getElementById('emptyStartDateError').style.display = "none";
      document.getElementById('emptyEndDateError').style.display = "none";        
      document.getElementById('dateRangeError').style.display = "none";        

    var startDate = document.forms["emptyAvailability"]["startDate"].value;
    var endDate = document.forms["emptyAvailability"]["endDate"].value;

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
  <div class="section-text black-link">
    <img id="edit-image" src="${context}/resources/img/edit.png">    
    <hr>

    <form:form modelAttribute="newAvailability" action="/createAvailability" onsubmit="return validateForm()">
    <table border="0" cellpadding="10" width="100%">
    <tr>
      <td align="top">
            <div class="startDateSection">
                    <div data-role="fieldcontain" class="demo-datetime">
                        <h4><label for="startDate">From: </label></h4>
                        <form:input style="width:100%;" type="text" path="startDate" id="startDate" class="datetimeField"/>
                        <div class="error-text">
                          <span id="emptyStartDateError" style="display: none;">Field cannot be left blank!</span>
                          <span id="dateRangeError" style="display: none;">Start date cannot be after end date!</span>
                        </div>
                    </div>
            </div>
      </td>
    </tr>
    <tr>
      <td align="top">
              <div class="endDateSection">
                      <div data-role="fieldcontain" class="demo-datetime">
                          <h4><label for="endDate">To: </label></h4>
                          <form:input style="width:100%;" type="text" path="endDate" id="endDate" class="datetimeField"/>
                          <div class="error-text">
                            <span id="emptyEndDateError" style="display: none;">Field cannot be left blank!</span>
                          </div>
                      </div>
              </div>
      </td>
      </tr>
</table>

      <b>Choose Friends</b>
      <br>
      <c:forEach var="friend" items='${friendList}'>
          <input type="checkbox" name="selectedFriends" value="${friend.emailAddress}">
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

      <br>
      <br>
      <input type="checkbox" onClick="toggle(this)" /> Toggle All Selections<br/>
   	  <form:errors path="*" cssClass="errorblock" element="div" />
  	  <br>
      <input type="submit" class="btn-accept btn btn-large btn-block" value="Create Availability" />

      
    </form:form>
  </div>
    <script type="text/javascript">
      var d = new Date();
      var day=(d.getDate()<10?'0':'') + d.getDate();
      var month=d.getMonth() + 1;
      var month=(month<10?'0':'') + month;
      var hours=(d.getHours()<10?'0':'') + d.getHours();
      var year=d.getFullYear();
      var minutes=(d.getMinutes()<10?'0':'') + d.getMinutes();

      currentDateTime = year+"-"+month+"-"+day+" "+hours+":"+minutes
      document.getElementById('startDate').value = currentDateTime;

      Date.prototype.addHours= function(h){
         this.setHours(this.getHours()+h);
         return this;
        }
      d.addHours(4);
      var day=(d.getDate()<10?'0':'') + d.getDate();
      var month=d.getMonth() + 1;
      var month=(month<10?'0':'') + month;
      var hours=(d.getHours()<10?'0':'') + d.getHours();
      var year=d.getFullYear();
      var minutes=(d.getMinutes()<10?'0':'') + d.getMinutes();

      currentDateTime = year+"-"+month+"-"+day+" "+hours+":"+minutes
      document.getElementById('endDate').value = currentDateTime;
    </script>



</body>
<%@ include file="/WEB-INF/views/footer.jsp" %>