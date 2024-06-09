<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/matricula.css" />'>
    <title>Matrícula</title>
</head>
<body style="background-image: url('./resources/imagens/imagem_fundo.png')" class="tela_aluno">
<div class="menu">
    <jsp:include page="menuMatricula.jsp" />
</div>
<br />
<div align="center" class="container">
    <form action="matricula" method="post" class="matricula">
        <p class="title">
        <p class="cadastrar">Realizar Matrícula</p>
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
            <tr>
                <td class="aluno"><label for="disciplina">Disciplina:</label></td>
                <td>
                    <select class="input_data" id="disciplina" name="disciplina">
                        <option value="0">Escolha a Disciplina</option>
                        <c:forEach var="d" items="${disciplinas}">
                            <c:choose>
                                <c:when test="${empty matricula or d.codigo ne matricula.disciplina.codigo}">
                                    <option value="${d.codigo}">
                                        <c:out value="${d.nome}" />
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${d.codigo}" selected="selected">
                                        <c:out value="${d.nome}" />
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="aluno">
                    <p class="title">Data da Matrícula:</p> <input class="cadastro" type="date" id="data_m" name="data_m"
                                                                 value='<c:out value="${matricula.data_m }"></c:out>'>
                </td>
            </tr>
            <tr class="botoes2">
                <td><input type="submit" id="botao" name="botao" value="Cadastrar"></td>
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

<br />
<div align="center">
    <c:if test="${not empty saida}">
        <h3><b><c:out value="${saida}" /></b></h3>
    </c:if>
</div>

<br />
<div align="center">
    <c:if test="${not empty matriculas}">
        <table class="table_round">
            <thead>
            <tr>
                <th>Selecionar</th>
                <th>Código</th>
                <th>Aluno</th>
                <th>Disciplina</th>
                <th>Data da Matrícula</th>
                <th>Status</th>
                <th>Nota Final</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="m" items="${matriculas}">
                <tr>
                    <td><input type="radio" name="opcao" value="${m.codigo}" onclick="editarMatricula(this.value)"
                               ${m.codigo eq codigoEdicao ? 'checked' : ''} /></td>
                    <td><c:out value="${m.codigo}" /></td>
                    <td><c:out value="${m.aluno.nome}" /></td>
                    <td><c:out value="${m.disciplina.nome}" /></td>
                    <td><c:out value="${m.data_m}" /></td>
                    <td><c:out value="${m.status_m}" /></td>
                    <td><c:out value="${m.nota.media}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
<script>
    function editarMatricula(codigo) {
        window.location.href = 'matricula?cmd=alterar&codigo=' + codigo;
    }
</script>
</body>
</html>