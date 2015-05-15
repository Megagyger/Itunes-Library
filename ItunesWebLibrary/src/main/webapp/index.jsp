<!DOCTYPE HTML>

<html>

<head>
<!-- Bootstrap Core CSS -->
<link href="Resources/css/bootstrap1.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="Resources/css/simple-sidebar.css" rel="stylesheet">
<link href="Resources/css/StyleSheet.css" rel="stylesheet">

<script src="Resources/js/jquery-1.6.1.min.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="Resources/js/bootstrap.min.js"></script>
</head>

<body class="index">

	<div class="pageHead">
		<br>
		<h1><b>ITunes Library</b></h1>
		<h2>Welcome</h2>
	</div>
	
	<div id="wrapper">
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li><a href="#" onclick="toggle('loginDiv');">LogIn</a></li>
				<li><a href="#" onclick="toggle('registerDiv');">Register</a></li>			
			</ul>
		</div>
		
		
	<div id="loginDiv" class="col-md-4 col-md-offset-2">
		<form action="http://localhost:8080/dssAssignment/LoginServlet" id='form' method="post" 
					onsubmit="return check_form();">
		<h2 class="form-signin-heading">Please sign in</h2>
		<br> <input id="userName" name="userName" type="text"
			placeholder="UserName" class="form-control" required autofocus><br>

		<input name="password" id="password" type="password"
			placeholder="Password" class="form-control" required><br>

		<button class="btn btn-lg btn-primary btn-block" name="submit"
			id="submit" value="Login">Log In</button>
	</form>

	</div>

	<div id="registerDiv" class="col-md-10 col-md-offset-2">
			
			<div class="form-group">				
				
				<div class="col-sm-5">	
				<h2 class="form-signin-heading">Register</h2><br>
				
						<input type="text" class="form-control" name="userNameReg"
								id="userNameReg" placeholder="UserName" autofocus required>
					<br>
						
						<input type="password" class="form-control" name="passwordReg"
								id="passwordReg" placeholder="Password" required>
					<br>
					
					
						<input type="text" class="form-control" name="persistIDReg"
								id="persistIDReg" placeholder="PersistID" required><br>
								
								<button class="btn btn-lg btn-primary btn-block" name="reg"
						id="reg" value="Login">Register</button>
				</div>
			</div>			
	</div>
</div>

	
	
<script>

$(function(){
	var correct;
	
	$( "#reg" ).click(function(e) {
		var name = $('#userNameReg').val();
		var pass = $('#passwordReg').val();
		var pID = $('#persistIDReg').val();
		correct = true;

		var regFormIsValid = checkRegFormValidity(name, pass, pID);
		
		if(regFormIsValid==true){

			for (var i = 0; i < userData.length; i++) {
				if(userData[i]===name){
					alert('Username already taken')
					correct = false;
				}
			}

			for(var j = 0; j < persistData.length; j++){
				if(persistData[j]===pID){
					alert('PersistenceID already taken');
					correct = false;
				}
			}
			
			if(pass.length<4){
				alert('Please enter a password greater than 3 characters');
				correct = false;
			}

			if(correct === true){

				newUser = {
						userName: name,
						password: pass,
						persistenceID: pID
						}
					
					$.ajax({
						type: 'POST',
						url:'http://localhost:8080/dssAssignment/rest/user/add',
					    dataType: 'json',
						data:JSON.stringify(newUser),
						contentType: "application/json",
						success: function(){
							alert('Success, Please log in');
							location = ('http://localhost:8080/dssAssignment/index.jsp');
						}					
					});	
			}		
		}
	});	
});

function checkRegFormValidity(name, pass, pID){
	if(name.match(/^[A-Za-z0-9 ]+$/)){}
	else{
		alert('Please enter a username');
		return false;
	}
	if(pass.match(/^[A-Za-z0-9]+$/)){}
	else{
		alert('Please enter a valid password containing no white space');
		return false;
	}
	if(pID.match(/^[A-Za-z0-9]+$/)){}
	else{
		alert('Please enter a valid persistence ID');
		return false;
	}

	return true;
	
}



</script>	
	
	
	
	
	
<script>
var userData;
var persistData;

$(function(){

	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/dssAssignment/rest/user/userNames',
		dataType: 'json',
		contentType: "application/json",
		success: function(retData){
			userData = retData;
		}
	});	

	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/dssAssignment/rest/user/persistIDs',
		dataType: 'json',
		contentType: "application/json",
		success: function(retData){
			persistData = retData;
		}

		});
});


</script>



<script>


function check_form(){
var result;
	
	for (var i = 0; i < userData.length; i++) {
		if(userData[i]===$('#userName').val()){
			alert('Welcome back '+$('#userName').val());
			result = true;
			break;
			}
		else{
			result = false;
		}
	}

	if(result==false){
		alert('Username does not exist');
		return result;
	}
	else
		return result;
		
}

</script>	
	
<script>

var divs = ["loginDiv","registerDiv"];
var visibleDiv = null;
var ids = [];


$(function(){

	document.getElementById("loginDiv").style.display='none';
	document.getElementById("registerDiv").style.display='none';
	
});

function toggle(divId){
	if(visibleDiv === divId) {
		  visibleDiv = null;
	} else {
		    visibleDiv = divId;
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

