<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/professor.css" />'>
<title>Professor</title>
</head>
<body style="background-image: url('./resources/imagens/imagem_fundo.png')" class="tela_aluno">
	<div class="menu">
		<jsp:include page="menuS.jsp"></jsp:include>
	</div>
	<br />
	<div align="center" class="container">
		<form action="professor" method="post">
			<p class="title"></p>
			<p class="cadastrar">Cadastre o Professor</p>
			<table>

				<tr>
					<td class="aluno" colspan="3">
						<p class="title">Codigo:</p> <input class="cadastro" type="number"
						id="codigo" name="codigo" placeholder=""
						value='<c:out value="${professor.codigo }"></c:out>'> <input
						type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Nome:</p> <input class="cadastro" type="text"
						id="nome" name="nome" placeholder=""
						value='<c:out value="${professor.nome }"></c:out>'>
					</td>
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
			<H2>
				<b><c:out value="${erro }" /></b>
			</H2>
		</c:if>
	</div>
	<div class="mensagem" align="center">
		<c:if test="${not empty saida }">
			<H3>
				<b><c:out value="${saida }" /></b>
			</H3>
		</c:if>
	</div>
	<br />
	<div align="center">
		<c:if test="${not empty professores }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Selecionar</th>
						<th>Codigo</th>
						<th>Nome</th>
						<th>Excluir</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${professores }">
						<tr>
							<td><input type="radio" name="opcao" value="${p.codigo }"
								onclick="editarProfessor(this.value)"
								${p.codigo eq codigoEdicao ? 'checked' : ''} /></td>
							<td><c:out value="${p.codigo}" /></td>
							<td><c:out value="${p.nome}" /></td>
							<td style="text-align: center;">
								<button class="btn-excluir"
									onclick="excluirProfessor('${p.codigo}')">Excluir</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<script>
				function editarProfessor(codigo) {
					window.location.href = 'professor?cmd=alterar&codigo='
							+ codigo;
				}

				function excluirProfessor(codigo) {
					if (confirm("Tem certeza que deseja excluir este professor?")) {
						window.location.href = 'professor?cmd=excluir&codigo='
								+ codigo;
					}
				}
			</script>
		</c:if>
	</div>