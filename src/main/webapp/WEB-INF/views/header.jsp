<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.util.ResourceBundle" %> 

<%
    Boolean isAuthenticated = (Boolean) session.getAttribute("IS_AUTHENTICATED");
    String token = (String) session.getAttribute("OAUTH_TOKEN");
    String facebookId = (String) session.getAttribute("FACEBOOK_ID");
    String userName = (String) session.getAttribute("USER_NAME");
%>

<head>
	<title>socialeggbox</title>
    <meta charset="utf-8" />
    <link rel="icon" href="${context}/resources/img/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" href="${context}/resources/img/favicon.ico" type="image/x-icon">

    <style type="text/css">
        .demo {
            display: none;
        }
    </style>

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <link href="${context}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${context}/resources/css/grid.css" type="text/css" media="screen"> 
    <link rel="stylesheet" href="${context}/resources/css/style.css" type="text/css" media="screen">


</head>

  <div class="clearfix">


    <body>

      <% if(isAuthenticated != null && isAuthenticated) { %>
              <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
                 <div>
                    <table class="table-header">
                        <tr>
                          <td>
                              <a class="logo" href="viewNewsFeed">
                                social eggbox
                              </a>
                            </td>
                            <td ><div class="welcome"> Welcome <b><%=userName%>!</b></div>
                                <fmt:bundle basename="propertyPlaceholder">
                                  <a class="logout" id="logout" href="https://www.facebook.com/logout.php?access_token=<%=token%>&next=<fmt:message key="facebookLogoutUrl"/>"> Logout</a>
                                </fmt:bundle>
                            </td>
                            <td class="user-picture">
                                <img id="user-picture" src="https://graph.facebook.com/<%=facebookId%>/picture?type=square">
                            </td>
                        </tr>
                    </table>
                 </div>
              </nav>        

    <% }else {%>
       <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
         <div>
            <table class="table-header"> 
                <tr>
                  <td>
                      <div class="logo">social eggbox</div> 
                   </td>
                 </tr>
            </table>
          </div>
        </nav>            
    <% } %>
    <br>
    <br>
    <br>
    <br>




  <div class="grid_8 middle-pane">
    <c:choose>
        <c:when test="${not empty message}">
          <div class="alert alert-success" role="alert">
            ${message}
          </div>
        </c:when>
    </c:choose>
