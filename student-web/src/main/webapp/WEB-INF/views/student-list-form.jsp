<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
<meta charset="UTF-8">
<title>All Eleve</title>
</head>
<body>
<%@ include file="/WEB-INF/menu.jsp" %>
<main class="page-content">
	<div class="panel">
		<h2 class="pgtitre">La liste de tous les élèves</h2>
		<form name="id" class="form"
			action="${pageContext.request.contextPath}/students" method="POST">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />
			<input type="submit" value="Valider" class="submit" />
		</form>
	</div>
</main>
</body>
</html>