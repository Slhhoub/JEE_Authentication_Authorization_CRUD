
<%@ page import="java.sql.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String  LoginUser="";
if(session.getAttribute("login") == null){
	response.sendRedirect("index.jsp");
}else{
	  LoginUser=session.getAttribute("login").toString();
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>Insert title here</title>
</head>
<body>
    <div class="container">
       <nav class="navbar navbar-expand-lg bg-body-tertiary mb-5">
		  <div class="container-fluid">
		    <a class="navbar-brand" href="home.jsp">AuthJEE</a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
		    <div class="collapse navbar-collapse" id="navbarSupportedContent">
		      <ul class="navbar-nav m-auto mb-2 mb-lg-0">
		        <li class="nav-item">
		          <a class="nav-link active" aria-current="page" href="home.jsp">Home</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link active" aria-current="page" href="#">Table Users</a>
		        </li>
		       <li class="nav-item">
		          <a class="nav-link" href="role.jsp">Role</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="#">Permission</a>
		        </li>
		      </ul>
		      <div class="d-flex mt-2">
		          <h6 class="nav-link text-black" >Login  : <%= LoginUser %></h6>
			      <form method="POST" action="logout">
			        <input type="submit" value="logout" >
			      </form>
		      </div>
		    </div>
		  </div>
		</nav>
		
		<% if(request.getAttribute("errorMessage") != null){ %>
			<div class="alert alert-danger" role="alert">
			 <%= request.getAttribute("errorMessage") %>
			</div>
		<% } %>
		<form method="POST" action="editUser">
		     <h1 class="mb-3 mt-2">Edit User</h1>
		 		<% if(request.getAttribute("errorMessage") != null){ %>
				<div class="alert alert-danger" role="alert">
				 <%= request.getAttribute("errorMessage") %>
				</div>
				<% }
		 		
		 		String id = request.getParameter("id");
		 		
		 		 String url = "jdbc:mysql://localhost:3306/jee_etudiant";
			        String user = "root";
			        String pwd = "";
			        try {
			            Class.forName("com.mysql.cj.jdbc.Driver");
			            Connection con = DriverManager.getConnection(url, user, pwd);
			            if (con != null) {
			                PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE id = ?");
			                pst.setString(1, id);
			                ResultSet rs = pst.executeQuery();
			                while (rs.next()) {
			        %>
			        <input type="hidden" name="id" value=" <%= rs.getString(1)  %>" />
				      <div class="mb-3">
					    <label for="exampleInputEmail1" class="form-label">Username</label>
					    <input type="text" name="username" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"  value=" <%= rs.getString(2)  %> ">
					  </div>
					  <div class="mb-3">
					    <label for="exampleInputEmail1" class="form-label">Email address</label>
					    <input type="email" name="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" value="<%= rs.getString(3)  %>">
					  </div>
					  <div class="mb-3">
					    <label for="exampleInputPassword1" class="form-label">Password</label>
					    <input type="password" name="password" class="form-control" id="exampleInputPassword1">
					  </div>
					  <button type="submit" class="btn btn-primary">edit</button>
			    </form>
		     <%
			                } 
			                con.close();
			            }
			        } catch (Exception e) {
			            System.out.println(e);
			        }
		 		
		 		%>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>