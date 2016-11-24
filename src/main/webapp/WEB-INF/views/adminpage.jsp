<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/css/CSS_template.css">
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<title>PubSong</title>
</head>
<body>
	<h1>ADMIN</h1>
	
	<table>
		<tr>
			<th>Naam</th>
		</tr>
		<tr>
			<td><input type="text" id="userinput"></td>
		</tr>
		<tr>
			<td><input type="submit" id="zoek-user" value="zoek"></td>
		</tr>
	</table>
	
	<table>
		<tr>
			<th> Naam </th>
			<th> Votes </th>
			<th> Rechten </th>
		</tr>
		<tr>
			<td><input type="text" id="userNaam"></td>
			<td><input type="text" id="userVotes"></td>
			<td><input type="text" id="userRights"></td>
		</tr>
		<tr>
			<td><input type="submit" id="save-user" value="Save"></td>
		</tr>
	</table>
	
	<script>
	$(document).ready(function() {
		$('#zoek-user').click(function(){
			$.post("findUser", {
				userNaam: $('#userinput').val()
			},
			function(userdata) {
				$('#userNaam').val(userdata.userName);
				$('#userVotes').val(userdata.votes);
				$('#userRights').val(userdata.rightsString);
			});
		});
		$('#save-user').click(function(){
			$.post("saveUser", {
				userNaam: $('#userNaam').val(),
				userVotes: $('#userVotes').val(),
				userRights: $('#userRights').val()
			});
		});
	});
	</script>
</body>
</html>