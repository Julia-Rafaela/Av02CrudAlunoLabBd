<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta charset="UTF-8">
<title>Chamada</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value="./resources/css/chamada.css" />'>
<script>
	function realizarChamada() {
		// Obter todos os campos de falta
		var camposFalta = document.querySelectorAll('input[name^="falta"]');

		// Iterar sobre todos os campos de falta
		camposFalta.forEach(function(campo) {
			// Verificar se o campo está vazio
			if (campo.value === '') {
				// Se estiver vazio, definir o valor como zero
				campo.value = '0';
			}
		});

		return true; // Permitir a submissão do formulário
	}
</script>
</head>
<body
	style="background-image: url('./resources/imagens/imagem_fundo.png')"
	class="tela_aluno">
	<div class="menu">
		<jsp:include page="menuP.jsp" />
	</div>
	<br />
	<div align="center" class="container">
		<form action="chamada" method="post" class="matricula"
			onsubmit="return realizarChamada()">
			<p class="title">
			<p class="cadastrar">Realize a Chamada</p>
			</p>
			<table>
					<tr>
					<td class="aluno" colspan="4">
						<p class="title">Codigo:</p> <input class="cadastro" type="text"
						id="codigo" name="codigo" placeholder=""
						value='<c:out value="${chamada.codigo }"></c:out>'>
						<input type="submit" id="buscar" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Data:</p> <input class="cadastro" type="date"
						id="data" name="data" placeholder=""
						value='<c:out value="${chamada.data }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno"><label for="disciplina">Disciplina:</label></td>
					<td><select class="input_data" id="disciplina"
						name="disciplina">
							<option value="0">Escolha a Disciplina</option>
							<c:forEach var="d" items="${disciplinas}">
								<option value="${d.codigo}"
									<c:if test="${d.codigo eq chamada.disciplina.codigo}">selected</c:if>>
									<c:out value="${d.nome}" />
								</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr class="botoes2">
					<td><input type="submit" id="botao" name="botao"
						value="Cadastrar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Realizar Chamada"></td>
						<td><input type="submit" id="botao" name="botao"
						value="Alterar"></td>
						
				</tr>
			</table>
			<c:if test="${not empty chamadas}">
				<table class="table_round">
					<thead>
						<tr>
							<th>Disciplina</th>
							<th>Aluno</th>
							<th>CPF</th>
							<th>Faltas</th>
						</tr>
					</thead>
					<tbody>
							<c:forEach var="cm" items="${chamadas}" varStatus="status">
    <tr>
        <td><c:out value="${cm.disciplina.nome}" /></td>
        <td><input type="text"
                   id="nomeAluno${status.index}" name="nomeAluno${status.index}" placeholder=""
                   value='<c:out value="${cm.aluno.cpf }"></c:out>'></td>
        <td><input type="text"
                   id="aluno${status.index}" name="aluno${status.index}" placeholder=""
                   value='<c:out value="${cm.aluno.nome }"></c:out>'></td>
        <td><input type="number"
                   id="falta${status.index}" name="falta${status.index}" placeholder=""
                   value='<c:out value="${cm.falta }"></c:out>'></td>
    </tr>
</c:forEach>
							
					</tbody>

				</table>
			</c:if>
			<input type="hidden" id="falta" name="falta">
		</form>
	</div>
	<br />
	<div align="center">
		<c:if test="${not empty erro}">
			<h2>
				<b><c:out value="${erro}" /></b>
			</h2>
		</c:if>
	</div>
	<br />
	<div align="center">
		<c:if test="${not empty saida}">
			<h3>
				<b><c:out value="${saida}" /></b>
			</h3>
		</c:if>
	</div>
</body>
</html>