<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/telefone.css" />'>
<title>Telefone</title>
</head>
<body  style="background-image: url('./resources/imagens/imagem_fundo.png')" class="tela_aluno">
    <div class="menu">
        <jsp:include page="menu.jsp"></jsp:include>
    </div>
    <div align="center" class="container">
        <form action="telefone" method="post">
            <p class="title"><b class="cadastrar">Vincule o Telefone</b></p>
            <table>
             <tr>
                    <td class="aluno">
                        <p class="title">Codigo:</p>
                        <input class="cadastro" type="text" id="codigo" name="codigo" value='<c:out value="${telefone.codigo }"></c:out>'>
                    </td>
                </tr>
               <tr>
                     <td class="aluno">
                       <p class="title">Aluno:</p>
                        <select class="input_data" id="aluno" name="aluno">
                            <option value="">Selecione o Aluno</option>
                            <c:forEach var="aluno" items="${alunos}">
                                <option value="${aluno.cpf}">${aluno.nome}</option>
                            </c:forEach>
                            <c:if
								test="${empty telefone or aluno.cpf ne telefone.aluno.cpf}">
								<option value="${a.cpf}">
									<c:out value="${a}" />
								</option>
							</c:if>
							
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="aluno">
                        <p class="title">Telefone:</p>
                        <input class="cadastro" type="text" id="telefone" name="telefone" value='<c:out value="${telefone.telefone }"></c:out>'>
                    </td>
                </tr>
                <tr class="botoes">
                    <td><input type="submit" name="botao" value="Cadastrar" id= "botao"></td>
                    <td><input type="submit" name="botao" value="Listar"  id= "listar"></td>
                </tr>
            </table>
        </form>
    </div>
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
    <div align="center">
	<c:if test="${not empty telefones}">
		<table class="table_round">
			<thead>
				<tr>
				    <th>Aluno</th>
					<th>Código</th>
					<th>Telefone</th>
					<th>Excluir</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="t" items="${telefones }">
					<tr>
					    <td><c:out value="${t.aluno.nome }" /></td>
						<td><c:out value="${t.codigo }" /></td>
						<td><c:out value="${t.telefone }" /></td>
					 <td><a href="telefone?cmd=excluir&codigo=${t.codigo}">EXCLUIR</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>
<script>
		
		function excluirTelefone(codigo) {
			if (confirm("Tem certeza que deseja excluir este Telefone?")) {
				window.location.href = 'telefone?cmd=excluir&codigo=' + codigo;
			}
		}
	</script>
</body>
</html>