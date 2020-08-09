<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<meta charset="utf-8">
<title>Users</title>
</head>
<body>
	<br>
	<div class="container col-md-10 col-md-offset-1">
	
		<!-- Static navbar -->
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Show Room</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
              
			  <li class="dropdown">
			    <a href="${pageContext.request.contextPath}/show" class=" dropdown-toggle" type="button" id="menu1" data-toggle="dropdown">Users
			    <span class="caret"></span></a>
			    <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
			    	
				      <li role="presentation"><a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/showUsers">All Users</a></li>
				     
			      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">CSS</a></li>
			      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">JavaScript</a></li>
			      <li role="presentation" class="divider"></li>
			      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">About Us</a></li>
			    </ul>
			  </li>
              
              
              <li><a href="${pageContext.request.contextPath}/about">About</a></li>
              <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
        
        
            </ul>
        
        	<ul class="nav navbar-nav navbar-right">
            	<form:form cssClass="navbar-form navbar-right" action="${pageContext.request.contextPath}/searchUser" method="POST">
				    <div class="form-group">
				        <input type="text" class="form-control" name="motCle" value="${motCle}" placeholder="UserName">
				    </div>
				    <button type="submit" class="btn btn-default">
				        <span class="glyphicon glyphicon-search"></span>
				    </button>
				</form:form>              
            </ul>
             
  	         <ul class="nav navbar-nav navbar-right">
              <li class="active">
	             	<form:form method="POST" action="${pageContext.request.contextPath}/logout">
	              		<input type="submit" value="Sign Out" class="btn btn-link" style="margin-top: 8px;"><span class="sr-only">(current)</span>
	              	</form:form>
	          </li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

	
		<div class="panel panel-info">
			<div class="panel-heading"><h3 class="text-center">List of Users</h3></div>
			<div class="panel-body">
				<br>
				<table class="table table-striped">
					<tr class="warning">
						<th class="text-center">ID</th>
						<th class="text-center">UserName</th>
						<th class="text-center">First Name</th>
						<th class="text-center">Last Name</th>
						<th class="text-center">Email</th>
						<th class="text-center">Roles</th>
							
							<th></th>
							<th></th>
						
						
					</tr>
					
					<c:forEach items="${listUsers}" var="u">
						<tr>
							<td class="text-center">${u.id}</td>
							<td class="text-center">${u.userName}</td>
							<td class="text-center">${u.firstName}</td>
							<td class="text-center">${u.lastName}</td>
							<td class="text-center">${u.email}</td>
							<td class="text-center">
								
								    <select  id="myRoles"  class="form-control" autofocus> 
							            <c:forEach items="${u.roles}" var="role">
							          
							            		<option value="${role.name}">${role.name}</option>
							            	
							            </c:forEach> 
							        </select> 
								  
							</td>
								
							
								
							<td class="text-center">
								<a type="submit" class="btn btn-default" href="${pageContext.request.contextPath}/updateFormUser?id=${u.id}">Update</a>
							</td>
							<td class="text-center">
								<a type="submit" 
								onclick="return confirm('are you sure ?')"
								class="btn btn-default" href="${pageContext.request.contextPath}/deleteUser?id=${u.id}">Delete</a>
							</td>
						
						</tr>
					</c:forEach>
					
				</table>

				<div class="text-right">
						
						<a type="submit" class="btn btn-success btn-block" style="width: 50%;margin: 0 auto;" href="${pageContext.request.contextPath}/">Return</a>
					
				</div>
				


			</div>
		</div>
	</div>
</body>
</html>