<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

    <script type="text/javascript" src="assets/js/loader_info.js" ></script>

</head>
<body>
	<%@ page import="model.User" %>
	<%! User user; %>
	<%  user = (User)session.getAttribute("UserLogged"); %>
	
	<%!
		boolean isLogged(){
		
			if(user != null)
			{
				return true;
			} 
			else
				return false;
		}
	%>

	<% if (isLogged()) { %>
		<h1>Benvenuto Sig. <%=user.getNome()%></h1>
	<% } else { %>
		<h1>Per accedere al sito devi fare il login</h1>
	<% } %>
</body>
</html>