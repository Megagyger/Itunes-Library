<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">


<!-- Bootstrap Core CSS -->
<link href="Resources/css/bootstrap1.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="Resources/css/simple-sidebar.css" rel="stylesheet">
<link href="Resources/css/StyleSheet.css" rel="stylesheet">

<script src="Resources/js/jquery-1.6.1.min.js"></script>
<script src="Resources/js/bootstrap.min.js"></script>

<!-- The following are libraries so I can use bootstrap table for pagination -->

<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">   
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<!-- <link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css"></style> -->
<script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>



<body class='userPage'>

<%
	String user = null;
	if(session.getAttribute("pID")==null){
		response.sendRedirect("index.jsp");
		
	}else user = (String) session.getAttribute("pID");
	String persistID = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
	for(Cookie cookie : cookies){
    if(cookie.getName().equals("persistID")) persistID = cookie.getValue();
    if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
	}
}
%>
	

<nav>
		<div class = "navCalvin">		
				<li><a href="#"> Menu </a></li>
				<li><a href="#">Track Stuff</a>
					<ul>
						<li><a href="#" onclick="toggle('allTracks');">See all Tracks</a></li>
						<li><a href="#" onclick="toggle('artistTracks');">Tracks by Artist</a></li>
						<li><a href="#" onclick="toggle('albumTracks');">Tracks by Album</a></li>
						<li><a href="#" onclick="toggle('genreTracks');">Tracks by Genre</a></li>
						<li><a href="#" onclick="toggle('addTrack');">Add Track</a></li>
						<li><a href="#" onclick="toggle('editTrack');">Edit Track</a></li>
					</ul>
				</li>
				<li><a href="#">Playlist Stuff</a>
					<ul>
						<li><a href="#" onclick="toggle('createPlaylistDiv');">Create Playlist</a></li>
						<li><a href="#" onclick="toggle('modPlaylistDiv');">Modify Playlist's</a></li>
						<li><a href="#" onclick="toggle('viewPlaylistsDiv');">View Playlist Tracks</a></li>
					</ul>
				</li>
				<li><a href="http://localhost:8080/dssAssignment/LogoutServlet">Log out</a></li>
		</div>
	</nav>	

		
<div id="page-content-wrapper">

	<div id="createPlaylistDiv">	
		<div id='trackSelectionDiv'>
			<div class="form-group">				
			<div class="col-sm-5">	
				<h1 class="form-signin-heading"><b>Create Playlist</b></h1>
					
					<br><p><b>Hold down control key to select multiple tracks</b></p><br>
					<select id=createPlaylisttrackDiv multiple>
					</select>
					<br><button class='myButton' name="addTrackButton"
						id="createPlaylistButton" value="Add">Create Playlist</button>
			</div>
			</div>	
		</div>	
	</div>
	
	
	
	
	
	
	<div id="modPlaylistDiv">
	<h1>Modify Playlist</h1>
		<div id='selectPlistIdsMod'>
			<select id='plistIDSelect'></select><br>		
			<button class='myButton' id='plistIDSelectionButton'>Select Playlist</button><br>
			
		</div>
		
		<div id='tracksFromPlistMod'>
			<br><select id='plistTrackSelect'></select><br>
			<button class='myButton' id='plistIDSelectionButtonRemove'>Delete Track</button><br>
			<br><button class='myButton' id='plistDeleteButton'>Delete Playlist</button><br>
					
		</div>
		<div id='trackToAddDiv'>
			<br><select  id='allTracksForPlistAddition'></select><br>
			<button class='myButton' id='plistIDSelectionButtonAddToPlist'>Add Track</button>
		</div>
	
	</div>
	
	<div id="viewPlaylistsDiv">
	<h1>View Playlist</h1>
		<div id='selectPlistIdsView'>
			<select id='plistIDSelectView'></select><br>		
			<button class='myButton' id='plistIDSelectionViewButton'>Select Playlist</button><br>
		</div>	
		<div id='playlistTableDiv'>
		</div>
	</div>

	
	<div id="allTracks">
	<div class="table-responsive">
	<table id = "allTracksTable" class="table table-striped">
		<thead>
		<tr><th>Title</th><th>Artist</th><th>Album</th><th>Genre</th></tr>
		</thead>	
		<tbody>	
		</tbody>	
	</table>
	</div>	
	</div>
	
	<div id="artistTracks">
		<div class="form-group" id="myDiv">
			<label class="control-label col-sm-2" for="ID">Artist:</label> 
			<select class="col-sm-5" id="artistSelect"></select>
			<br><button id='submitByArtist' name='submitByArtist' class="btn btn-primary">Search</button>
		</div>	
		<div id='artistTableDiv'>
		</div>
	</div>
	
	<div id="albumTracks">
	<div class="form-group" id="myDiv">
			<label class="control-label col-sm-2" for="ID">Album:</label> 
			<select class="col-sm-5" id="albumSelect"></select>
			<br><button id='submitByAlbum' name='submitByAlbum' class="btn btn-primary">Search</button>
		</div>
		<div id="albumTableDiv">
		</div>
	</div>
	
	<div id="genreTracks">
	<div class="form-group" id="myDiv">
			<label class="control-label col-sm-2" for="ID">Genre:</label> 
			<select class="col-sm-5" id="genreSelect"></select>
			<br><button id='submitByGenre' name='submitGenre' class="btn btn-primary">Search</button>
		</div>	
	
		<div id="genreTableDiv">		
		</div>
	</div>
	
	<div id="addTrack" class="col-md-10 col-md-offset-2">
		<div class="form-group">				
			<div class="col-sm-5">	
				<h2 class="form-signin-heading">Add Track</h2>
					<br><input type="text" class="form-control" name="addTrackName"
								id="addTrackName" placeholder="TrackName" required>
					<br><input type="text" class="form-control" name="addTrackArtist"
								id="addTrackArtist" placeholder="TrackArtist" required>
					<br><input type="text" class="form-control" name="addTrackAlbum"
								id="addTrackAlbum" placeholder="TrackAlbum" required>
					<br><input type="text" class="form-control" name="addTrackGenre"
								id="addTrackGenre" placeholder="TrackGenre" required>
					<br><button class="btn btn-lg btn-primary btn-block" name="addTrackButton"
						id="addTrackButton" value="Add">Add Track</button>
			</div>
		</div>			
	</div>
	
	<div id="editTrack">
		<div id="editTrackSelectionDiv">
			<select id="trackToEditSelectBox"></select>
			<br><button id='trackEditButton' name='trackEditButton' class="btn btn-primary">Edit</button>
		</div>
		
		<div class="form-group">				
			<div class="col-sm-5">	
				<h2 class="form-signin-heading">Edit Track</h2>
					<br><input type="text" class="form-control" name="addTrackName"
								id="editTrackName" placeholder="TrackName" required>
					<br><input type="text" class="form-control" name="addTrackArtist"
								id="editTrackArtist" placeholder="TrackArtist" required>
					<br><input type="text" class="form-control" name="addTrackAlbum"
								id="editTrackAlbum" placeholder="TrackAlbum" required>
					<br><input type="text" class="form-control" name="addTrackGenre"
								id="editTrackGenre" placeholder="TrackGenre" required>
					<br><button class="btn btn-lg btn-primary btn-block" name="submitTrackEdit"
						id="submitTrackEdit" value="Add">Submit Edit</button>
			</div>
		</div>
		
	
	</div>		
</div>
<script>

// Variale to hold the persistence id
// throughout the session
var pID = '<%= session.getAttribute("pID") %>';
var tracksList = [];


// All JQ/JS for adding a track
$(function() {
	$("#addTrackButton").click(function(e) {

		// Variables that hold user input
		var nameAdd = $('#addTrackName').val();
		var artistAdd = $('#addTrackArtist').val();
		var albumAdd = $('#addTrackAlbum').val();
		var genreAdd = $('#addTrackGenre').val();

			// Increment track id as must be unique
			trackIDInc += 1;

			var correctTrackEntry = checkTrackEntry(nameAdd,artistAdd,albumAdd,genreAdd);

			if(correctTrackEntry==true){
				$.ajax({
					type:'GET',
					url:'http://localhost:8080/dssAssignment/rest/track/addTrackByUser/'+trackIDInc+
					'£'+nameAdd+'£'+artistAdd+'£'+albumAdd+'£'+genreAdd+'£'+pID,
					success: function(){
						alert('Your track has been added to database');
						location = 'http://localhost:8080/dssAssignment/userPage.jsp';
						}
					});
			}		
	});
});

function checkTrackEntry(name,artist,album,genre){
	var result;

	if(name.match(/^[A-Za-z0-9 ]+$/)){
		for(var i=0; i<tracksList.length; i++){
			var x=tracksList[i];
			if(name==x){
				alert('Track already exists');
				result = false;
				break;
			}
			result=true;
		}
		
	}else {
			alert('Enter a track title');
			return false;
		}
	if(artist.match(/^[A-Za-z0-9$£.%:/@# ]+$/)){}
	else{
		alert('Enter artist');
		return false;
		}
		
	
	if(album.match(/^[A-Za-z0-9$£.%:/@# ]+$/)){}
	else{
		alert('Enter album');
		return false;
		}
	
	if(genre.match(/^[A-Za-z0-9 ]+$/)){}
	else{
		alert('Enter genre');
		return false;
		}
	
		

	return result;
	
	
}

</script>

<script>

// this function gets called when a 
// track is added or deleted to a selected
// playlist as it updates without a reload
function updatePlayList(){
	$('#plistTrackSelect').empty();
	
	$.ajax({
		type:'GET',
		url: 'http://localhost:8080/dssAssignment/rest/playlist/playlistTracks/'+selectedPlist,
		contentType:'application/json',
		success:function(data){
			$.each(data, function(i, value){
				var option = document.createElement('option');
				option.text = value[0]+'-'+'Name: '+value[1]+' Artist: '+value[2];
				$('#plistTrackSelect').append(option);
			});
		}
	});
	
}
</script>




<script>
// global variables to hold the selected playlist to modify
var plistSelect;
var selectedPlist; 
var playlistSelected;
var playlistIdsArray;



function getPlaylistIds(){
	// Here we get all the playlist IDs so the created one will
	// be a unique id
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/dssAssignment/rest/playlist/playlistIds',
		contentType: 'application/json',
		success: function(data){
			playlistIdsArray = data;
		}
	});		
}

/* All functions contained in this script build the appropriate tables/dropdowns  */
$(document).ready(function(){
	
	// Variable to hold the maximum trackID so we
	// can increment it to a unique value when adding a new track
	var trackIDInc;
	

	// calling this to generate Ids
	getPlaylistIds();

	$('#plistIDSelectionViewButton').click(function(e){
		removeData();
		createTableHeaders('plistView');
		createButton();
		var playSelect = document.getElementById('plistIDSelectView');
		var selectedViewPlist = playSelect.options[playSelect.selectedIndex].text;

		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/dssAssignment/rest/playlist/playlistTracks/'+selectedViewPlist,
			contentType: 'application/json',
			success: function(data){
				$.each(data, function(i, track){
					$('#plistViewTable').find('tbody').append('<tr><td>'+track[0]+'</td><td>'
							+track[1]+'</td><td>'+track[2]+'</td></tr>');
					});
				$('#plistViewTable').dataTable();
				}
		});	
	});

	// Pressing this butting fills up the form fields with the
	// original data of the track which is selected to edit
	$('#trackEditButton').click(function(e){
		editTrackSelect = document.getElementById('trackToEditSelectBox');
		selectedEditTrack = editTrackSelect.options[editTrackSelect.selectedIndex].text;
		editTrackId = selectedEditTrack.split('-')[0];

		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/dssAssignment/rest/track/trackById/'+editTrackId,
			contentType: 'application/json',
			success: function(data){
				document.getElementById("editTrackName").value = data.name;
				document.getElementById("editTrackArtist").value = data.artist;
				document.getElementById("editTrackAlbum").value = data.album;
				document.getElementById("editTrackGenre").value = data.genre;
			}
		});		
	});

	$('#submitTrackEdit').click(function(e){
		var name = document.getElementById("editTrackName").value;
		var artist = document.getElementById("editTrackArtist").value;
		var album = document.getElementById("editTrackAlbum").value;
		var genre = document.getElementById("editTrackGenre").value;

		if((name.match(/^[A-Za-z0-9$£.%:/@# ]+$/))&&(artist.match(/^[A-Za-z0-9$£.%:/@# ]+$/))&&(album.match(/^[A-Za-z0-9$£.%:/@# ]+$/))&&(genre.match(/^[A-Za-z0-9$£.%:/@# ]+$/))){
			$.ajax({
				type: 'GET',
				url: 'http://localhost:8080/dssAssignment/rest/track/update/'+editTrackId+'£'+name+'£'+artist+'£'+album+'£'+genre,
				contentType: 'application/json',
				success: function(data){
					alert('successfully updated');
					$('#trackToEditSelectBox').empty();
					ajaxGetAll();
				}
			});
		}
		else{
			alert('Select Track/Invalid Filed Entries');
			}
	});

	// Playlist create
	$('#createPlaylistButton').click(function(e){
		// Creating unique playlistId
		var newPlaylistId=0;
		var tIDArray = [];

		// Going through all playlist ids and incement
		// by one so we have a unique playlist id.
		for(var j=0; j<playlistIdsArray.length;j++){
			var num = parseInt(playlistIdsArray[j]);		
			if(num>=newPlaylistId){
				newPlaylistId = num+1;
			}
		}
		
		var selectList = document.getElementById('createPlaylisttrackDiv');		

		// Adding all our selected trackIds to an array for
		// adding to the playlist
		for(var i=0; i<selectList.length;i++){
			option = selectList[i];
			if(option.selected){
				var x = option.text;
				tIDArray.push( x.split('-')[0]);
			}
		}

		// Check if at least 1 selection is made
		if(tIDArray.length<1){
			alert('Please select a track/s');
			return;
		}

		// Cycle thorugh tarcklist adding each one
		// to the playlist in the database
		for(var n=0; n<tIDArray.length;n++){

			var lastTrackRecord = tIDArray.length;
			var trackInc = 0;
			
			$.ajax({
				type: 'GET',
				url: 'http://localhost:8080/dssAssignment/rest/playlist/createPlist/'+newPlaylistId+'£'+pID+'£'+tIDArray[n],
				contentType: 'application/json',
				success: function(data){
					// We only need to do this one time  after the last track is added
					trackInc++;
					if(trackInc==lastTrackRecord){
						trackInc++;
						alert('Playlist Created');
						getPlaylistIds();
						location = 'http://localhost:8080/dssAssignment/userPage.jsp';
					}			
				}
			});
		}			
	});// end of playlist creation

	

	// Function that gets all tracks/artists/albums and genres
	// stored in the library
	ajaxGetAll();

	// Pressing this button fills up a select menu of all
	// the tracks that are in the selected playlist. The 
	// Now the user can choose a track to delete from the 
	// list or move on to adding a track to the list 
	$('#plistIDSelectionButton').click(function(e){
		plistSelect = document.getElementById('plistIDSelect');
		selectedPlist = plistSelect.options[plistSelect.selectedIndex].text;
		playlistSelected = selectedPlist;
		updatePlayList();
		fillUpForPlistTrackAddition();
		
	});
	
	// we get here when the user has selected a track to delete
	// from the playlist. Splitting the option to get the track id
	// as this is what is used to join the track and playlist tables
	
	$('#plistIDSelectionButtonRemove').click(function(e){
		var removeTrack = document.getElementById('plistTrackSelect');
		var selectedTrack = removeTrack.options[removeTrack.selectedIndex].text;
		var trackID = selectedTrack.split('-')[0];

		$.ajax({
			type: 'DELETE',
			url: 'http://localhost:8080/dssAssignment/rest/playlist/delete/'+playlistSelected+'£'+trackID+'£'+pID,
			contentType: 'application/json',
			success: function(){
				alert('Successfully Deleted');
				updatePlayList();
				}
		});
	});

	$('#plistDeleteButton').click(function(e){
		var confirmChoice = confirm("Confirm delete");
		if (confirmChoice == true) {

			$.ajax({
				type: 'DELETE',
				url: 'http://localhost:8080/dssAssignment/rest/playlist/deletePList/'+playlistSelected,
				contentType: 'application/json',
				success: function(){
					alert('Successfully Deleted');
					fillUpSeletionsOfPlaylistIDs();
					$('#plistTrackSelect').empty();
					}
			});
			
		} else {
			alert('Cancelled');
		}
	});

	// We enter here when the user adds a track to playlist. 
	$('#plistIDSelectionButtonAddToPlist').click(function(e){
		var addTrack = document.getElementById('allTracksForPlistAddition');
		var selectedTrack = addTrack.options[addTrack.selectedIndex].text;

		var trackID = selectedTrack.split('-')[0];

		
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/dssAssignment/rest/playlist/addTrack/'+playlistSelected+'£'+trackID+'£'+pID,
			contentType: 'application/json',
			success: function(){
				alert('Successfully Added');
				updatePlayList();
				}
		});		
	});

	// Here all 3 ajax calls dependent on a button switch
	// use the information got in the ajaxGetAll function
	// to return all tracks by artist/album/genre
	$( "#submitByArtist" ).click(function(e) {
		removeData();
		createTableHeaders('artist');
		var artistSelect = document.getElementById('artistSelect');
		var selectedArtist = artistSelect.options[artistSelect.selectedIndex].text;

		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/dssAssignment/rest/track/artist/'+selectedArtist,
			dataType: 'json',
			contentType: 'application/json',
			success: function(data){
				$.each(data, function(i, track){
					$('#allTracksByArtist').find('tbody').append('<tr><td>'+track.name+'</td><td>'
							+track.album+'</td><td>'+track.genre+'</td></tr>');
					});
				$('#allTracksByArtist').dataTable();
				}
		});
	});

	// All tracks by album
	$( "#submitByAlbum" ).click(function(e) {
		removeData();
		createTableHeaders('album');
		var albumSelect = document.getElementById('albumSelect');
		var selectedAlbum = albumSelect.options[albumSelect.selectedIndex].text;
		
		
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/dssAssignment/rest/track/album/'+selectedAlbum,
			dataType: 'json',
			contentType: 'application/json',
			success: function(data){
				$.each(data, function(i, track){
					$('#allTracksByAlbum').find('tbody').append('<tr><td>'+track.name+'</td><td>'
							+track.artist+'</td><td>'+track.genre+'</td></tr>');
					});
				$('#allTracksByAlbum').dataTable();
				}
		});
	});

	// all tracks by genre
	$( "#submitByGenre" ).click(function(e) {
		removeData();
		createTableHeaders('genre');
		var genreSelect = document.getElementById('genreSelect');
		var selectedGenre = genreSelect.options[genreSelect.selectedIndex].text;

		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/dssAssignment/rest/track/genre/'+selectedGenre,
			dataType: 'json',
			contentType: 'application/json',
			success: function(data){
				$.each(data, function(i, track){
					$('#allTracksByGenre').find('tbody').append('<tr><td>'+track.name+'</td><td>'
							+track.album+'</td><td>'+track.artist+'</td></tr>');
					});
				$('#allTracksByGenre').dataTable();
				}
		});
	});
		
});

// Here we dynamically create the table structure
// for a passed table name which indicates
// what header values to use for example
// artist is passed so the artist table 
// will have three headers Name, Album, Genre
// Each time we just get the container div of the 
// table
function createTableHeaders(tableName){
	var tableDiv;
	var divContainer = document.createElement('div');
	divContainer.setAttribute('class', 'table-responsive');
	divContainer.setAttribute('id', 'divContainer');
	var table=document.createElement('table');
	table.setAttribute('class', 'table table-striped');
	var header = document.createElement('thead');
	var body = document.createElement('tbody');
	var row = document.createElement('tr');
	var colOne=document.createElement('td');
	var colTwo=document.createElement('td');
	var colThree=document.createElement('td');

	if(tableName=='plistView'){
		tableDiv = document.getElementById('playlistTableDiv');
		table.setAttribute('id', 'plistViewTable'); 
		colOne.innerHTML='TRACK ID';
		colTwo.innerHTML='NAME';
		colThree.innerHTML='ARTIST';
		
	}
	
	else if(tableName=='artist'){
		tableDiv = document.getElementById('artistTableDiv');
		table.setAttribute('id', 'allTracksByArtist');
		colOne.innerHTML='NAME';
		colTwo.innerHTML='ALBUM';
		colThree.innerHTML='GENRE';
		
	}
	else if(tableName=='album'){
		tableDiv = document.getElementById('albumTableDiv');
		table.setAttribute('id', 'allTracksByAlbum');
		colOne.innerHTML='NAME';
		colTwo.innerHTML='ARTIST';
		colThree.innerHTML='GENRE';
		}
	else{
		tableDiv = document.getElementById('genreTableDiv');
		table.setAttribute('id', 'allTracksByGenre');
		colOne.innerHTML='NAME';
		colTwo.innerHTML='ALBUM';
		colThree.innerHTML='ARTIST';
	}
	
	row.appendChild(colOne);
	row.appendChild(colTwo);
	row.appendChild(colThree);
	header.appendChild(row);
	table.appendChild(header);
	table.appendChild(body);
	divContainer.appendChild(table);
	tableDiv.appendChild(divContainer);
	
}
function createButton(){

	var butDiv=document.createElement('div');
	butDiv.setAttribute('class', "col-sm-offset-12 col-sm-10");
	var button=document.createElement(button);
	button.setAttribute('id', 'tableButton');
	button.setAttribute('class','btn btn-primary');
	button.setAttribute('position', 'absolute');
	button.setAttribute('top', '50%');
	button.innerHTML='Search Again';
	button.addEventListener('click', removeData);
	butDiv.appendChild(button);
	$('#divContainer').append(butDiv);
}

// This is called each time a user wishes
// display another query so the containing
// table divs are emptied
function removeData(){
	$('#artistTableDiv').empty();
	$('#albumTableDiv').empty();
	$('#genreTableDiv').empty();
	$('#playlistTableDiv').empty();
}

function fillUpForPlistTrackAddition(){
	$.ajax({
		 type: 'GET',
		 url: 'http://localhost:8080/dssAssignment/rest/track',
		 contentType: 'application/json',
		 success: function(data){
			$.each(data, function(i, track){
					// Here we are filling up an options table so users can choose tracks from
					// here to add to their playlist
					var trackOption = document.createElement('option');
					trackOption.setAttribute('background-color','#ffff00');
					trackOption.text=track.trackID+"- Name:"+track.name+" Artist:"+track.artist;
					$('#allTracksForPlistAddition').append(trackOption);
					
				});
			 } 
		});	
}

// This is called when the page loads so we can see all the tracks
// get all artists, genres, albums when teh user wishes.
function ajaxGetAll(){
	
	// Get all tracks(name, artist, genre, title) from library and put in table
	 $.ajax({
		 type: 'GET',
		 url: 'http://localhost:8080/dssAssignment/rest/track',
		 contentType: 'application/json',
		 success: function(data){
			$.each(data, function(i, track){
					$("#allTracksTable").find("tbody").append('<tr><td>'+track.name+'</td><td>'+track.artist+'</td><td>'
					+track.album+'</td><td>'+track.genre+'</td></tr>');
					trackIDInc = track.trackID;
					// Here we are filling up an options table so users can choose tracks from
					// here to add to their playlist
					var trackOption = document.createElement('option');
					trackOption.text=track.trackID+"- Name:"+track.name+" Artist:"+track.artist;
					tracksList.push(track.name);
					$('#createPlaylisttrackDiv').append(trackOption);
			});
			$('#allTracksTable').dataTable();
			 } 
		});

	// Gets all the tracks that a user has added for modification
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/dssAssignment/rest/track/tracksEdit/'+pID,
		contentType: 'application/json',
		success: function(data){
			$.each(data, function(i, d){
			var optionEditTrack = document.createElement('option');
			optionEditTrack.text = d.trackID+"-"+ "Name: "+d.name+" Artist: "+d.artist;
			$('#trackToEditSelectBox').append(optionEditTrack);
			});
		}
	});
		
	// Get all artists who have tracks in the library
	 $.ajax({
		 type: 'GET',
		 url: 'http://localhost:8080/dssAssignment/rest/track/artists',
		 contentType: 'application/json',
		 success: function(data){
			var artists = data
			for(var i=0; i<artists.length; i++){
				var x =artists[i];
				var option = document.createElement('option');
				option.text = x;
				$('#artistSelect').append(option);
				}
			 } 
		});

	// Get all the genres of music stored in library
	 $.ajax({
		 type: 'GET',
		 url: 'http://localhost:8080/dssAssignment/rest/track/genres',
		 contentType: 'application/json',
		 success: function(data){
			var genres = data
			for(var i=0; i<genres.length; i++){
				var option = document.createElement('option');
				option.text = genres[i];
				$('#genreSelect').append(option);
				}
			 } 
		});

	// Get all albums stored in the library
	 $.ajax({
		 type: 'GET',
		 url: 'http://localhost:8080/dssAssignment/rest/track/albums',
		 contentType: 'application/json',
		 success: function(data){
			var albums = data
			for(var i=0; i<albums.length; i++){
				var option = document.createElement('option');
				option.text = albums[i];
				$('#albumSelect').append(option);
				}
			 } 
		});

		fillUpSeletionsOfPlaylistIDs();
		
}

function fillUpSeletionsOfPlaylistIDs(){
	// Get all playlistIDs that are stored in the library for a given persistent id
	$('#plistIDSelect').empty();
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/dssAssignment/rest/playlist/playListIDs/'+pID,
		contentType: 'application/json',
		success: function(data){
			if(data==null){
				alert('Make a playlist or add a tracks to the library');
			}
			else{
				var ids=data;
				for(var i=0; i<ids.length;i++){
					var option=document.createElement('option');
					option.text=ids[i];
					$('#plistIDSelect').append(option);
				}				
			}
		}
	});
}

function addOptions(){

	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/dssAssignment/rest/playlist/playListIDs/'+pID,
		contentType: 'application/json',
		success: function(data){
			if(data==null){
				alert('No playlists to view');
			}
			else{
				var ids=data;
				for(var i=0; i<ids.length;i++){
					var option=document.createElement('option');
					option.text=ids[i];
					$('#plistIDSelectView').append(option);
				}				
			}
		}
	});
}

</script>


<script>
// Here when a user cliscks on an option only stuff relevent
// to that option will be shown on the screen
var divs = ["artistTracks","allTracks","albumTracks","genreTracks","editTrack",
            "addTrack","modPlaylistDiv","viewPlaylistsDiv","createPlaylistDiv"];
var visibleDiv = null;
var ids = [];

$(function(){

	document.getElementById("artistTracks").style.display='none';
	document.getElementById("allTracks").style.display='none';
	document.getElementById("albumTracks").style.display='none';
	document.getElementById("genreTracks").style.display='none';
	document.getElementById("addTrack").style.display='none';
	document.getElementById("editTrack").style.display='none';
	document.getElementById("modPlaylistDiv").style.display='none';
	document.getElementById("createPlaylistDiv").style.display='none';
	document.getElementById("viewPlaylistsDiv").style.display='none';
	
});

function toggle(divId){
	if(visibleDiv === divId) {
		  visibleDiv = null;
	} else {
		    visibleDiv = divId;
		}

	if(divId == 'viewPlaylistsDiv'){
		addOptions();
	}

	hideOtherDivs();
}

function hideOtherDivs(){
	var i, divId, div;

	for(i = 0; i <divs.length; i++){
		divId = divs[i];
		div = document.getElementById(divId);

		if(visibleDiv == divId){
			div.style.display = 'block';
		}else{
			div.style.display = 'none';
		}
	}
}

</script>	

</body>

</html>
