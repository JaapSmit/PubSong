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
	<c:if test="${mainAfspeellijst.size > 0}">
		<ul>
		<c:forEach items="${mainAfspeellijst.afspeellijst}" var="n">
			<li>${n.artiest} -- ${n.titel}</li> 
		</c:forEach>
		</ul>
	</c:if>
	
	Artiest: ${gekozenNummer.artiest} <br>
	Titel: ${gekozenNummer.titel} <br>
		
	<form method="post" action="homeVoegToe">
		
		<input hidden="nummer" name="id" value="${gekozenNummer.id}">
		<input type="submit" value="Voeg toe">
	</form>
	
	<form method="post" action="homeZoek">
		<input type="text" name="zoek">
		<input type="submit" value="zoek">
	</form>
		
	
	<ul>
	<c:forEach items="${alleResultaten}" var="n">
		<li><a href="homeSelectie?id=${n.id}" > ${n.artiest} -- ${n.titel} </a></li> 
	</c:forEach>
	</ul>
	
	

</body>
</html>