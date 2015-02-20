      <div id="right-pane" class="grid_4 right-pane">

              <img src="resources/img/advert1.jpg" >
              <img src="resources/img/advert2.jpg" >
              <img src="resources/img/advert3.jpg" >
      </div> <!--grid_7 middle-pane--> 
</body>

    <c:choose>
        <c:when test="${not empty username}">
			<br>
			<br>
			<br>
			<nav class="navbar navbar-custom navbar-fixed-bottom" role="navigation">
			   <div>
			      <ul id="nav" class="nav navbar-nav navbar-custom">
			         <li <c:if test="${section=='Social Feed'}"> class="active" </c:if> >
			         	<a href="viewNewsFeed">Social Feed</a></li>
			         <li <c:if test="${section=='Calendar'}"> class="active" </c:if> >
			         	<a href="calendar">Calendar</a></li>
			         <li <c:if test="${section=='Friends'}"> class="active" </c:if> >
						<a href="friend">Friends</a></li>
			         <li <c:if test="${section=='Availability'}"> class="active" </c:if> >
			         	<a href="createAvailability">Availability</a></li>
			      </ul>
			   </div>
			</nav>        
        </c:when>
    </c:choose>
	</div>

<br>
<br>
<br>
<br>
<br>
</html>