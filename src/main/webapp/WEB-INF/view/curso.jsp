<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/curso.css" />'>
<title>Curso</title>
</head>
<body style="background-image: url('./resources/imagens/imagem_fundo.png')" class="tela_aluno">
	<div class="menu">
		<jsp:include page="menuS.jsp"></jsp:include>
	</div>
	<br />
	<div align="center" class="container">
		<form action="curso" method="post">
			<p class="title"></p>
			<p class="cadastrar">Cadastre o Curso</p>
			<table>

				<tr>
					<td class="aluno" colspan="3">
						<p class="title">Codigo:</p> <input class="cadastro" type="number"
						id="codigo" name="codigo" placeholder=""
						value='<c:out value="${curso.codigo }"></c:out>'> <input
						type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Nome:</p> <input class="cadastro" type="text"
						id="nome" name="nome" placeholder=""
						value='<c:out value="${curso.nome }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Carga Horaria:</p> <input class="cadastro"
						type="text" id="carga_horaria" name="carga_horaria" placeholder=""
						value='<c:out value="${curso.carga_horaria }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Sigla:</p> <input class="cadastro" type="text"
						id="sigla" name="sigla"
						value='<c:out value="${curso.sigla }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Nota Enade:</p> <input class="cadastro"
						type="text" id="nota_enade" name="nota_enade"
						value='<c:out value="${curso.nota_enade }"></c:out>'>
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
		<c:if test="${not empty cursos }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Selecionar</th>
						<th>Codigo</th>
						<th>Nome</th>
						<th>Carga Horaria</th>
						<th>Sigla</th>
						<th>Nota Enade</th>
						<th>Excluir</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${cursos }">
						<tr>
							<td><input type="radio" name="opcao" value="${c.codigo }"
								onclick="editarCurso(this.value)"
								${c.codigo eq codigoEdicao ? 'checked' : ''} /></td>
							<td><c:out value="${c.codigo}" /></td>
							<td><c:out value="${c.nome}" /></td>
							<td><c:out value ="${c.carga_horaria }"/></td>
							<td><c:out value ="${c.sigla }"/></td>
                            <td><c:out value ="${c.nota_enade }"/></td>
							<td style="text-align: center;">
								<button class="btn-excluir"
									onclick="excluirCurso('${c.codigo}')">Excluir</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<script>
				function editarCurso(codigo) {
					window.location.href = 'curso?cmd=alterar&codigo='
							+ codigo;
				}

				function excluirCurso(codigo) {
					if (confirm("Tem certeza que deseja excluir este curso?")) {
						window.location.href = 'curso?cmd=excluir&codigo='
								+ codigo;
					}
				}
			</script>
		</c:if>
	</div>
</body>
</html>