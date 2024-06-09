<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href='<c:url value="./resources/css/grade.css" />'>
<title>Grade</title>
</head>
<body
	style="background-image: url('./resources/imagens/imagem_fundo.png')"
	class="tela_aluno">
	<div class="menu">
		<jsp:include page="menuS.jsp" />
	</div>
	<br />
	<div align="center" class="container">
		<form action="grade" method="post">
			<p class="title">
			<p class="cadastrar">Vincule a Grade</p>
			</p>
			<table>
				<tr>
					<td class="aluno">
						<p class="title">Codigo:</p> <input class="cadastro" type="number"
						id="codigo" name="codigo" placeholder=""
						value='<c:out value="${grade.codigo }"></c:out>'>
					</td>
				<tr>
					<td class="aluno"><label for="curso">Curso:</label></td>
					<td><select class="input_data" id="curso" name="curso">
							<option value="0">Escolha o Curso</option>
							<c:forEach var="c" items="${cursos }">
								<c:if test="${empty grade or c.codigo ne grade.curso.codigo}">
									<option value="${c.codigo }">
										<c:out value="${c.nome }" />
									</option>
								</c:if>
								<c:if test="${c.codigo eq grade.curso.codigo }">
									<option value="${c.codigo }" selected="selected">
										<c:out value="${c.nome }" />
									</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td class="aluno"><label for="disciplina">Disciplina:</label></td>
					<td><select class="input_data" id="disciplina"
						name="disciplina">
							<option value="0">Escolha a Disciplina</option>
							<c:forEach var="d" items="${disciplinas }">
								<c:if
									test="${empty grade or d.codigo ne grade.disciplina.codigo}">
									<option value="${d.codigo }">
										<c:out value="${d.nome }" />
									</option>
								</c:if>
								<c:if test="${d.codigo eq grade.disciplina.codigo }">
									<option value="${d.codigo }" selected="selected">
										<c:out value="${d.nome }" />
									</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>

				<tr class="botoes">
					<td><input type="submit" id="botao" name="botao"
						value="Cadastrar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Alterar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Excluir"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Listar"></td>
				</tr>
			</table>
		</form>
	</div>
	<br />
	<div align="center">
		<c:if test="${not empty erro }">
			<h2>
				<b><c:out value="${erro }" /></b>
			</h2>
		</c:if>
	</div>

	<br />
	<div align="center">
		<c:if test="${not empty saida }">
			<h3>
				<b><c:out value="${saida }" /></b>
			</h3>
		</c:if>
	</div>

	<br />
	<div align="center">
		<c:if test="${not empty grades }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Codigo</th>
						<th>Curso</th>
						<th>Disciplina</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="g" items="${grades }">
						<tr>
							<td><input type="radio" name="opcao" value="${g.codigo}"
								onclick="editarGrade(this.value)"
								${g.codigo eq codigoEdicao ? 'checked' : ''} /></td>
							<td><c:out value="${g.codigo }" /></td>
							<td><c:out value="${g.curso.nome }" /></td>
							<td><c:out value="${g.disciplina.nome }" /></td>
							<td><button onclick="excluirGrade('${g.codigo}')">EXCLUIR</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	<script>
		function editarGrade(codigo) {
			window.location.href = 'grade?cmd=alterar&codigo=' + codigo;
		}

		function excluirGrade(codigo) {
			if (confirm("Tem certeza que deseja excluir está Grade?")) {
				window.location.href = 'grade?cmd=excluir&codigo=' + codigo;
			}
		}
	</script>
</body>
</html>