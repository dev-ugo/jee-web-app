<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
<meta charset="UTF-8">
<title>Search Eleve</title>
</head>
<body>
<%@ include file="/WEB-INF/menu.jsp" %>
<main class="page-content">
	<div class="panel">
		<h1 class="pgtitre">Rechercher un élève</h1>
		<form name="id" class="form"
			action="${pageContext.request.contextPath}/students/search" method="POST">
			<table>
				<tr>
					<td><label for="numelev">Numéro d’élève</label></td>
					<td><input type="text" name="numelev" id="numelev" /></td>

					<td width="120px"><input type="submit" value="Valider"
						class="submit" /></td>
			</table>
		</form>
		<br>
	</div>
</main>
</body>
</html>