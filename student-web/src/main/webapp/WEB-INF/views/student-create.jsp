<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
<meta charset="UTF-8">
<title>Ajout Eleve</title>
</head>
<body>
<%@ include file="/WEB-INF/menu.jsp" %>
<main class="page-content">
	<div class="panel">
		<h1 class="pgtitre">Ajouter un élève</h1>
		<form name="id" class="form"
			action="${pageContext.request.contextPath}/students/new" method="POST">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />
			<table>
				<tr>
					<td><label for="numelev">Numéro d’élève</label></td>
					<td><input type="text" name="numelev" id="numelev" /></td>
				</tr>
				<tr>
					<td><label for="nomelev">Nom de l’élève</label></td>
					<td><input type="text" name="nomelev" id="nomelev" /></td>
				</tr>
				<tr>
					<td><label for="agelev">Âge</label></td>
					<td><input type="text" name="agelev" id="agelev" /></td>
				</tr>
				<tr>
					<td><label for="adresselev">Adresse</label></td>
					<td><input type="text" name="adresselev" id="adresselev" /></td>
				</tr>
				<tr>
					<td width="120px"><input type="submit" value="Valider"
						class="submit" /></td>
				</tr>
			</table>
			<c:out value="${txterro}" />
		</form>
	</div>
</main>
</body>
</html>