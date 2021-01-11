<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="styles/style.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	<script src="https://kit.fontawesome.com/8b41a163ea.js"></script>
	<script src="scripts/updateUserProfile.js"></script>
	<script src="scripts/login.js"></script>
<title>Sign Up</title>
</head>
<body>
	<form method="POST" action="?action=signUp">
		<div class="container">
		<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<label class="label control-label">First Name</label>
			<input type="text" class="form-control" id="firstName" name="firstName" required="required" maxlength="100" placeholder="First Name">
			<label class="label control-label">Last Name</label>
			<input type="text" class="form-control" id="lastName" name="lastName" required="required" maxlength="100" placeholder="Last Name">
			<label class="label control-label">Email</label>
			<input type="text" class="form-control" id="email" name="email" required="required" placeholder="Email">
			<label class="label control-label">Username</label>
			<input type="text" class="form-control" id="username" name="username" required="required" maxlength="20" placeholder="Username">
			<label class="label control-label">Password</label>
			<input type="password" class="form-control" id="firstPassword" name="firstPassword" pattern=".{8,}" required title="8 characters minimum" maxlength="128" placeholder="Password">
			<label class="label control-label">Confirm password</label>
			<input type="password" class="form-control" id="secondPassword" name="secondPassword" pattern=".{8,}" required title="8 characters minimum" maxlength="128" placeholder="Confirm Password">
			<button type="submit" class="btn btn-primary btn-lg btn-block">Sign Up</button>
		</div>
		<div class="col-md-3"></div>
		</div>
		</div>
		 			<%
						if(session != null && (session.getAttribute("notification") != null)){
					%>
							<div class="alert alert-info" role="alert" style="text-align: center; bottom: 0px;">
					<%
							out.print(session.getAttribute("notification"));
					%>
							</div>
					<% 
					}else {					
							out.print(session.getAttribute("notification"));		
					}
					%>
	</form>
</body>
</html>