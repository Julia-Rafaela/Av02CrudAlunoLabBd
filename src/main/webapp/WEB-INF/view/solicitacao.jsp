<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href='<c:url value="./resources/css/solicitacao.css" />'>
    <title>Solicitações de Dispensa</title>
    <script>
        function aceitarSolicitacao(codigo, aluno) {
            if (confirm("Tem certeza de que deseja aceitar esta solicitação?")) {
                var form = document.createElement("form");
                form.setAttribute("method", "post");
                form.setAttribute("action", "aceitarSolicitacao");

                var hiddenCodigo = document.createElement("input");
                hiddenCodigo.setAttribute("type", "hidden");
                hiddenCodigo.setAttribute("name", "codigo");
                hiddenCodigo.setAttribute("value", codigo);

                var hiddenAluno = document.createElement("input");
                hiddenAluno.setAttribute("type", "hidden");
                hiddenAluno.setAttribute("name", "aluno");
                hiddenAluno.setAttribute("value", aluno);

                form.appendChild(hiddenCodigo);
                form.appendChild(hiddenAluno);

                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</head>
<body style="background-image: url('./resources/imagens/imagem_fundo.png')" class="tela_aluno">
    <div class="menu">
        <jsp:include page="menuS.jsp" />
    </div>
    <br />
    <form>
        <table>
            <tr class="botoes2">
                <td><input type="submit" id="botao" name="botao" value="Solicitações"></td>
            </tr>
            
        </table>
    </form>
    <div align="center">
        <c:if test="${not empty erro}">
            <h2><b><c:out value="${erro}" /></b></h2>
        </c:if>
    </div>
    <div align="center">
        <c:if test="${not empty saida}">
            <h3><b><c:out value="${saida}" /></b></h3>
        </c:if>
    </div>
    <div align="center">
        <c:if test="${not empty dispensas}">
            <table class="table_round">
                <thead>
                    <tr>
                        <th>Código Solicitação</th>
                        <th>Aluno</th>
                        <th>Disciplina</th>
                        <th>Data Solicitação</th>
                        <th>Instituição</th>
                        <th>Ações</th>
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
                            <td><button onclick="aceitarSolicitacao(${ds.codigo}, '${ds.aluno.cpf}')" class="botao-aceitar">Aceitar</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    <div align="center">
        <c:if test="${not empty matriculas}">
            <table class="table_round">
                <thead>
                    <tr>
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
</body>
</html>