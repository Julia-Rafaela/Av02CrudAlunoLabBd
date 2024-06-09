<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta charset="UTF-8">
<title>Consultar Chamadas</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value="./resources/css/consulta.css" />'>
</head>
<body
	style="background-image: url('./resources/imagens/imagem_fundo.png')"
	class="tela_aluno">
	<div class="menu">
		<jsp:include page="menuChamada.jsp" />
	</div>
	<br />
	<div align="center" class="container">
		<form action="consulta" method="post">
			<p class="title">
			<p class="cadastrar">Consulte as Chamadas</p>
			</p>
			<table>
			<tr>
                <td class="aluno"><label for="aluno">Aluno:</label></td>
                <td>
                    <select class="input_data" id="aluno" name="aluno">
                        <option value="0">Escolha o Aluno</option>
                        <c:forEach var="a" items="${alunos}">
                            <c:choose>
                                <c:when test="${empty matricula or a.cpf ne matricula.aluno.cpf}">
                                    <option value="${a.cpf}">
                                        <c:out value="${a.nome}" />
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${a.cpf}" selected="selected">
                                        <c:out value="${a.nome}" />
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
				<tr class="botoes3">
					<td><input type="submit" id="botao" name="botao"
						value="Listar"></td>
				</tr>
			</table>
			<c:if test="${not empty consultas}">
				<table class="table_round">
					<thead>
						<tr>
							<th>Codigo</th>
							<th>Disciplina</th>
							<th>Aluno</th>
							<th>CPF</th>
							<th>Faltas</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="cc" items="${consultas}" varStatus="status">
							<tr>
								<td><c:out value="${cc.chamada.codigo}" /></td>
								<td><c:out value="${cc.disciplina.nome}" /></td>
								<td><input type="text" id="nomeAluno${status.index}"
									name="nomeAluno${status.index}" placeholder=""
									value='<c:out value="${cc.aluno.cpf }"></c:out>'></td>
								<td><input type="text" id="aluno${status.index}"
									name="aluno${status.index}" placeholder=""
									value='<c:out value="${cc.aluno.nome }"></c:out>'></td>
								<td><input type="number" id="falta${status.index}"
									name="falta${status.index}" placeholder=""
									value='<c:out value="${cc.chamada.falta }"></c:out>'></td>
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