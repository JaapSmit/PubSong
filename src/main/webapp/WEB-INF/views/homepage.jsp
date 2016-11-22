<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/css/CSS_template.css">
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="voegtoe.js"></script>
</head>
<body>
	<table style="width: 100%">
		<tr>
			<th><h1>AFSPEELLIJST</h1></th>
			<th><h12>User: ${user.userName}</h12></th>
			<th><a href="logout">log uit</a></th>
		</tr>
	</table>
	<img src="/css/PubSong2.png" alt="PubSong logo" align="right"
		align="top">
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
							<input hidden="nummer" name="id" value="${n.id}"> <input
								type=submit class="vote-button" value="vote">


						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
	</c:if>
	<table style="width: 60%">
		<tr>
			<th><h8>Zoek in de database:</h8> <br> <br>
				<form method="post" action="homeZoek">
					<input type="text" name="zoek"> <input type="submit"
						class="zoek-button" value="zoek">
				</form></th>
			<th><h2 style="display:none"><h8>Artiest: </h8> <span id="artiest"></span> <br> <h8>Titel: </h8>
				${gekozenNummer.titel}<br>

				<form method="post" action="homeVoegToe">

					<input hidden="nummer" name="id" value="${gekozenNummer.id}">
					<input type="submit" class="voegtoe-button" value="Voeg toe">
				</form></h2></th>
		<tr>
			<td >
				<ul>
					<c:forEach items="${alleResultaten}" var="n">
						<li class="toevoegen"><!-- <a class="voegtoe" href="homeSelectie?id=${n.id}"> ${n.artiest} --
								${n.titel} </a> -->
								<span class="artiest">${n.artiest }</span> -- <span class="titel">${n.titel}</span>
								</li>
					</c:forEach>
				</ul>
			</td>
		</tr>


	</table>
	
	<script>
	$(document).ready(function() {
		function selectieToevoegen() {
			
			console.log(".toevoegen geklikt op", $(this).find('.artiest').text());
			var artiest = $(this).find('.artiest').text();
			var titel = $(this).find('.titel').text();
			$('#artiest').text(artiest);
			$('h2').show();
		}
		$(".toevoegen").click(selectieToevoegen);
	});
	</script>

</body>
</html>