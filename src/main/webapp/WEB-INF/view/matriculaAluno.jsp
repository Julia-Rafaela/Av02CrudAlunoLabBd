<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/historico.css" />'>
    <title>Matricula Aluno</title>
</head>
<body style="background-image: url('<c:url value="/resources/imagens/imagem_fundo.png" />')" class="tela_aluno">
    <div class="menu">
        <jsp:include page="menuS.jsp" />
    </div>
    <br />
    <div align="center" class="container">
        <form action="matriculaAluno" method="post">
            <p class="title"></p>
            <p class="cadastrar">Consultar Matriculas do Aluno</p>
            <table>
                <tr>
                    <td class="aluno"><label for="matriculaAluno">Aluno:</label></td>
                    <td>
                        <select class="input_data" id="matriculaAluno" name="aluno">
                            <option value="0">Escolha o Aluno</option>
                            <c:forEach var="a" items="${alunos}">
                                <c:if test="${empty aluno or a.cpf ne matriculaAluno.aluno.cpf}">
                                    <option value="${a.cpf}">
                                        <c:out value="${a.nome}" />
                                    </option>
                                </c:if>
                                <c:if test="${a.cpf eq matriculaAluno.aluno.cpf}">
                                    <option value="${a.cpf}" selected="selected">
                                        <c:out value="${a.nome}" />
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr class="botoes">
                    <td><input type="submit" id="botao" name="botao" value="Listar"></td>
                </tr>
            </table>
        </form>
    </div>
    <br />
    <div align="center">
        <c:if test="${not empty erro}">
            <h2><b><c:out value="${erro}" /></b></h2>
        </c:if>
    </div>
    <div class="mensagem" align="center">
        <c:if test="${not empty saida}">
            <h3><b><c:out value="${saida}" /></b></h3>
        </c:if>
    </div>
    <br />
    <div align="center">
        <c:if test="${not empty historicosM}">
            <table class="table_round">
                <thead>
                    <tr>
                        <th>Codigo Disciplina</th>
                        <th>Nome Disciplina</th>
                        <th>Nome Professor</th>
                        <th>Nota Final</th>
                        <th>Quantidade Falta</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="h" items="${historicosM}">
                        <tr>
                            <td><c:out value="${h.disciplina.codigo}" /></td>
                            <td><c:out value="${h.disciplina.nome}" /></td>
                            <td><c:out value="${h.professor.nome}" /></td>
                            <td><c:out value="${h.nota.media}" /></td>
                            <td><c:out value="${h.chamada.falta}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>