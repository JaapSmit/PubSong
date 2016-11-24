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
			<th><h12>User: ${user.userName} -- Votes: <span id="uservotes"></span></h12></th>
			<th><a href="logout">log uit</a></th>
		</tr>
	</table>
	<img src="/css/PubSong2.png" alt="PubSong logo" align="right"
		align="top">
	<c:if test="${mainAfspeellijst.size > 0}">
		<table style="width: 50%; height: 500px;" id="hoofdAfspeellijst">
			
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
			<th><h2 style="display:none"><h8>Artiest: </h8> <span id="artiest"></span> <br> <h8>Titel: </h8> <span id="titel"></span>

					<input type="submit" class="voegtoe-button" value="Voeg toe">
				</h2></th>
		<tr>
			<td >
				<ul>
					<c:forEach items="${alleResultaten}" var="n">
						<li class="toevoegen" id=${n.id}><!-- <a class="voegtoe" href="homeSelectie?id=${n.id}"> ${n.artiest} --
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
			var artiest = $(this).find('.artiest').text();
			var titel = $(this).find('.titel').text();
			$('#artiest').text(artiest);
			$('#titel').text(titel);
			$('h2').show();
			var idVar = $(this).attr('id');
			//console.log(id)
			$(".voegtoe-button").click(function() {
				$.post("homeVoegToe", {id: idVar}, function() {
					Refresh();
					$('h2').hide();
				});
				// refresh van de main afspeellijst
			})
		}
		$(".toevoegen").click(selectieToevoegen);
		
		function Refresh() {
			
			//$('#hoofdAfspeellijst tr').remove();

			
			$.get("refresh", function(data) {
				console.log("refresh");
				$('#hoofdAfspeellijst').empty();
				var headerElement = $('<tr>' +
						'<th>Artiest</th>' +
						'<th>Titel</th>' +
						'<th>Votes</th>' +
						'</tr>'
						);
				$('#hoofdAfspeellijst').append(headerElement);
				for(var i = 0; i < data.length; i++) {
					var votes;
					if (data[i].adminVote == true) {
						votes = "Admin veto!";
					} else {
						votes = data[i].votes;
					} 
					
					var newElement = $('<tr>' + 
							'<td>' + data[i].nummer.artiest + '</td>' + 
							'<td>' + data[i].nummer.titel + '</td>' + 
							'<td>' + votes + '</td>' +
							'<td>' + '<form method="post" action="upvote"><input hidden="nummer" name="id" value=' + 
							data[i].id +  
							'> <input type=submit class="vote-button" value="vote"> </form>' + '</td>' +
							
							'</tr>');
					$('#hoofdAfspeellijst').append(newElement);
				}
			});
			$.get("uservote", function(data) {
				$('#uservotes').text(data);
			});
			
		}
		Refresh();
		myVar = setInterval(Refresh, 5000);
		
	});
	</script>

</body>
</html>