<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href='<c:url value="./resources/css/solicitacaoDispensa.css" />'>
<title>Solicitar Dispensa</title>
</head>
<body
	style="background-image: url('./resources/imagens/imagem_fundo.png')"
	class="tela_aluno">
	<div class="menu">
		<jsp:include page="menu.jsp" />
	</div>
	<br />
	<div align="center" class="container">
		<form action="solicitarDispensa" method="post" class="matricula">
			<p class="title">
			<p class="cadastrar">Solicite a dispensa da materia desejada</p>
			</p>
			<table>
				<tr>
					<td class="aluno">
						<p class="title">Digite seu CPF:</p> <input class="cadastro" type="text"
						id="cpf" name="cpf" placeholder=""
						value='<c:out value="${dispensa.aluno.cpf }"></c:out>'>
					</td>
					</tr>
				<tr>
					<td class="aluno"><label for="disciplina">Disciplina:</label></td>
					<td><select class="input_data" id="disciplina"
						name="disciplina">
							<option value="0">Escolha a Disciplina</option>
							<c:forEach var="d" items="${disciplinas }">
								<c:if
									test="${empty dispensa or d.codigo ne dispensa.disciplina.codigo}">
									<option value="${d.codigo }">
										<c:out value="${d.nome }" />
									</option>
								</c:if>
								<c:if test="${d.codigo eq dispensa.disciplina.codigo }">
									<option value="${d.codigo }" selected="selected">
										<c:out value="${d.nome }" />
									</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
					<tr>
					<td class="aluno">
						<p class="title">Intituição de ensino:</p> <input class="cadastro" type="text"
						id="instituicao" name="instituicao" placeholder=""
						value='<c:out value="${dispensa.instituicao }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno">
						<p class="title">Data da solicitação:</p> <input class="cadastro"
						type="date" id="data_s" name="data_s"
						value='<c:out value="${dispensa.data_s }"></c:out>'>
					</td>
				</tr>

				<tr class="botoes2">
					<td><input type="submit" id="botao" name="botao"
						value="Solicitar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Listar"></td>
						<input type="hidden" name="listar" value="true">
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
		<c:if test="${not empty dispensas }">
			<table class="table_round">
				<thead>
					<tr>
					    <th>Codigo Solicitação</th>
						<th>Aluno</th>
						<th>Disciplina</th>
						<th>Data Solicitação</th>
						<th>Instituição</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="ds" items="${dispensas}">
						<tr>
						    <td><c:out value="${ds.codigo}" /></td>
							<td><c:out value="${ds.aluno.cpf}" /></td>
							<td><c:out value="${ds.disciplina.nome}" /></td>
							<td><c:out value="${ds.data_s}" /></td>
							<td><c:out value="${ds.instituicao}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>