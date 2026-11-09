<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html lang="fr">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
<meta charset="UTF-8">
<title>École · Accueil</title>
</head>
<body>
<%@ include file="/WEB-INF/menu.jsp" %>
<main class="page-content">
  <section class="welcome">
    <div class="welcome-copy"><p class="eyebrow">ESPACE SCOLARITÉ</p><h1>La vie scolaire,<br>en toute simplicité.</h1><p>Retrouvez les élèves de votre établissement et gérez leurs informations dans un espace dédié.</p></div>
    <img src="${pageContext.request.contextPath}/resources/campus.jpg" alt="Campus de l’établissement" class="campus-image">
  </section>
  <div class="section-heading"><p class="eyebrow">AU QUOTIDIEN</p><h2>Votre espace de gestion</h2></div>
  <div class="action-grid">
    <a class="action-card" href="${pageContext.request.contextPath}/students/new"><span class="card-number">01</span><h3>Ajouter un élève</h3><p>Renseignez les informations d’un nouvel élève.</p><span class="card-link">Ouvrir le formulaire <span aria-hidden="true">↗</span></span></a>
    <a class="action-card" href="${pageContext.request.contextPath}/students"><span class="card-number">02</span><h3>Tous les élèves</h3><p>Consultez la liste des élèves de l’établissement.</p><span class="card-link">Consulter la liste <span aria-hidden="true">↗</span></span></a>
    <a class="action-card" href="${pageContext.request.contextPath}/students/search"><span class="card-number">03</span><h3>Rechercher un élève</h3><p>Retrouvez un dossier à partir du numéro d’élève.</p><span class="card-link">Lancer une recherche <span aria-hidden="true">↗</span></span></a>
  </div>
</main>
</body>
</html>