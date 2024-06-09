<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/disciplina.css" />'>
<title>Disciplina</title>
</head>
<body
	style="background-image: url('./resources/imagens/imagem_fundo.png')"
	class="tela_aluno">
	<div class="menu">
		<jsp:include page="menu.jsp" />
	</div>
	<br />
	<div align="center" class="container">
		<form action="disciplina" method="post">
			<p class="title">
			<p class="cadastrar">Cadastre a Disciplina</p>
			</p>

			<table>
				<tr>
					<td class="aluno" colspan="3">
						<p class="title">Codigo:</p> <input class="cadastro" type="text"
						id="codigo" name="codigo" placeholder=""
						value='<c:out value="${disciplina.codigo }"></c:out>'> <input
						type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Nome:</p> <input class="cadastro" type="text"
						id="nome" name="nome" placeholder=""
						value='<c:out value="${disciplina.nome }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Hora Inicio:</p> <input class="cadastro"
						type="time" id="horas_inicio" name="horas_inicio" placeholder=""
						value='<c:out value="${disciplina.horas_inicio }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Duração:</p> <input class="cadastro"
						type="number" id="duracao" name="duracao"
						value='<c:out value="${disciplina.duracao }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Dia Semana:</p> <input class="cadastro"
						type="text" id="dia_semana" name="dia_semana"
						value='<c:out value="${disciplina.dia_semana }"></c:out>'>
					</td>
				</tr>

				<tr>
					<td class="aluno"><label for="professor">Professor:</label></td>
					<td><select class="input_data" id="professor" name="professor">
							<option value="0">Escolha um professor</option>
							<c:forEach var="p" items="${professores }">
								<c:if
									test="${empty disciplina or p.codigo ne disciplina.professor.codigo}">
									<option value="${p.codigo }">
										<c:out value="${p }" />
									</option>
								</c:if>
								<c:if test="${p.codigo eq disciplina.professor.codigo }">
									<option value="${p.codigo }" selected="selected">
										<c:out value="${p }" />
									</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>

			</table>

			<table>
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
		<c:if test="${not empty disciplinas }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Selecionar</th>
						<th>Codigo</th>
						<th>Nome</th>
						<th>Hora Inicio</th>
						<th>Duração</th>
						<th>Dia da Semana</th>
						<th>Professor</th>
						<th>Excluir</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="d" items="${disciplinas }">
						<tr>
							<td><input type="radio" name="opcao" value="${d.codigo}"
								onclick="editarDisciplina(this.value)"
								${d.codigo eq codigoEdicao ? 'checked' : ''} /></td>
							<td><c:out value="${d.codigo }" /></td>
							<td><c:out value="${d.nome }" /></td>
							<td><c:out value="${d.horas_inicio }" /></td>
							<td><c:out value="${d.duracao }" /></td>
							<td><c:out value="${d.dia_semana }" /></td>
							<td><c:out value="${d.professor.nome }" /></td>
							<td><button onclick="excluirDisciplina('${d.codigo}')">EXCLUIR</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	<script>
		function editarDisciplina(codigo) {
			window.location.href = 'disciplina?cmd=alterar&codigo=' + codigo;
		}

		function excluirDisciplina(codigo) {
			if (confirm("Tem certeza que deseja excluir esta Disciplina?")) {
				window.location.href = 'disciplina?cmd=excluir&codigo=' + codigo;
			}
		}
	</script>
</body>
</html>