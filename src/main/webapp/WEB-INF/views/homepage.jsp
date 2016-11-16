<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PubSong</title>
</head>
<body>
	<h1>AFSPEELLIJST</h1>
	
	<ul>
	<c:forEach items="${alleResultaten}" var="n">
		<li>${n.artiest} -- ${n.titel}</li>
	</c:forEach>
	</ul>
	
	
	<form method="post" action="homeZoek">
	<input type="text" name="zoek">
	<input type="submit" value="zoek">
	
	</form>
	<form method="post" action="homeVoegToe">
	<input type="submit" value="Voeg toe">
	
	</form>
</body>
</html>