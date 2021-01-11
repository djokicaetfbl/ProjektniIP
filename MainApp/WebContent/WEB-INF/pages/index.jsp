<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="userBean" type="org.unibl.etf.beans.UserBean" scope="session"></jsp:useBean> 

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
      #map {
        height: 512px;
        width: 512px;
      }
    </style>

<!--   <meta http-equiv="refresh" content="30"> -->
<!-- <link href="styles/style.css" type="text/css" rel="stylesheet">  -->
	<link href="styles/weather.css" type="text/css" rel="stylesheet">
	<link href="styles/iconSocialNetworks.css" type="text/css" rel="stylesheet">
	<link href="styles/index.css" type="text/css" rel="stylesheet">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	<script src="https://kit.fontawesome.com/8b41a163ea.js"></script>
	<script src="scripts/index.js"></script>
	
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">       
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
 	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 	
	<link href="styles/sharePost.css" type="text/css" rel="stylesheet">
	<link href="styles/post.css" type="text/css" rel="stylesheet">
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCEn6jQCDOpwcNLwvDEHrS1SSHx-GSCM3w"></script>    
         
	
<title>Application</title>
</head>
<!-- <body onload="loadWeather()" style="min-height: 75rem; padding-top: 4.5rem;">  -->
<body onload="loadData()">
 
<nav class="navbar fixed-top navbar navbar-dark bg-primary" role="navigation">
<div class="container">
	<a href="javascript:refreshCurrentPage()"><i class="fa fa-home fa-2x " style="color: #ffffff;" aria-hidden="true"></i></a>		
	<span style="font-family: Arial, Helvetica, sans-serif; font-size: 25px; font-weight: bold; color: #ffffff;" >Information about hazards</span>
    <form class="form-inline my-2 my-lg-0" action="?action=logout" method="post">
		<input class="btn btn-pill btn-light navbar-btn" style="font-weight: bold;"  type="submit" value="Logout" />
	</form>
</div>
</nav>


<div id="page" class="container-fluid" style="margin-top: 70px">
	<div class="row">
		<div class="col-md-3 col-xl-3">
	<img src=<%=userBean.getUser().getPicture()%> alt="Avatar" class="md-avatar rounded-circle size-3" style="width: 150px; height: 150px; border-radius: 50%; border: solid 2px #1a1aff;">
		<h5>
			<%
				out.println(userBean.getUser().getFirstName() + " " + userBean.getUser().getLastName());
			%>
		</h5>
		<h6>
			<%
				out.println("Broj prijava na sistem: " + userBean.getUser().getSignInCounter());
			%>
		</h6>
		
	<!--	<div class="list-group" id="list-groupEN">	-->

	<div class="all-en-posts row" id="listGroupEN" style="max-width: 150px; max-height: 500px; padding: 5px;" ></div>
  	<br>
					
		</div>
				
<div class="col-md-6 gedf-main">
                <div class="card-socialStatus gedf-card">
                <form id="postForm" action="javascript:sharePost()" method="post">
                    <div class="card-header">
                        <ul class="nav nav-tabs card-header-tabs" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="posts-tab" data-toggle="tab" href="#posts" role="tab" aria-controls="posts" aria-selected="true" onclick="shareLinkButton()" >Link</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="images-tab" data-toggle="tab" role="tab" aria-controls="images" aria-selected="false" href="#images" onclick="shareImageButton()">Images</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="videos-tab" data-toggle="tab" role="tab" aria-controls="videos" aria-selected="false" href="#videos" onclick="shareVideoButton()">Videos</a>
                            </li>
                        </ul>
                    </div>                 
                    <div class="card-body" id="hazardPostArea"></div>
                     <div id="selectDangerCategories" required></div>
                    <div id="mapAndShare"></div>
                    <hr>                   
                </div>
				</form>     			     			     			     			 
				<div class="all-user-posts row" id="all-user-posts"></div><br>
				<div class="all-rss-posts row" id="all-rss-posts"></div>
			</div>

		<div class="col-md-3 col-xl-3">
		<input type="text" id="countryA2C" name="countryA2C" hidden="true" value=<%= userBean.getUser().getCountry() %>>
		<input type="text" id="countryRegion" name="countryRegion" hidden="true" value=<%= userBean.getUser().getRegion() %>>				
		
		
				<div class="container-fluid px-1 px-md-4 py-5 mx-auto">
    	<div class="row d-flex justify-content-center px-3">
        <div class="card-weather">
            <h2 class="ml-auto mr-4 mt-3 mb-0" id="firstWeatherCity" name="firstWeatherCity">-</h2>
            <p class="ml-auto mr-4 mb-0 med-font" id="firstWeatherSky" name="firstWeatherSky">-</p>
            <h1 class="ml-auto mr-4 large-font" id="firstWeatherTemperature" name="firstWeatherTemperature">-&#176;</h1>
            <p class="time-font mb-0 ml-4 mt-auto" id="firstWeatherTime" name = "firstWeatherTime" >-</p>
            <p class="ml-4 mb-4" id="firstWeatherDate" name="firstWeatherDate">-</p>
        </div>
    	</div>
		</div>
		
		  
				<div class="container-fluid px-1 px-md-4 py-5 mx-auto">
    	<div class="row d-flex justify-content-center px-3">
        <div class="card-weather">
            <h2 class="ml-auto mr-4 mt-3 mb-0" id="secondWeatherCity" name="secondWeatherCity">-</h2>
            <p class="ml-auto mr-4 mb-0 med-font" id="secondWeatherSky" name="secondWeatherSky">Snow</p>
            <h1 class="ml-auto mr-4 large-font" id="secondWeatherTemperature" name="secondWeatherTemperature">-&#176;</h1>
            <p class="time-font mb-0 ml-4 mt-auto" id="secondWeatherTime" name = "secondWeatherTime" >-</p>
            <p class="ml-4 mb-4" id="secondWeatherDate" name="secondWeatherDate">-</p>
        </div>
    	</div>
		</div>
		
				<div class="container-fluid px-1 px-md-4 py-5 mx-auto">
    	<div class="row d-flex justify-content-center px-3">
        <div class="card-weather">
            <h2 class="ml-auto mr-4 mt-3 mb-0" id="thirdWeatherCity" name="thirdWeatherCity">-</h2>
            <p class="ml-auto mr-4 mb-0 med-font" id="thirdWeatherSky" name="thirdWeatherSky">Snow</p>
            <h1 class="ml-auto mr-4 large-font" id="thirdWeatherTemperature" name="thirdWeatherTemperature">-&#176;</h1>
            <p class="time-font mb-0 ml-4 mt-auto" id="thirdWeatherTime" name = "thirdWeatherTime" >-</p>
            <p class="ml-4 mb-4" id="thirdWeatherDate" name="thirdWeatherDate">-</p>
        </div>
    	</div>
		</div>
		
		
		</div>
	</div>
</div>
 											
</body>
</html>