<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="userBean" type="org.unibl.etf.beans.UserBean" scope="session"></jsp:useBean> 
<html>
<head>
<meta charset="UTF-8">
<title>User profile</title>
	<link href="styles/style.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	<script src="https://kit.fontawesome.com/8b41a163ea.js"></script>
	<script src="scripts/updateUserProfile.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body onload="init()">
	<!-- 
	HTML forms provide three methods of encoding.

    application/x-www-form-urlencoded (the default)
    multipart/form-data
    text/plain
        
    use multipart/form-data when your form includes any <input type="file"> elements
    otherwise you can use multipart/form-data or application/x-www-form-urlencoded but application/x-www-form-urlencoded will be more efficient
    
    multipart/form-data is significantly more complicated but it allows entire files to be included in the data.
    text/plain is introduced by HTML 5 and is useful only for debugging
	
	 -->
	<form method="POST" action="?action=updateProfil" enctype="multipart/form-data" > <!-- multipart/form-data naglasiti u servletu da kroz request saljes fajl :S  https://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet -->
		<div class="container">
		<div class="row">
		<div class="col-md-3">
			<%	out.println("<input type='text' class='form-control' id='username' maxlength='100' name='username' hidden='true' value='"+userBean.getUser().getUsername()+"'>"); 	%>
		</div>
		<div class="col-md-6">
			<label class="label control-label" >First Name</label>
			<%	out.println("<input type='text' class='form-control' id='firstName' required='required' maxlength='100' name='firstName' value='"+userBean.getUser().getFirstName()+"'>"); 	%>
			<label class="label control-label">Last Name</label>			
			<% 	out.println("<input type='text' class='form-control' id='lastName' name='lastName' required='required' maxlength='100' value='"+userBean.getUser().getLastName()+"' >"); 	%>
			<label class="label control-label">Email</label>
			<% 	out.println("<input type='text' class='form-control' id='email' name='email' required='required' value='"+userBean.getUser().getEmail()+"' >"); 	%>
			<label class="label control-label">Picture</label>
			  <div class="custom-file">
    			<input type="file" class="custom-file-input" id="pictureFile" name="pictureFile">
    			<label class="custom-file-label" for="inputGroupFile01">Choose picture</label>
 			 </div> <br><br>
 			 

			
			<div class="custom-control custom-checkbox">
  			<input type="checkbox" class="custom-control-input" id="emergencyNotification" name="emergencyNotification" checked>
  			<label class="custom-control-label" for="emergencyNotification">Emergency notification</label>
			</div>

			<button type="submit" class="btn btn-primary btn-lg btn-block">Sign Up</button>
			</div>
			<div class="col-md-3">
			<label class="label control-label">Country</label>
			<select class="form-control" id="country" name="country"  required="required">
			<option value="" selected disabled hidden>Choose here</option>
			</select>		
			<label class="label control-label">Region</label>
			<select class="form-control" id="region" name="region"  required="required">
			<option value="" selected disabled hidden>Choose here</option>
			</select>			
			<label class="label control-label">City</label>
			<select class="form-control" id="city" name="city"  required="required">
			<option value="" selected disabled hidden>Choose here</option>
			</select> 
			<div class="container">     
  			<img class="userProfilePicture" id="userProfilePicture" src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTWRolIN3VPIBYPi8lhOfMwbCYfy0ez7cgIyeR1qJFrO0OAKd-3&usqp=CAU" class="img-thumbnail" alt="Profile picture"> 
			</div>
			<label class="label control-label" hidden="true">Country FLAG</label>
			<input type="text" id="countryFlag" name="countryFlag" hidden="true">
			
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
					}
					%>
	</form>
	
</body>
</html>