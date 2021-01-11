<%@page import="org.unibl.etf.controller.Controller"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="styles/style.css" type="text/css" rel="stylesheet">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	<script src="https://kit.fontawesome.com/8b41a163ea.js"></script>
	<script src="scripts/login.js"></script>

<title>Sign In</title>
</head>
<body>
	<form method="POST" action="?action=login">
		 <div class="container">
		<!--  	<i class="fa fa-lock" aria-hidden="true"></i> -->
		<br><br><br><br>
		<p class="text-center" style="color: #3333ff; font-size: 25px; ">Sign in</p>
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<span class="badge badge-primary" style="font-size: 20px;">Username</span>
					<!--<label class="label control-label">User name</label>   -->
					<input type="text" class="form-control" id="username" name="username" >
					<!-- <label class="label control-label">Password</label> -->
					<span class="badge badge-primary" style="font-size: 20px;">Password</span>
					<input type="password" class="form-control" id="password" name="password" >
					<button type="submit" class="btn btn-primary btn-lg btn-block">Login</button>
				</div>								
				<div class="col-md-3"></div>
			</div>
				<p class="text-center" style="padding: 10px; color: #3366ff; font-size: 25px;">Are you new? <a class="btn btn-primary" href="?action=signUp" role="button">Sign up</a>
		</div>
		
				
	 					<%
						if(session != null && (session.getAttribute("notification") != null) && Controller.flagLogin){
					 	%>
							<div class="myAlert-bottom alert alert-success">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>Success!</strong> Log In ...
							</div>
							<script>
							  $(".myAlert-bottom").show();
							  setTimeout(function(){
							    $(".myAlert-bottom").hide(); 
							  }, 2000);
							</script>
					<% 
					} else {
					%>
							<div class="myAlert-bottom alert alert-danger">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>Error!</strong> Wrong username or password.
							</div>
							<script>
							  $(".myAlert-bottom").show();
							  setTimeout(function(){
							    $(".myAlert-bottom").hide(); 
							  }, 2000);
							</script>						
					<%
					}
					%>			

	</form>

	
</body>
</html>





