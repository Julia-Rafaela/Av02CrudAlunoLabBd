<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href='<c:url value="./resources/css/nota.css" />'>
<title>Notas</title>
</head>
<body
	style="background-image: url('./resources/imagens/imagem_fundo.png')"
	class="tela_aluno">
	<div class="menu">
		<jsp:include page="menuP.jsp" />
	</div>
	<br />
	<div align="center" class="container">
		<form action="nota" method="post" class="matricula">
			<p class="title">
			<p class="cadastrar">Cadastre as Notas</p>
			</p>
			<table>
				<tr>
					<td class="aluno"><label for="aluno">Aluno:</label></td>
					<td><select class="input_data" id="aluno" name="aluno">
							<option value="0">Escolha o Aluno</option>
							<c:forEach var="a" items="${alunos }">
								<c:if test="${empty nota or a.cpf ne nota.aluno.cpf}">
									<option value="${a.cpf }">
										<c:out value="${a.nome }" />
									</option>
								</c:if>
								<c:if test="${a.cpf eq nota.aluno.cpf }">
									<option value="${a.cpf }" selected="selected">
										<c:out value="${a.nome }" />
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
									test="${empty nota or d.codigo ne nota.disciplina.codigo}">
									<option value="${d.codigo }">
										<c:out value="${d.nome }" />
									</option>
								</c:if>
								<c:if test="${d.codigo eq nota.disciplina.codigo }">
									<option value="${d.codigo }" selected="selected">
										<c:out value="${d.nome }" />
									</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr class="espaco">
					<td class="aluno">
						<p class="title">Nota P1:</p> 
						<input class="cadastro" type="text" id="nota1" 
						name="nota1" value='<c:out value="${nota.nota1 }"></c:out>'>
						
					</td>
				</tr>
				<tr class="espaco">
					<td class="aluno">
						<p class="title">Nota P2:</p> 
						<input class="cadastro" type="text" id="nota2" 
						name="nota2" value='<c:out value="${nota.nota2 }"></c:out>'>
						
					</td>
				</tr>
				<tr class="espaco">
					<td class="aluno">
						<p class="title">Nota Recuperação:</p> 
						<input class="cadastro" type="text" id="nota_recuperacao" 
						name="nota_recuperacao" value='<c:out value="${nota.nota_recuperacao }"></c:out>'>
						
					</td>
				</tr>

				<tr class="botoes2">
					<td><input type="submit" id="botao" name="botao"
						value="Cadastrar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Alterar"></td>
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
		<c:if test="${not empty notas }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Codigo</th>
						<th>Aluno</th>
						<th>Nota P1</th>
						<th>Nota P2</th>
						<th>Nota Recuperação</th>
						<th>Media</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="n" items="${notas}">
						<tr>
							<td><c:out value="${n.codigo}" /></td>
							<td><c:out value="${n.aluno.nome}" /></td>
							<td><c:out value="${n.nota1}" /></td>
							<td><c:out value="${n.nota2}" /></td>
							<td><c:out value="${n.nota_recuperacao}" /></td>
							<td><c:out value="${n.media}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	<script>
    function editarNotas(codigo) {
        window.location.href = 'nota?cmd=alterar&codigo=' + codigo;
    }
	</script>
</body>
</html>