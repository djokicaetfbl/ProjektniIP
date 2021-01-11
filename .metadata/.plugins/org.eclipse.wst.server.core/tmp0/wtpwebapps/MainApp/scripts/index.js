var myBattutaApiKey = "c3dd144e449f000c5a260a6aae408262";
var myOpenWeatherApiKey = "1d3a557be243131e44cb4a44b97c65d5"; 
var rssFeeds = [];
var geographicLatitude = 0;
var geographicLongitude = 0;

function loadData() {
	loadWeather();
	fillDangerousCategoriesSeceltBox();
	shareLinkButton();	
	loadRSS();
	getCurrentUser();
	getAllUsers();
	loadUsersPost();
    emergencyNotificationInterval = setInterval(() => {
    	loadEmergencyNotification();
    }, 10000); 
    loadPostsInterval = setInterval(() => {
    	loadUsersPost();
    }, 60000); 
}

function refreshCurrentPage(){
	window.location.reload();
}

function loadWeather(){
	var countryA2C = document.getElementById("countryA2C").value;
	var countryRegion = document.getElementById("countryRegion").value;	
	var callback = "cb";
	var url = `http://battuta.medunes.net/api/city/${countryA2C}/search/?region=${countryRegion}&key=${myBattutaApiKey}&callback=cb`;
	
	let e = document.createElement("script");
	e.src =  url;
	document.body.appendChild(e);
	
	window[callback] = (cities) => {
		var citiesLength = cities.length;
		var firstRN = Math.floor(Math.random() * citiesLength);
		var secondRN = Math.floor(Math.random() * citiesLength);
		var thirdRN = Math.floor(Math.random() * citiesLength);
		
		while(secondRN === firstRN){
			secondRN = Math.floor(Math.random() * citiesLength);
		}
		
		while(thirdRN === firstRN || thirdRN == secondRN){
			thirdRN = Math.floor(Math.random() * citiesLength);
		}
		
		if(citiesLength === 1){
			for(c of cities){
				getTemperatureForCity(c.city, text)
			}
		} else if (citiesLength === 2){
			let tmp = 0;
			for(c of cities){
				if(tmp === 0){
					getTemperatureForCity(c.city, "firstWeather");			
				} else if (tmp === 1){
					getTemperatureForCity(c.city, "secondWeather");
				}
				tmp++;
			}
		} else {
			let tmp = 0;
			for(c of cities){
				if(tmp === firstRN){
					getTemperatureForCity(c.city, "firstWeather");
				} else if(tmp === secondRN){
					getTemperatureForCity(c.city, "secondWeather");
				} else if(tmp === thirdRN){
					getTemperatureForCity(c.city, "thirdWeather");
				}
				tmp++;
			}
		}
	}
}

function getTemperatureForCity(city, text){
	
	var request = new XMLHttpRequest();

	
	 request.onload = function(){
	 var obj = JSON.parse(this.response);
	 if (request.status >= 200 && request.status < 400) {
	 let cityWeather = obj.weather[0].main;
	 let temp = obj.main.temp;
	 
	 document.getElementById(text + "Sky").innerHTML = cityWeather;
	 document.getElementById(text + "Temperature").innerHTML = temp+" °C";
	 document.getElementById(text + "City").innerHTML = city;
	 document.getElementById(text + "Date").innerHTML = formatAMPMDATE();
	 document.getElementById(text + "Time").innerHTML = formatAMPMTIME();
	 
	 }
	 else{
	  console.log("The city doesn't exist! Kindly check");
	 }
	}
	request.open("GET", `http://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${myOpenWeatherApiKey}&units=metric`, true);
	request.send(null);
}

function formatAMPMDATE() {
	var d = new Date(),
	    minutes = d.getMinutes().toString().length == 1 ? '0'+d.getMinutes() : d.getMinutes(),
	    hours = d.getHours().toString().length == 1 ? '0'+d.getHours() : d.getHours(),
	    ampm = d.getHours() >= 12 ? 'pm' : 'am',
	    months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
	    days = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
	return days[d.getDay()]+' '+months[d.getMonth()]+' '+d.getDate()+' '+d.getFullYear();
	}

function formatAMPMTIME() {
	var d = new Date(),
	    minutes = d.getMinutes().toString().length == 1 ? '0'+d.getMinutes() : d.getMinutes(),
	    hours = d.getHours().toString().length == 1 ? '0'+d.getHours() : d.getHours(),
	    ampm = d.getHours() >= 12 ? 'pm' : 'am',
	    months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
	    days = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
	return hours+':'+minutes+ampm;
	}


function loadRSS(){
	var request = new XMLHttpRequest();
	
	request.onreadystatechange = function () {
		if((request.readyState == 4) && (request.status == 200)){
			rssFeeds = JSON.parse(request.responseText);

			let allPosts = document.getElementById("all-rss-posts");
			allPosts.innerHTML = "";
			
		    rssFeeds.forEach(rs => {
		        rs.creationTime = new Date(rs.creationTime);
		    });
		    
		    rssFeeds.sort(function(a,b){
		    	return b.creationTime - a.creationTime;
		    });
			
		    
		    Array.from(rssFeeds).forEach( rss => {
				let node = document.createElement('div');
				node.innerHTML = `
				<div class="card">
					<div class="card-body">
						<h5 class="card-title"> <a href="#"><b>${rss.title}</b></a>.</h5>
						
						        <span class="text-muted pull-right">
                                    <small class="text-muted">${formatDate(rss.creationTime)} (rss)</small>
                                </span>
						<p class="card-text">${rss.description}</p>
						<a href=${rss.link} target='_blank'>${rss.link}</a>
						<p align="right" class="authorParagraph">Author: ${rss.author}</p>
					</div>
				</div> <br>
		        `;
				allPosts.appendChild(node);
			});
		}
	}
	
	request.open("GET", "api/rss/",true);
	request.send();
}

function shareLinkButton(){
	document.getElementById("hazardPostArea").innerHTML = `
		                    <div class="tab-pane fade show active" id="posts" role="tabpanel" aria-labelledby="posts-tab">
                                <div class="form-group">
                                    <label class="sr-only" for="messageLink">post</label>
                                    <textarea class="form-control" id="message" rows="3" placeholder="Share link" data-post-type="2"></textarea>
                                </div>
								<div class="google-map" id="mapArea" style="padding: 10px;"></div>
                            </div>
	`;
    document.getElementById("selectDangerCategories").innerHTML = `
        <label style="font-size: 24px;">Select danger categories</label>
    	<select id="dangerselect" class="custom-select" multiple style="font-size: 16px;">
        </select>
    `;
    
    loadDangerousGroup();
    loadMapAndShare();
   
}

function shareImageButton(){
	document.getElementById("hazardPostArea").innerHTML = `
		                            <div class="tab-pane fade show active" id="images" role="tabpanel" aria-labelledby="images-tab">
                                <div class="form-group">
                                	<label class="sr-only" for="messageForPicture">post</label>
                                    <textarea class="form-control" id="message" rows="3" placeholder="Enter some text" data-post-type="3"></textarea>                                  
                                    <hr>
                                    <!-- <img src="https://cdn1.vectorstock.com/i/1000x1000/27/15/flowers-border-frame-vector-1382715.jpg"
                                         class="rounded mx-auto d-block" alt="..." style=" padding: 10px; width: 512px; height: 256px;"> -->
                                    <div class="custom-file">
                                        <input type="file" class="custom-file-input" id="customImageFile" accept="image/*" multiple>                                       
                                        <label class="custom-file-label" for="customFile">Upload image</label>
                                    </div>	
                                    <!-- <input type="file" class="form-control" id="customImageFile" multiple/> -->
                                </div>
                               <div class="google-map" id="mapArea" style="padding: 10px;"></div>
                            </div>
	`;
    loadDangerousGroup();
    loadMapAndShare();
}

function shareVideoButton(){
	document.getElementById("hazardPostArea").innerHTML = `<div class="tab-pane fade show active" id="videos" role="tabpanel" aria-labelledby="videos-tab">
    <div class="form-group">
          <label class="sr-only" for="messageForVideo">post</label>
           <textarea class="form-control" id="message" rows="3" placeholder="Enter url" data-post-type="4"></textarea>
            <hr>
        <div class="custom-file">
            <input type="file" class="custom-file-input" id="customVideoFile" accept="video/mp4,video/x-m4v,video/*">
            <label class="custom-file-label" for="customFile">Upload video</label>            
        </div>
    </div>
		<div class="google-map" id="mapArea" style="padding: 10px;"></div>
</div>`;
	
    loadDangerousGroup();
    loadMapAndShare();
}

function loadMapAndShare(){
	document.getElementById("mapAndShare").innerHTML = `
        <div id="buttonToolbar" class="btn-toolbar justify-content-between" style="padding: 20px;">
            <div class="btn-group">
                <input type="submit" class="btn btn-primary btn-rounded" value="Share post" style="font-weight: bold;"></button>                               
            </div> 
            <div class="btn-group">
            
				<div class="custom-control custom-checkbox">
					 <input type="checkbox" class="custom-control-input" id="emergencyNotification">
					 <label class="custom-control-label" for="emergencyNotification">Emergency notification &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
				
					 <label for="buttonLocation" style="display: inline-block;">Location &nbsp;</label> 
				<!-- <button class="btn" id="buttonLocation" type="change" onclick="initMap()"><i class="fa fa-globe fa-2x" style="color: #3366ff;"></i></button> -->
				 <a href="#" onclick=initMap()><i class="fa fa-globe fa-2x" style="color: #3366ff;"></i></a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="btnGroupDrop1">                         
            </div><br>	
`;
}

function loadDangerousGroup(){
	
    var node = document.getElementById("selectDangerCategories");   
   
    var dg = JSON.parse(localStorage.getItem("dangerGroups")); 
    
    var optionNode = node.querySelector("#dangerselect");
    
    var length = optionNode.options.length;
    if(length > 0 ){
        for(i = length - 1; i >= 0; i--){
        	optionNode.options[i] = null;
        	}
    }
    
    Array.from(dg).forEach( hazard => {
		var option = document.createElement("option");
		option.text = hazard.name;
		option.value = hazard.id;
		optionNode.appendChild(option);
	});
}

function sharePost(){

	var emergencyNotification = document.getElementById("emergencyNotification").checked;
	var messageLink = document.getElementById("message");
	var postGroupId = parseInt(messageLink.dataset.postType);
	var contents = messageLink.value;
	var shareTime = new Date();
	const cookieName = "userId";
	var currentUserId = getCookieValue(cookieName);
	
	if(postGroupId === 2){
		if(!checkURL(contents)){
			alert("Your URL is not valid !");
		} else {
			
		var newPost = {
				"contents": contents,
				"userId": currentUserId, // dohvatiti usera :D
				"active": true,
				"postGroupIdPostGroup": postGroupId,
				"shareTime": shareTime,
				"emergencyNotification": emergencyNotification,
				"geographicLatitude": geographicLatitude,  // za sad
				"geographicLongitude": geographicLongitude,
		}		
		createPost(newPost);
		}
	}else if(postGroupId === 3) {
		var file1 = document.getElementById("customImageFile").files[0];
		
		if(contents !== "" && typeof file1 !== "undefined"){
      		var newPost = {
    				"contents": contents,
    				"userId": currentUserId, // dohvatiti usera :D
    				"active": true,
    				"postGroupIdPostGroup": postGroupId,
    				"shareTime": shareTime,
    				"emergencyNotification": emergencyNotification,
    				"geographicLatitude": geographicLatitude,  // za sad
    				"geographicLongitude": geographicLongitude,
    		}
    		createPost(newPost);
		} else {
			alert("There is no text or file is not selected !");
		}
	}
	else if(postGroupId === 4) {
		var file=document.getElementById("customVideoFile").files[0];
		
        if(typeof file !== "undefined"){
            var formData = new FormData(); //he FormData interface provides a way to easily construct a set of key/value pairs representing form fields and their values, which can then be easily sent using the XMLHttpRequest.send()
            formData.append('videoFile', file);
            formData.append('postGroupIdPostGroup', postGroupId);
            formData.append('userId', currentUserId);
            formData.append('active', true);
            formData.append('shareTime', shareTime);
            formData.append('geographicLatitude', geographicLatitude);
            formData.append('geographicLongitude', geographicLongitude);
            formData.append('emergencyNotification', emergencyNotification);
            
            var filename = file.name;
            formData.append('filename', filename);
            
            var request = new XMLHttpRequest();
            request.open('POST', 'http://localhost:8080/MainApp/VideoController');
            request.send(formData);
            
			shareVideoButton();			
            
        } else {
        	
        	if(checkYouTubeURL(contents)){
    		
              const regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
              const match = contents.match(regExp);
              const lastPart = (match && match[7].length == 11) ? match[7] : false;
              postContent = 'https://www.youtube.com/embed/' + lastPart; // + "?autoplay=1";
    		            
        		var newPost = {
    				"contents": postContent,
    				"userId": currentUserId, // dohvatiti usera :D
    				"active": true,
    				"postGroupIdPostGroup": postGroupId,
    				"shareTime": shareTime,
    				"emergencyNotification": emergencyNotification,
    				"geographicLatitude": geographicLatitude,  // za sad
    				"geographicLongitude": geographicLongitude,
    			}
        	}
    		createPost(newPost);
        }
	}	
}

var onePost = false;

function createPost(newPost){
	
	var request = new XMLHttpRequest();
	var theUrl = "api/post/insertPost";
		
	request.open("POST", theUrl, true);
	request.setRequestHeader("Content-Type", "application/json"); // before send after open
	request.send(JSON.stringify(newPost));
	
	request.onreadystatechange = function (){
		if ((request.readyState == 4) && (request.status == 200)) {
			var newPostId = JSON.parse(request.responseText);
			var allUsersPosts = document.getElementById("all-user-posts");
			var currentUser = JSON.parse(localStorage.getItem("currentUser"));
			
			var postId = JSON.parse(request.responseText);
			var dangerPostGroupIdArrayList = $("#dangerselect").val();
			
			if(newPost.postGroupIdPostGroup === 2){
				onePost = true;
				var node = document.createElement("div");
				shareLink(node, currentUser, newPost);
				shareLinkButton();
				var dangerCategoryForPost = {
						"postID": postId,
						"dangerPostGroupIdArrayList" : dangerPostGroupIdArrayList,
				}
				dangerCategoriesForPost(dangerCategoryForPost);
				if(newPost.emergencyNotification === true){
				}
			} else if(newPost.postGroupIdPostGroup === 3){
				
				
				var file1 = document.getElementById("customImageFile").files[0];
				var file2 = document.getElementById("customImageFile").files[1];
								
				var node = document.createElement("div");
				
				for(var i=0; i < document.getElementById("customImageFile").files.length; i++){
					if(typeof document.getElementById("customImageFile").files[i] !== "undefined"){
						setPicturesForPost(postId,document.getElementById("customImageFile").files[i],node);
					}
				}
				
				var fileLength = document.getElementById("customImageFile");

				onePost = true;
													
				shareImageButton(); 
							
				var dangerCategoryForPost = { 
						"postID": postId,
						"dangerPostGroupIdArrayList" : dangerPostGroupIdArrayList,
				}
				dangerCategoriesForPost(dangerCategoryForPost);
			}
			else if (newPost.postGroupIdPostGroup === 4){
				onePost = true;
				var node = document.createElement("div");
				if(newPost.contents.startsWith("https://www.youtube.com/embed")){
					shareYouTubeVideo(node, currentUser, newPost);
				} else {
				shareVideo(node, currentUser, newPost);
				}
				shareVideoButton();
							
				var dangerCategoryForPost = { 
						"postID": postId,
						"dangerPostGroupIdArrayList" : dangerPostGroupIdArrayList,
				}
				dangerCategoriesForPost(dangerCategoryForPost);
				
			}
		}
	}	
}

function setPicturesForPost(postId,file,node){
	
	var formData = new FormData();
	formData.append('pictureFileForPost', file);
    formData.append('active', true);
    formData.append('postId', postId);
    formData.append('filename', file.name);
    
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/MainApp/PictureForPostController');
    request.send(formData);
}

function loadUsersPost() {
	var allUsersPostElement = document.getElementById("all-user-posts");
	
	allUsersPostElement.innerHTML = "";
		
	var request = new XMLHttpRequest();
			
	request.onreadystatechange = function (){
		if ((request.readyState == 4) && (request.status == 200)) {
			var userPosts = JSON.parse(request.responseText);
			var usersWhichCreatePost ; 
			
			userPosts.forEach( post => {
				post.shareTime = new Date(post.shareTime.split("[UTC]")[0]);
			});
						
			Array.from(userPosts).forEach( post => {
				var node = document.createElement("div");
				node.id = post.id;
				node.innerHTML = "";
				
				if(post.postGroupIdPostGroup === 2){
					usersWhichCreatePost =  JSON.parse(localStorage.getItem("allUsersForPosts"));
					for(usersPost of usersWhichCreatePost){
						if(usersPost.id === post.userId){
							shareLink(node,usersPost,post);
							showDangerCategoriesForPost(node, "#listCategoryforPost", post.id);
							if(post.geographicLatitude !== 0 && post.geographicLongitude !== 0){							
								showGoogleMap(post.geographicLatitude, post.geographicLongitude, node, "#locationMap");
							} 
							loadCommentsForPost(post.id);
						}							
					}
				}
				else if (post.postGroupIdPostGroup === 4){
					usersWhichCreatePost =  JSON.parse(localStorage.getItem("allUsersForPosts"));
					for(usersPost of usersWhichCreatePost){
						if(usersPost.id === post.userId){
							
							if(post.contents.startsWith("https://www.youtube.com/embed")){
								shareYouTubeVideo(node,usersPost,post);
							} else {
							shareVideo(node,usersPost,post);
							}
							showDangerCategoriesForPost(node, "#listCategoryforPost", post.id);
							if(post.geographicLatitude !== 0 && post.geographicLongitude !== 0){						
								showGoogleMap(post.geographicLatitude, post.geographicLongitude, node, "#locationMap");
							} 
							loadCommentsForPost(post.id);
						}							
					}
				} else if(post.postGroupIdPostGroup === 3){					
					usersWhichCreatePost =  JSON.parse(localStorage.getItem("allUsersForPosts"));
					for(usersPost of usersWhichCreatePost){
						if(usersPost.id === post.userId){
							if(post.geographicLatitude !== 0 && post.geographicLongitude !== 0){		
								//console.log("NODE: "+node);
								//showGoogleMap(post.geographicLatitude, post.geographicLongitude, node, "#locationMap");
								shareImagesWithLocation(node,usersPost,post,post.id,post.geographicLatitude, post.geographicLongitude, node, "#locationMap");
							} else {
							shareImage(node,usersPost,post,post.id);
							}
							showDangerCategoriesForPost(node, "#listCategoryforPost", post.id);
							
							
							
							loadCommentsForPost(post.id);
						}							
					}
				}
				
				allUsersPostElement.appendChild(node);
			});
		}
	}
	
    request.open("GET", "api/post", true);
    request.send();
}

var tmpNode;

function shareLink(node, user, post){

	node.innerHTML = "";
	
	node.innerHTML = `<br>
			<div id="${post.id}">
	        <div class="card" style="padding: 10px;">
            <div class="panel panel-info">
                <div class="panel-heading">
                
                    <ul class="media-list">
                        <li class="media">
                             <div class="pull-left image">
                              <img src="${user.picture}" alt="${user.picture}" class="md-avatar rounded-circle size-3" style="width: 100px; height: 100px; border-radius: 50%; border: solid 2px #1a1aff;">
                            </div>  
                            <div class="media-body">
                                <span class="text-muted pull-right">
                                    <small class="text-muted">${formatDate(post.shareTime)} &nbsp;</small>
                                </span>
                                <strong class="text-success">&nbsp; ${user.firstName} &nbsp; ${user.lastName} &nbsp;</strong>
                                <div class="container" id="post-description" style="max-width: 400px; max-height: 256px;">
                                <p class="p-for-post">&nbsp;
                                <a href="${post.contents}" target="_blank">${post.contents} &nbsp;</a>
                                </p>
                                </div>
                            </div>
                        </li>
                    </ul>
					<hr>
					</ul>					   										
				<div class="container" id="locationMap"></div>
				<!--	<br>	-->
				<p style="font-weight: bold; font-size: 75%; color:rgb(252,115,3);">Dangerous categories: <p>
				<div class="container" id="listCategoryforPost">
				</div>
				<hr>
				<!--	<br>	-->							
                </div>            
                <div class="panel-body">
                    <!-- 	<textarea class="form-control" placeholder="Write a comment..." rows="3"></textarea>   -->
                     <form id="post#${post.id}" class="share-comment" method="post" onsubmit="shareComment(event)">
                             <input class="form-control"  type="text" id="comment" rows="3" placeholder="Write a comment..." required="required">
                             <br>
                             <!-- <input class="custom-file-input" id="picture" type="file">	-->
                             <div class="input-group">
								<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
							</div>
								<div class="custom-file">
								<input type="file" class="custom-file-input" id="picture"
									aria-describedby="inputGroupFileAddon01">
								<label class="custom-file-label" for="picture">Choose file</label>
							</div>
							</div>
                             <br>
                             <button type="submit" class="btn btn-info pull-left" id="post-comment" style="font-weight: bold; font-size: 125%;">&nbsp;&nbsp;&nbsp;&nbsp;Post comment&nbsp;&nbsp;&nbsp;&nbsp;</button>               	
                    </form>                                   
                    <br><br><br>
                    <div class="btn-toolbar mb-3" role="toolbar">                   
                    <div>
					<ul class="social-network social-circle">
					<li><a href="#" class="icoRss" title="Vk"><i class="fa fa-vk" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>         			
          			<li><a href="https://www.facebook.com/sharer/sharer.php?u=${post.contents}" class="icoFacebook" title="Facebook" target="_blank"><i class="fa fa-facebook"></i></a></li>				
					<li><a href="https://twitter.com/intent/tweet?text=${post.contents}" class="icoTwitter" title="Twitter" target="_blank"><i class="fa fa-twitter"></i></a></li>
          			<li><a href="#" class="icoGoogle" title="Google +"><i class="fa fa-google-plus" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
          			<li><a href="#" class="icoLinkedin" title="Linkedin"><i class="fa fa-linkedin" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
        			<!-- <a href="https://www.facebook.com/sharer/sharer.php?u=${post.contents}" target="_blank">
						Share on Facebook
					</a>	-->
        			</ul>     								
					</div>
					<hr>
                    <div>
					<div class="clearfix"></div>
                    <hr><br>                        
                        </li>
                </div>			
            </div>
        </div> <br>
        <button type="button" class="btn btn-primary pull-right" onclick="myFunction(${post.id})">Show/Hide comments</button>
        <br><br>
        <hr>        
       <ul class="comment-list" id="comment-list${post.id}" style="list-style-type: none;">
       </div>
	`;
	
	tmpNode = node;
	
	if(onePost === true){
		var allUsersPostElement = document.getElementById("all-user-posts");
		allUsersPostElement.insertBefore(node, allUsersPostElement.childNodes[0]);
		onePost = false;
	}
}

function myFunction(postId) {
	  var tmp = "comment-list".concat(postId);
	  var x = document.getElementById(tmp);
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } else {
	    x.style.display = "none";
	  }
}

function shareVideo(node, user, post){
	
	node.innerHTML = "";
	
	node.innerHTML = `<br>
        <div class="card" style="padding: 10px;">
        <div class="panel panel-info">
            <div class="panel-heading">
            
                <ul class="media-list">
                    <li class="media">
                         <div class="pull-left image">
                          <img src="${user.picture}" alt="${user.picture}" class="md-avatar rounded-circle size-3" style="width: 100px; height: 100px; border-radius: 50%; border: solid 2px #1a1aff;">
                        </div>  
                        <div class="media-body" style="width="100%; height="100%;">
                            <span class="text-muted pull-right">
                                <small class="text-muted">${formatDate(post.shareTime)} &nbsp;</small>
                            </span>
                            <strong class="text-success">&nbsp; ${user.firstName} &nbsp; ${user.lastName} &nbsp;</strong>
                            <br><br><br>
                            <div class="container" id="post-description" style="max-width: 400px; max-height: 1px;">
							<!--	<iframe style="width="100%; height="100%;"  src="${post.contents}" class="myIframe"></iframe>	-->
							
							<!--	<iframe src="${post.contents}" id="myIframe" scrolling="no" frameborder="0" allow='autoplay'
								style="position: relative; height: 100%; width: 100%;">
								</iframe>	-->
								

                            </div>
                            
                         		<video width="374" height="187" controls>
									<source src="${post.contents}" type="video/mp4">
									<source src="${post.contents}" type="video/ogg">	
								</video> 
                            
                        </div>
                    </li>
                </ul>
				<hr>
				</ul>					   										
			<div class="container" id="locationMap"></div>
			<!--	<br>	-->
			<!-- <iframe style="width="100%; height="100%;"  src="${post.contents}" class="myIframe"></iframe> -->
			<p style="font-weight: bold; font-size: 75%; color:rgb(252,115,3);">Dangerous categories: <p>
			<div class="container" id="listCategoryforPost">
			</div>
			<hr>
			<!--	<br>	-->							
            </div>            
            <div class="panel-body">
                <!-- 	<textarea class="form-control" placeholder="Write a comment..." rows="3"></textarea>   -->
                 <form id="post#${post.id}" class="share-comment" method="post" onsubmit="shareComment(event)">
                         <input class="form-control"  type="text" id="comment" rows="3" placeholder="Write a comment..." required="required">
                         <br>
                         <!-- <input class="custom-file-input" id="picture" type="file">	-->
                         <div class="input-group">
							<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
						</div>
							<div class="custom-file">
							<input type="file" class="custom-file-input" id="picture"
								aria-describedby="inputGroupFileAddon01">
							<label class="custom-file-label" for="picture">Choose file</label>
						</div>
						</div>
                         <br>
                         <button type="submit" class="btn btn-info pull-left" id="post-comment" style="font-weight: bold; font-size: 125%;">&nbsp;&nbsp;&nbsp;&nbsp;Post comment&nbsp;&nbsp;&nbsp;&nbsp;</button>               	
                </form>                                   
                <br><br><br>
                <div class="btn-toolbar mb-3" role="toolbar">                   
                <div>
				<ul class="social-network social-circle">
				<!-- <li><a href="#" class="icoRss" title="Rss"><i class="fa fa-rss"></i></a></li> -->
				<li><a href="#" class="icoRss" title="Vk"><i class="fa fa-vk" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
      			<li><a href="https://www.facebook.com/sharer/sharer.php?u=${post.contents}" class="icoFacebook" title="Facebook" target="_blank"><i class="fa fa-facebook"></i></a></li>				
				<li><a href="https://twitter.com/intent/tweet?text=${post.contents}" class="icoTwitter" title="Twitter" target="_blank"><i class="fa fa-twitter"></i></a></li>
          		<li><a href="#" class="icoGoogle" title="Google +"><i class="fa fa-google-plus" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
      			<li><a href="#" class="icoLinkedin" title="Linkedin"><i class="fa fa-linkedin" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
    			</ul>     								
				</div>
				<hr>
                <div>
				<div class="clearfix"></div>
                <hr><br>                        
                    </li>
            </div>			
        </div>
    </div> <br>
		<button type="button" class="btn btn-primary pull-right" onclick="myFunction(${post.id})">Show/Hide comments</button>
        <br><br>
        <hr>        
       <ul class="comment-list" id="comment-list${post.id}" style="list-style-type: none;">
`;
	

tmpNode = node;

	if(onePost === true){
	var allUsersPostElement = document.getElementById("all-user-posts");
	allUsersPostElement.insertBefore(node, allUsersPostElement.childNodes[0]);
	onePost = false;
	}
	
}

function shareYouTubeVideo(node, user, post){
	
	node.innerHTML = "";
	
	node.innerHTML = `<br>
        <div class="card" style="padding: 10px;">
        <div class="panel panel-info">
            <div class="panel-heading">
            
                <ul class="media-list">
                    <li class="media">
                         <div class="pull-left image">
                          <img src="${user.picture}" alt="${user.picture}" class="md-avatar rounded-circle size-3" style="width: 100px; height: 100px; border-radius: 50%; border: solid 2px #1a1aff;">
                        </div>  
                        <div class="media-body" style="width="100%; height="100%;">
                            <span class="text-muted pull-right">
                                <small class="text-muted">${formatDate(post.shareTime)} &nbsp;</small>
                            </span>
                            <strong class="text-success">&nbsp; ${user.firstName} &nbsp; ${user.lastName} &nbsp;</strong>
                            <br><br><br>
                            <div class="container" id="post-description" style="max-width: 400px; max-height: 256px;">
							<!--	<iframe style="width="100%; height="100%;"  src="${post.contents}" class="myIframe"></iframe>	-->
							
							<iframe src="${post.contents}" id="myIframe" scrolling="no" frameborder="0" allow='autoplay'
								style="position: center; height: 100%; width: 100%;">
								</iframe
                            </div>                           
                         		<video width="200" height="1">
									<!--	<source src="${post.contents}" type="video/mp4">
									<source src="${post.contents}" type="video/ogg">	-->
								</video> 
                            
                        </div>
                    </li>
                </ul>
				<hr>
				</ul>					   										
			<div class="container" id="locationMap"></div>
			<!--	<br>	-->
			<!-- <iframe style="width="100%; height="100%;"  src="${post.contents}" class="myIframe"></iframe> -->
			<p style="font-weight: bold; font-size: 75%; color:rgb(252,115,3);">Dangerous categories: <p>
			<div class="container" id="listCategoryforPost">
			</div>
			<hr>
			<!--	<br>	-->							
            </div>            
            <div class="panel-body">
                <!-- 	<textarea class="form-control" placeholder="Write a comment..." rows="3"></textarea>   -->
                 <form id="post#${post.id}" class="share-comment" method="post" onsubmit="shareComment(event)">
                         <input class="form-control"  type="text" id="comment" rows="3" placeholder="Write a comment..." required="required">
                         <br>
                         <!-- <input class="custom-file-input" id="picture" type="file">	-->
                         <div class="input-group">
							<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
						</div>
							<div class="custom-file">
							<input type="file" class="custom-file-input" id="picture"
								aria-describedby="inputGroupFileAddon01">
							<label class="custom-file-label" for="picture">Choose file</label>
						</div>
						</div>
                         <br>
                         <button type="submit" class="btn btn-info pull-left" id="post-comment" style="font-weight: bold; font-size: 125%;">&nbsp;&nbsp;&nbsp;&nbsp;Post comment&nbsp;&nbsp;&nbsp;&nbsp;</button>               	
                </form>                                   
                <br><br><br>
                <div class="btn-toolbar mb-3" role="toolbar">                   
                <div>
				<ul class="social-network social-circle">
				<!-- <li><a href="#" class="icoRss" title="Rss"><i class="fa fa-rss"></i></a></li> -->
				<li><a href="#" class="icoRss" title="Vk"><i class="fa fa-vk" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
      			<li><a href="https://www.facebook.com/sharer/sharer.php?u=${post.contents}" class="icoFacebook" title="Facebook" target="_blank"><i class="fa fa-facebook"></i></a></li>				
				<li><a href="https://twitter.com/intent/tweet?text=${post.contents}" class="icoTwitter" title="Twitter" target="_blank"><i class="fa fa-twitter"></i></a></li>
          		<li><a href="#" class="icoGoogle" title="Google +"><i class="fa fa-google-plus" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
      			<li><a href="#" class="icoLinkedin" title="Linkedin"><i class="fa fa-linkedin" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
    			</ul>     								
				</div>
				<hr>
                <div>
				<div class="clearfix"></div>
                <hr><br>                        
                    </li>
            </div>			
        </div>
    </div> <br>
		<button type="button" class="btn btn-primary pull-right" onclick="myFunction(${post.id})">Show/Hide comments</button>
        <br><br>
        <hr>        
       <ul class="comment-list" id="comment-list${post.id}" style="list-style-type: none;">
`;

tmpNode = node;

	if(onePost === true){
	var allUsersPostElement = document.getElementById("all-user-posts");
	allUsersPostElement.insertBefore(node, allUsersPostElement.childNodes[0]);
	onePost = false;
	}
}

function shareImage(node, user, post, postId){
	
	node.innerHTML = "";
	
	var allimagesForPost = [];
	var request = new XMLHttpRequest();
	var theUrl = `api/post/getImagesForPosts/${postId}`;
	request.open("GET",theUrl, true);
	request.send();
	request.onreadystatechange = function (){
		if ((request.readyState == 4) && (request.status == 200)) {
			 allimagesForPost = JSON.parse(request.responseText);
			
			Array.from(allimagesForPost).forEach(image => {
				
			if(image.postId === postId ){
			
			node.innerHTML = `<br>
		        <div class="card" style="padding: 10px;">
	            <div class="panel panel-info">
	                <div class="panel-heading">
	                
	                    <ul class="media-list" id="mlmlml">
	                        <li class="media">
	                             <div class="pull-left image">
	                              <img src="${user.picture}" alt="${user.picture}" class="md-avatar rounded-circle size-3" style="width: 100px; height: 100px; border-radius: 50%; border: solid 2px #1a1aff;">
	                            </div>  
	                            <div class="media-body">
	                                <span class="text-muted pull-right">
	                                    <small class="text-muted">${formatDate(post.shareTime)} &nbsp;</small>
	                                </span>
	                                <strong class="text-success">&nbsp; ${user.firstName} &nbsp; ${user.lastName} &nbsp;</strong>
	                                <div class="container" id="post-description" style="max-width: 400px; max-height: 256px;">
	                                <p style="width: 250px;">&nbsp;
	                                <!-- <a href="${post.contents}" target="_blank">${post.contents} &nbsp;</a> -->
	                                ${post.contents}
	                                </p>
	                                </div>
									<div id="container"></div>
									<div id="container"></div>
									<div id="pictureForComment${post.id}">
									</div>																		
	                            </div>
	                        </li>
	                    </ul>
						<hr>
						</ul>					   										
					<div class="container" id="locationMap"></div>
					
					<p style="font-weight: bold; font-size: 75%; color:rgb(252,115,3);">Dangerous categories: <p>
					<div class="container" id="listCategoryforPost">
					</div>
					<hr>						
	                </div>            
	                <div class="panel-body">
	                    <!-- 	<textarea class="form-control" placeholder="Write a comment..." rows="3"></textarea>   -->
	                     <form id="post#${post.id}" class="share-comment" method="post" onsubmit="shareComment(event)">
	                             <input class="form-control"  type="text" id="comment" rows="3" placeholder="Write a comment..." required="required">
	                             <br>
	                             <!-- <input class="custom-file-input" id="picture" type="file">	-->
	                             <div class="input-group">
									<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
								</div>
									<div class="custom-file">
									<input type="file" class="custom-file-input" id="picture"
										aria-describedby="inputGroupFileAddon01">
									<label class="custom-file-label" for="picture">Choose file</label>
								</div>
								</div>
	                             <br>
	                             <button type="submit" class="btn btn-info pull-left" id="post-comment" style="font-weight: bold; font-size: 125%;">&nbsp;&nbsp;&nbsp;&nbsp;Post comment&nbsp;&nbsp;&nbsp;&nbsp;</button>               	
	                    </form>                                   
	                    <br><br><br>
	                    <div class="btn-toolbar mb-3" role="toolbar">                   
	                    <div>
						<ul class="social-network social-circle">
						<!-- <li><a href="#" class="icoRss" title="Rss"><i class="fa fa-rss"></i></a></li> -->
						<li><a href="#" class="icoRss" title="Vk"><i class="fa fa-vk" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
	          			<li><a href="https://www.facebook.com/sharer/sharer.php?u=${post.contents}${image.content}" class="icoFacebook" title="Facebook" target="_blank"><i class="fa fa-facebook"></i></a></li>				
						<li><a href="https://twitter.com/intent/tweet?text=${post.contents}${image.content}" class="icoTwitter" title="Twitter" target="_blank"><i class="fa fa-twitter"></i></a></li>
          				<li><a href="#" class="icoGoogle" title="Google +"><i class="fa fa-google-plus" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
	          			<li><a href="#" class="icoLinkedin" title="Linkedin"><i class="fa fa-linkedin" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
	        			</ul>     								
						</div>
						<hr>
	                    <div>
						<div class="clearfix"></div>
	                    <hr><br>                        
	                        </li>
	                </div>			
	            </div>
	        </div> <br>
			<button type="button" class="btn btn-primary pull-right" onclick="myFunction(${post.id})">Show/Hide comments</button>
        <br><br>
        <hr>        
       <ul class="comment-list" id="comment-list${post.id}" style="list-style-type: none;">
		`;
			
			
		}
			});
			loadAllImagesForPost(postId,allimagesForPost);

		tmpNode = node;
		
		if(onePost === true){
			var allUsersPostElement = document.getElementById("all-user-posts");
			allUsersPostElement.insertBefore(node, allUsersPostElement.childNodes[0]);
			onePost = false;
		}
			
		}
	}
	
}

function shareImagesWithLocation(node,user,post,postId,postGeographicLatitude, postGeographicLongitude, node, mapId){
node.innerHTML = "";
	
	var allimagesForPost = [];
	var request = new XMLHttpRequest();
	var theUrl = `api/post/getImagesForPosts/${postId}`;
	request.open("GET",theUrl, true);
	request.send();
	request.onreadystatechange = function (){
		if ((request.readyState == 4) && (request.status == 200)) {
			 allimagesForPost = JSON.parse(request.responseText);
			
			Array.from(allimagesForPost).forEach(image => {
				
			if(image.postId === postId ){
			
			node.innerHTML = `<br>
		        <div class="card" style="padding: 10px;">
	            <div class="panel panel-info">
	                <div class="panel-heading">
	                
	                    <ul class="media-list" id="mlmlml">
	                        <li class="media">
	                             <div class="pull-left image">
	                              <img src="${user.picture}" alt="${user.picture}" class="md-avatar rounded-circle size-3" style="width: 100px; height: 100px; border-radius: 50%; border: solid 2px #1a1aff;">
	                            </div>  
	                            <div class="media-body">
	                                <span class="text-muted pull-right">
	                                    <small class="text-muted">${formatDate(post.shareTime)} &nbsp;</small>
	                                </span>
	                                <strong class="text-success">&nbsp; ${user.firstName} &nbsp; ${user.lastName} &nbsp;</strong>
	                                <div class="container" id="post-description" style="max-width: 400px; max-height: 256px;">
	                                <p style="width: 250px;">&nbsp;
	                                <!-- <a href="${post.contents}" target="_blank">${post.contents} &nbsp;</a> -->
	                                ${post.contents}
	                                </p>
	                                </div>
									<div id="container"></div>
									<div id="container"></div>
									<div id="pictureForComment${post.id}">
									</div>																		
	                            </div>
	                        </li>
	                    </ul>
						<hr>
						</ul>					   										
					<div class="container" id="locationMap"></div>
					
					<p style="font-weight: bold; font-size: 75%; color:rgb(252,115,3);">Dangerous categories: <p>
					<div class="container" id="listCategoryforPost">
					</div>
					<hr>						
	                </div>            
	                <div class="panel-body">
	                    <!-- 	<textarea class="form-control" placeholder="Write a comment..." rows="3"></textarea>   -->
	                     <form id="post#${post.id}" class="share-comment" method="post" onsubmit="shareComment(event)">
	                             <input class="form-control"  type="text" id="comment" rows="3" placeholder="Write a comment..." required="required">
	                             <br>
	                             <!-- <input class="custom-file-input" id="picture" type="file">	-->
	                             <div class="input-group">
									<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
								</div>
									<div class="custom-file">
									<input type="file" class="custom-file-input" id="picture"
										aria-describedby="inputGroupFileAddon01">
									<label class="custom-file-label" for="picture">Choose file</label>
								</div>
								</div>
	                             <br>
	                             <button type="submit" class="btn btn-info pull-left" id="post-comment" style="font-weight: bold; font-size: 125%;">&nbsp;&nbsp;&nbsp;&nbsp;Post comment&nbsp;&nbsp;&nbsp;&nbsp;</button>               	
	                    </form>                                   
	                    <br><br><br>
	                    <div class="btn-toolbar mb-3" role="toolbar">                   
	                    <div>
						<ul class="social-network social-circle">
						<!-- <li><a href="#" class="icoRss" title="Rss"><i class="fa fa-rss"></i></a></li> -->
						<li><a href="#" class="icoRss" title="Vk"><i class="fa fa-vk" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
	          			<li><a href="https://www.facebook.com/sharer/sharer.php?u=${post.contents}${image.content}" class="icoFacebook" title="Facebook" target="_blank"><i class="fa fa-facebook"></i></a></li>				
						<li><a href="https://twitter.com/intent/tweet?text=${post.contents}${image.content}" class="icoTwitter" title="Twitter" target="_blank"><i class="fa fa-twitter"></i></a></li>
          				<li><a href="#" class="icoGoogle" title="Google +"><i class="fa fa-google-plus" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
	          			<li><a href="#" class="icoLinkedin" title="Linkedin"><i class="fa fa-linkedin" onclick="thisFunctionIsNotAvailaibleAlert()"></i></a></li>
	        			</ul>     								
						</div>
						<hr>
	                    <div>
						<div class="clearfix"></div>
	                    <hr><br>                        
	                        </li>
	                </div>			
	            </div>
	        </div> <br>
			<button type="button" class="btn btn-primary pull-right" onclick="myFunction(${post.id})">Show/Hide comments</button>
        <br><br>
        <hr>        
       <ul class="comment-list" id="comment-list${post.id}" style="list-style-type: none;">
		`;
			
			
		}
			});
			loadAllImagesForPost(postId,allimagesForPost);

		tmpNode = node;
		
		if(onePost === true){
			var allUsersPostElement = document.getElementById("all-user-posts");
			allUsersPostElement.insertBefore(node, allUsersPostElement.childNodes[0]);
			onePost = false;
		}
		
		showGoogleMap(postGeographicLatitude, postGeographicLongitude, node, "#locationMap");
			
		}
	}
	
}

function loadAllImagesForPost(postId,allimagesForPost){	
	var node = document.createElement("div")
    var imageDivId = "pictureForComment".concat(postId);
    var imageDiv = document.getElementById(imageDivId);
		if(allimagesForPost.length === 1){
		node.innerHTML = "";
		node.innerHTML = `		    
	        <img onclick="window.open(this.src)" src="${allimagesForPost[0].content}"  id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">
			`;
			}
		
		else if(allimagesForPost.length === 2){
			node.innerHTML = "";
			node.innerHTML = `
				
		        <img onclick="window.open(this.src)" src="${allimagesForPost[0].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">
				<img onclick="window.open(this.src)" src="${allimagesForPost[1].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">
				`;
				}
		
		else if(allimagesForPost.length === 3){
			node.innerHTML = "";
			node.innerHTML = `
		        <img onclick="window.open(this.src)" src="${allimagesForPost[0].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">
				<img onclick="window.open(this.src)" src="${allimagesForPost[1].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">
				<img onclick="window.open(this.src)" src="${allimagesForPost[2].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">				
				`;
				}
		else if(allimagesForPost.length > 3){
			node.innerHTML = "";
			node.innerHTML = `
		        <img onclick="window.open(this.src)" src="${allimagesForPost[0].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">
				<img onclick="window.open(this.src)" src="${allimagesForPost[1].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">
				<img onclick="window.open(this.src)" src="${allimagesForPost[2].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">
				<img onclick="window.open(this.src)" src="${allimagesForPost[3].content}" id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 200px; height: 200px;">				
				`;
				}
	imageDiv.appendChild(node);
}

function showDangerCategoriesForPost(node, dcId,getDangerGroupIds) {
    var dc = node.querySelector(dcId);
	var request = new XMLHttpRequest();	
	var theUrl = `api/post/${getDangerGroupIds}`;
	
	request.onreadystatechange = function (){
		if((request.readyState == 4) && (request.status == 200)){
		var array = JSON.parse(request.responseText);
		setDangerCategoriesForPost(node,array,dcId);
		}
	}
	request.open("GET", theUrl, true);
	request.send();	    
}

function setDangerCategoriesForPost(node,array,dcId){
	var dc = node.querySelector(dcId);
	var list = document.createElement('ul');
	list.className = "list-group";
	
	if(dc !== null){
	Array.from(array).forEach( dangerName => {
    	   	
    	var getPostGroupName = dangerName;
    	var request = new XMLHttpRequest();
		var theUrl = `api/dangerPost/${getPostGroupName}`;
    	
    	request.onreadystatechange = function (){
    		if((request.readyState == 4) && (request.status == 200)){
    			var result = request.responseText;  	
    			var item = document.createElement('li');
    			item.className = "list-group-item list-group-item-danger";
    			item.style.width = "40vh";
    			item.style.fontSize = "12px";
    			item.appendChild(document.createTextNode(result));

        // Add it to the list:
        list.appendChild(item);
        dc.appendChild(list);
    		} 
    }
    	request.open("GET", theUrl, true);
    	request.send();	
	}); }
}


function getCookieValue(cookieName){
    let cookieValue = "";
    let name = cookieName + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let cookies = decodedCookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) == 0) {
            cookieValue = cookie.substring(name.length, cookie.length);
        }
    }
    return cookieValue;
}

function getCurrentUser(){
	var request = new XMLHttpRequest();
	const cookieName = "userId";
	var currentUserId = getCookieValue(cookieName);
	var theUrl = `api/user/${currentUserId}`;
	request.onreadystatechange = function (){
		if((request.readyState == 4) && (request.status == 200)){
			var currentUser = JSON.parse(request.responseText);
			localStorage.setItem("currentUser",JSON.stringify(currentUser));
			return currentUser;
		}
	}
	
	request.open("GET", theUrl, true);
	request.send();
}

function getAllUsers() {
	var request = new XMLHttpRequest();
    	request.onreadystatechange = function (){
            if ((request.readyState == 4) && (request.status == 200)) {
                var allUsersForPosts = JSON.parse(request.responseText);
                localStorage.setItem("allUsersForPosts", JSON.stringify(allUsersForPosts));
                return allUsersForPosts;
            }
        }
        request.open("GET", "api/user", true);
        request.send();
}



var flag = true;
function initMap() {
	
	var tmp = document.getElementById("mapArea");
	tmp.innerHTML = flag ?  `
		<p>Izaberite lokaciju</p>
			<div id="map" style="height: 256px; width: 512px;">
			</div>
		`   : "";
	if(flag){
    
		var googleMap = document.getElementById('map');
    googleMap.style.display = "block";
    
    var map = new google.maps.Map(googleMap, {
        center: new google.maps.LatLng(44.7730610688622, 17.194976416015653),
        zoom: 8,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });
    
    marker = new google.maps.Marker({
        position: new google.maps.LatLng(44.7730610688622, 17.194976416015653),
        map: map,
        draggable: true
    });

    google.maps.event.addListener(marker, 'dragend', getLocation);
    marker.setMap(map);
    
	}
	flag = !flag;
}

function getLocation() {
    var path = marker.getPosition();
    geographicLatitude = path.lat();
    geographicLongitude = path.lng();
}


function checkURL(text){
    var pattern = new RegExp('^(https?:\\/\\/)?' + '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + '((\\d{1,3}\\.){3}\\d{1,3}))' + '(\\:\\d+)?' + '(\\/[-a-z\\d%@_.~+&:]*)*' + '(\\?[;&a-z\\d%@_.,~+&:=-]*)?' + '(\\#[-a-z\\d_]*)?$', 'i'); 
        var result = pattern.test(text);
        return result;
}


function formatDate(creationTime){
    var date = new Date(creationTime);
    var formatDate = ("0" + date.getDate()).slice(-2) + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + date.getFullYear() + " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2) + ":" + ("0" + date.getSeconds()).slice(-2);
    return formatDate;

}


function thisFunctionIsNotAvailaibleAlert(){
	alert("Sorry. This function is not availaible");
}

function fillDangerousCategoriesSeceltBox(){
	var request = new XMLHttpRequest();
	var theUrl = "api/post/getDangerPostGroup";
	request.onreadystatechange = function (){
		if((request.readyState == 4) && (request.status == 200)){
			var dangerGroups = JSON.parse(request.responseText);
			localStorage.setItem("dangerGroups",JSON.stringify(dangerGroups));
			return dangerGroups;
		}
	}	
	request.open("GET", theUrl, true);
	request.send();
}

function dangerCategoriesForPost(catg){
	var request = new XMLHttpRequest();
	var theUrl = "api/post/insertDangerGroupsForPost"
	request.open("POST",theUrl,true);
	request.setRequestHeader("Content-Type", "application/json");
	request.send(JSON.stringify(catg));
}


  function getCurrentUser(){
	var request = new XMLHttpRequest();
	const cookieName = "userId";
	var currentUserId = getCookieValue(cookieName);
	var theUrl = `api/user/${currentUserId}`;
	request.onreadystatechange = function (){
		if((request.readyState == 4) && (request.status == 200)){
			var currentUser = JSON.parse(request.responseText);
			localStorage.setItem("currentUser",JSON.stringify(currentUser));
			return currentUser;
		}
	}
	
	request.open("GET", theUrl, true);
	request.send();
}


function showGoogleMap(geographicLatitude, geographicLongitude, node, mapId) {
    var googleMap = node.querySelector(mapId);
    if(geographicLatitude !== 0 && geographicLongitude !== 0){
    	//style="width: 100%; height: 40vh;
    	googleMap.style.width = "100%";
    	googleMap.style.height = "40vh";
    }
    
    googleMap.style.display = "block";
    var map = new google.maps.Map(googleMap, {
        zoom: 8,
        center: new google.maps.LatLng(geographicLatitude, geographicLongitude),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });
    marker = new google.maps.Marker({
        position: new google.maps.LatLng(geographicLatitude, geographicLongitude),
        map: map
    });
}

var binPicture;

function shareComment(event){
	 
	event.preventDefault();
	
	var picture = event.target["picture"].files[0];
	
	var content = event.target["comment"].value;
	
	var currentUser = JSON.parse(localStorage.getItem("currentUser"));
	var postId = event.target.id.split("#")[1];
	var shareTime = new Date();
	
	var fileReader = new FileReader();
	
	fileReader.onload = function() {
		binPicture = fileReader.result;	
		binPicture = binPicture.split(",")[1];		
		var comment = {
				"content": content,
				"shareTime": shareTime,
				"picture": binPicture,
				"userId": currentUser.id,
				"postId": postId,
		};		
		addComment(postId, comment, currentUser);
	}
	
	if(typeof picture !== "undefined"){
		fileReader.readAsDataURL(picture);
	} else {
		var comment = {
				"content": content,
				"shareTime": shareTime,
				"picture": undefined,
				"userId": currentUser.id,
				"postId": postId,
		};
		addComment(postId, comment, currentUser);
	}
	event.target["comment"].value = "";
}

function addComment(postId, comment, user){
	
	var request = new XMLHttpRequest(); //addCommentWithPicture
	var theUrl
	if(typeof comment.picture !== "undefined"){
		theUrl = "api/post/addCommentWithPicture";
	} else {
		theUrl = "api/post/addComment";
	}
	request.open("POST",theUrl, true);
	request.setRequestHeader("Content-Type", "application/json");
	request.send(JSON.stringify(comment));
	
	request.onreadystatechange = function (){
		if ((request.readyState == 4) && (request.status == 200)) {
			var currentUser = JSON.parse(localStorage.getItem("currentUser"));
			comment.id = request.responseText;
			var getPost = document.getElementById(postId);
			var getListForComments = getPost.querySelector(".comment-list");
			var commentLi = document.createElement("li");
			
			commentLi.innerHTML = `				
								<a href="#" class="pull-left">
                                <img src="${user.picture}" alt="${user.picture}" class="md-avatar rounded-circle size-3" style="width: 75px; height: 75px; border-radius: 50%; border: solid 2px #1a1aff;">                            
                            </a>                        
                            <div class="media-body">
                                <span class="text-muted pull-right">
                                    <small class="text-muted">${formatDate(comment.shareTime)} &nbsp;</small>
                                </span>
                                <strong class="text-success">&nbsp; ${user.firstName} &nbsp; ${user.lastName} &nbsp;</strong>                               
                                <p>&nbsp;${comment.content}</p>
                                <img id="pictureForComment">
                            </div>                          
                            <br>
			                 <hr>			               
			`;
			
			if(typeof comment.picture !== "undefined"){
				var commentPicQS = commentLi.querySelector("#pictureForComment");
				var concatenateContent = "data:image/jpeg;base64,".concat(comment.picture);
				commentPicQS.src = concatenateContent;
				commentPicQS.height = 128;
				commentPicQS.width = 256;				
			}
			
			getListForComments.insertBefore(commentLi, getListForComments.childNodes[0]);
		}
	}
}


function loadCommentsForPost(postId){
	var request = new XMLHttpRequest();
	var theUrl = `api/post/getCommentForPost/${postId}`;
	request.open("GET",theUrl, true);
	request.send();
	request.onreadystatechange = function (){
		if ((request.readyState == 4) && (request.status == 200)) {
			var allCommentsForPost = JSON.parse(request.responseText);
			var allUsers = JSON.parse(localStorage.getItem("allUsersForPosts"));	
			
			allCommentsForPost.forEach( comment => {
				comment.shareTime = new Date(comment.shareTime.split("[UTC]")[0]);
			});
			
			var getPost = document.getElementById(postId);
			
			var getListForComments = getPost.querySelector(".comment-list");
			if(getListForComments !== null){
			getListForComments.innerHTML = "";
			}
			Array.from(allCommentsForPost).forEach(comment => {
				for(user of allUsers){
					if(user.id === comment.userId){
						var getPost = document.getElementById(postId);
						var getListForComments = getPost.querySelector(".comment-list");
						var commentLi = document.createElement("li");
						commentLi.innerHTML = `				
										<a href="#" class="pull-left">
			                            <img src="${user.picture}" alt="${user.picture}" class="md-avatar rounded-circle size-3" style="width: 75px; height: 75px; border-radius: 50%; border: solid 2px #1a1aff;">                            
			                            </a>                        
			                            <div class="media-body">
			                                <span class="text-muted pull-right">
			                                <!-- new Date(post.shareTime.split("[UTC]")[0]); -->
			                                    <small class="text-muted">${formatDate(comment.shareTime)} &nbsp;</small>
			                                </span>
			                                <strong class="text-success">&nbsp; ${user.firstName} &nbsp; ${user.lastName} &nbsp;</strong>
			                                <p>&nbsp;${comment.content}</p>
			                                <img id="pictureForComment" style=" border: 1px solid #ddd; border-radius: 4px; padding: 5px;  width: 256px; height: 128px;">
			                            </div>
			                            <br>
			                            <hr>
						`;

						if(typeof comment.picture !== "undefined"){
							var commentPicQS = commentLi.querySelector("#pictureForComment");
							var concatenateContent = "data:image/jpeg;base64,".concat(comment.picture);
							commentPicQS.src = concatenateContent;
							commentPicQS.height = 128;
							commentPicQS.width = 256;				
						}if(comment.picture === "") {
							var commentPicQS = commentLi.querySelector("#pictureForComment");
							commentPicQS.remove(); 
						}
						
						getListForComments.appendChild(commentLi);
					}
				}
			});
		}
	}
	
}

function getBase64Image(img) {
	  var canvas = document.createElement("canvas");
	  canvas.width = img.width;
	  canvas.height = img.height;
	  var ctx = canvas.getContext("2d");
	  ctx.drawImage(img, 0, 0);
	  var dataURL = canvas.toDataURL("image/png");
	  return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
	}

function checkYouTubeURL(url){
	    var tmp = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))((\w|-){11})(?:\S+)?$/;
	    if(url.match(tmp)){
	    	return true;
	    } else {
	    	alert("Youtube URL is not valid!");
	    return false;
	    }
}

function loadEmergencyNotification(){
		
	var currentUser = JSON.parse(localStorage.getItem("currentUser"));
	
	if(currentUser.emergencyNotification === true){		
		var request = new XMLHttpRequest();
		
		request.onreadystatechange = function (){
			if ((request.readyState == 4) && (request.status == 200)) {
				var userPosts = JSON.parse(request.responseText);
				let allPosts = document.getElementById("listGroupEN");
				allPosts.innerHTML = "";
				
				userPosts.forEach( post => {
					post.shareTime = new Date(post.shareTime.split("[UTC]")[0]);
				});
				
				userPosts.sort(function(a,b){
			    	return b.shareTime - a.shareTime;
			    });
				
				var allUsers = JSON.parse(localStorage.getItem("allUsersForPosts"));
				var tmpUser;
			    Array.from(userPosts).forEach( post => {
			    	if(post.emergencyNotification === true){
					let node = document.createElement('div');						
						for(allU of allUsers){
							if(post.userId == allU.id){
								tmpUser = allU;
							}
						}
						
						
					node.innerHTML = `					
				<a href="#${post.id}" class="list-group-item list-group-item-action flex-column align-items-start active">
   			 <div class="d-flex w-100 justify-content-between" max-width: 200px;>
      		<h3 class="mb-1" >Emergency notification</h3>
      		<!--                <span class="text-muted pull-right">
                                    <small class="text-muted">${formatDate(post.shareTime)} &nbsp;</small>
                                </span> -->
     		   <span class="text-muted pull-right">
                     <small class="text-muted">${formatDate(post.shareTime)} &nbsp;</small>
               </span>
   	 	</div>
   			 <p class="mb-1" style="width: 250px; max-height: 2000px;">${(post.contents)}
   			 </p>
    		 <small>${tmpUser.firstName}&nbsp;${tmpUser.lastName}</small>
    		<!-- <a href="#${post.id}">Go to post</a> -->
  		</a><br>
  		
			        `;
					allPosts.appendChild(node);
			    }
				});
				
				
				}
			}
	    request.open("GET", "api/post", true);
	    request.send();
	}
}

