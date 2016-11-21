<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/css/CSS_template.css">
</head>
<body>
	<table style="width: 100%">
		<tr>
	<th><h1>AFSPEELLIJST</h1></th>
	<th><h12>User: ${user.userName}</h12></th>
	<th><a href="logout">log uit</a></th>
		</tr>
	</table>
		<img src="/css/PubSong2.png" alt="PubSong logo" align="right" align="top">
	<c:if test="${mainAfspeellijst.size > 0}">
		<table style="width: 50%">
			<tr>
				<th>Artiest</th>
				<th>Titel</th>
				<th>Votes</th>
			</tr>

			<c:forEach items="${mainAfspeellijst.afspeellijst}" var="n">
				<tr>
					<td>${n.nummer.artiest}</td>
					<td>${n.nummer.titel}</td>
					<td><c:if test="${n.adminVote == true}"> Admin veto! </c:if> <c:if
							test="${n.adminVote == false}">${n.votes}</c:if></td>
					<td>
						<form method="post" action="upvote">
							<input hidden="nummer" name="id" value="${n.id}">
							<input type=submit class="vote-button" value="vote">


						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<h8>Artiest: ${gekozenNummer.artiest}</h8>
	<br> <h8>Titel: ${gekozenNummer.titel}</h8>
	<br>

	<form method="post" action="homeVoegToe">

		<input hidden="nummer" name="id" value="${gekozenNummer.id}">
		<input type="submit" class="voegtoe-button" value="Voeg toe">
	</form>


	<form method="post" action="homeZoek">
		<input type="text" name="zoek"> 
		<input type="submit" class="zoek-button" value="zoek">
	</form>


	<ul>
		<c:forEach items="${alleResultaten}" var="n">
			<li><a href="homeSelectie?id=${n.id}"> ${n.artiest} --
					${n.titel} </a></li>
		</c:forEach>
	</ul>


</body>
</html>