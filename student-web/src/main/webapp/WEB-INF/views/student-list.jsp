<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.s4m.jee.studentmanagement.domain.Student"%>
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
	<h2>Liste de tous les élèves</h2>
	<div class="table-wrap"><table class="student-table">
		<thead>
			<tr>
				<th>Numero</th>
				<th>Nom</th>
				<th>Adresse</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${allelevs}" var="ele">
				<tr>
					<td><c:out value="${ele.number}" /></td>
					<td><c:out value="${ele.name}" /></td>
					<td><c:out value="${ele.address}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table></div>
</main>
</body>
</html>