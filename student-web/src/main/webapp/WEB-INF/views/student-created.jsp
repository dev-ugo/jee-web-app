<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
<meta charset="UTF-8">
<title>Confirmation ajout Eleve</title>
</head>
<body>
<%@ include file="/WEB-INF/menu.jsp" %>
<main class="page-content">
<div class="panel result-panel"><p class="eyebrow">SCOLARITÉ</p><h1>Confirmation</h1>

<c:out value="${txtconfirmationadd}" />

</div>
</main>
</body>
</html>