var myBattutaApiKey = "c3dd144e449f000c5a260a6aae408262"; //"c3dd144e449f000c5a260a6aae408262";


function init(){
	getCountries();
    document.getElementById("country").onchange = getRegions;
    document.getElementById("region").onchange = getCities;
}


function getCountries() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function(){
		if(request.readyState == 4 && request.status == 200){
			fillSelectWithCountries(request.responseText);
		}
	}
	
	request.open("GET", "https://restcountries.eu/rest/v2/region/europe", true);
	request.send(null);
}

function fillSelectWithCountries (jsonCountries) {
	var countries = JSON.parse(jsonCountries);
	var selectCountriesElement = document.getElementById("country");
	var selectCitiesElement = document.getElementById("city");
	resetSelectElement(selectCountriesElement);
	resetSelectElement(selectCitiesElement);
	
	for(country of countries){
		let option = document.createElement("option");
		
		option.textContent = country.name + " (" +country.alpha3Code + ")";
		option.value = country.alpha2Code;
		selectCountriesElement.appendChild(option);
	}
}

function resetSelectElement(selectElement) {
	var length = selectElement.options.length;
	for (i = length-1; i >= 0; i--) {
		selectElement.options[i] = null;
	}
	
}

function getRegions(){
	var alpha2Code = document.getElementById("country").value;
		
	fillSelectRegions(`http://battuta.medunes.net/api/region/${alpha2Code}/all/?key=${myBattutaApiKey}&callback=cb`, "cb");
	var tmp = document.getElementById("country");
	var str = tmp.options[tmp.selectedIndex].text;
	var strstr = str.substring(
		    str.lastIndexOf("(") + 1, 
		    str.lastIndexOf(")")
			);
	var link = "https://restcountries.eu/data/" + strstr.toLowerCase() + ".svg"
	document.getElementById("countryFlag").value = link;
	if(document.getElementById("pictureFile").value === ""){
	document.getElementById("userProfilePicture").src = link;
	} else {
		document.getElementById("userProfilePicture").src = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTWRolIN3VPIBYPi8lhOfMwbCYfy0ez7cgIyeR1qJFrO0OAKd-3&usqp=CAU";	
	}
	if(document.getElementById("region").value = ""){
		fillSelectCities();
	}
}

/*
 * https://stackoverflow.com/questions/14666658/document-createelementscript-vs-script-src
The <script src=...> blocks the browser while document.createElement('script') loads the JavaScript asynchronously; this is the primary reason.
The <script src=...> tag blocks browser from displaying rest of the page until the script is loaded and executed
*/

function fillSelectRegions(url, callback){
	var e = document.createElement('script');
	e.src = url;
	document.body.appendChild(e);
	window[callback] = (data) => {
		var selectRegionsElement = document.getElementById("region");
		var selectCitiesElement = document.getElementById("city");
		resetSelectElement(selectRegionsElement);
		resetSelectElement(selectCitiesElement);

		for(region of data){
			let option = document.createElement("option");
			option.textContent = region.region;
			option.value = region.region;
			selectRegionsElement.appendChild(option);
		}
		getCities();
	}
}

//"http://battuta.medunes.net/api/city/search/?region={REGION_NAME_HINT}&city={CITY_NAME_HINT}&key={YOUR_API_KEY}"
//http://battuta.medunes.net/api/city/BA/search/?region=Republika Srpska&key=c3dd144e449f000c5a260a6aae408262&callback=cb
function getCities(){
	var alpha2Code = document.getElementById("country").value;
	var region = document.getElementById("region").value;
	console.log("alpha2Code: "+alpha2Code);
	console.log("region: "+region);
	fillSelectCities("http://battuta.medunes.net/api/city/"+alpha2Code+"/search/?region="+region+"&key="+myBattutaApiKey+"&callback=cb", "cb");
}

function fillSelectCities(url, callback){
	var e = document.createElement('script');
	e.src = url;
	document.body.appendChild(e);
	window[callback] = (data) => {
		var selectCitiesElement = document.getElementById("city");
		resetSelectElement(selectCitiesElement);
		for(city of data){
			let option = document.createElement("option");
			option.textContent = city.city;
			option.value = city.city;
			selectCitiesElement.appendChild(option);
		}
	}
}





