<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/css/CSS_template.css">
<title>PubSong</title>
</head>
<body>
	<h1>LOGIN</h1>
	<img src="/css/PubSong2.png" alt="PubSong logo" align="right"
		align="top">
	<table style="width: 10%">
		<tr>
			<form action="" method="post">
				<th><h8>Username:</h8></th>
				<th><input type="text" name="username"></th> <br>
		</tr>
		<tr>
			<th><h8>Password:</h8></th>
			<th><input type="password" name="password"></th>
			<br>
		</tr>
		<tr>
			<th><input type="submit" class="login-button" value="log in"></th>
		</tr>
		</form>
		</td>
		</tr>
	</table>
	<br>
	<h8>Of creëer een nieuw account:</h8>
	<table>
		<form action="/newUser">
			<input type="submit" class="new-user" value="New user">
		</form>
	</table>
</body>
</html>