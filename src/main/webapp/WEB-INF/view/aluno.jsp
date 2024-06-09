<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/aluno.css" />'>
<title>Aluno</title>
</head>
<body style="background-image: url('./resources/imagens/imagem_fundo.png')" class="tela_aluno">
	<div class="menu">
		<jsp:include page="menuS.jsp" />
	</div>
	<br />
	<div align="center" class="container">
		<form action="aluno" method="post">
			<p class="title">
			<p class="cadastrar">Cadastre o Aluno</p>
			</p>

			<table>
				<tr>
					<td class="aluno" colspan="3">
						<p class="title">CPF:</p> <input class="cadastro" type="text"
						id="cpf" name="cpf" placeholder=""
						value='<c:out value="${aluno.cpf }"></c:out>'> <input
						type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Nome:</p> <input class="cadastro" type="text"
						id="nome" name="nome" placeholder=""
						value='<c:out value="${aluno.nome }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Nome Social:</p> <input class="cadastro"
						type="text" id="nome_social" name="nome_social" placeholder=""
						value='<c:out value="${aluno.nome_social }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Data de Nascimento:</p> <input class="cadastro"
						type="date" id="data_nascimento" name="data_nascimento"
						value='<c:out value="${aluno.data_nascimento }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Email Pessoal:</p> <input class="cadastro"
						type="text" id="email_pessoal" name="email_pessoal"
						value='<c:out value="${aluno.email_pessoal }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Email Corporativo:</p> <input class="cadastro"
						type="text" id="email_corporativo" name="email_corporativo"
						value='<c:out value="${aluno.email_corporativo }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Data de Conclusão do 2° Grau:</p> <input
						class="cadastro" type="date" id="conclusao_segundo_grau"
						name="conclusao_segundo_grau"
						value='<c:out value="${aluno.conclusao_segundo_grau }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Instituição:</p> <input class="cadastro"
						type="text" id="instituicao_conclusao"
						name="instituicao_conclusao"
						value='<c:out value="${aluno.instituicao_conclusao }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Pontuação Vestibular:</p> <input class="cadastro"
						type="text" id="pontuacao_vestibular" name="pontuacao_vestibular"
						value='<c:out value="${aluno.pontuacao_vestibular }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Posição Vestibular:</p> <input class="cadastro"
						type="number" id="posicao_vestibular" name="posicao_vestibular"
						value='<c:out value="${aluno.posicao_vestibular }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Ano de Ingresso:</p> <input class="cadastro"
						type="text" id="ano_ingresso" name="ano_ingresso"
						value='<c:out value="${aluno.ano_ingresso }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Semestre de Ingresso:</p> <input class="cadastro"
						type="number" id="semestre_ingresso" name="semestre_ingresso"
						value='<c:out value="${aluno.semestre_ingresso }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Semestre Limite de Graduação:</p> <input
						class="cadastro" type="number" id="semestre_limite_graduacao"
						name="semestre_limite_graduacao"
						value='<c:out value="${aluno.semestre_limite_graduacao }"></c:out>'>
					</td>
				</tr>

				<tr>
					<td class="aluno"><label for="curso">Curso:</label></td>
					<td><select class="input_data" id="curso" name="curso">
							<option value="0">Escolha o Curso</option>
							<c:forEach var="c" items="${cursos }">
								<c:if
									test="${empty aluno or c.codigo ne aluno.curso.codigo}">
									<option value="${c.codigo }">
										<c:out value="${c }" />
									</option>
								</c:if>
								<c:if test="${c.codigo eq aluno.curso.codigo }">
									<option value="${c.codigo }" selected="selected">
										<c:out value="${c }" />
									</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>

			</table>

			<table>
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
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">RA:</p> <input class="calculado" type="number"
						id="ra" name="ra" placeholder=""
						value='<c:out value="${aluno.ra }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td class="aluno" colspan="4">
						<p class="title">Ano Limite de Graduação:</p> <input
						class="calculado" type="number" id="ano_limite_graduacao"
						name="ano_limite_graduacao"
						value='<c:out value="${aluno.ano_limite_graduacao }"></c:out>'>
					</td>
				</tr>
			</table>
			<p class="aviso">Cadastre seu Telefone logo após</p>
			<div class="link_telefone">
				<li class="telefone"><a href="telefone">Telefone</a></li>
			</div>
			<p class="aviso">Busque por seu CPF e obtenha seu RA e o Ano
				limite de Graduação</p>
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
		<c:if test="${not empty alunos }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Selecionar</th>
						<th>CPF</th>
						<th>RA</th>
						<th>Nome</th>
						<th>Nome Social</th>
						<th>Nascimento</th>
						<th>Email Pessoal</th>
						<th>Email Corporativo</th>
						<th>Data Conclusão 2°Grau</th>
						<th>Instituição de Conclusão</th>
						<th>Pontuação Vestibular</th>
						<th>Posição Vestibular</th>
						<th>Ano Ingresso</th>
						<th>Ano Limite</th>
						<th>Semestre Ingresso</th>
						<th>Semestre Limite</th>
						<th>Curso</th>
						<th>Excluir</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="a" items="${alunos }">
						<tr>
							<td><input type="radio" name="opcao" value="${a.cpf}"
								onclick="editarAluno(this.value)"
								${a.cpf eq codigoEdicao ? 'checked' : ''} /></td>
							<td><c:out value="${a.cpf }" /></td>
							<td><c:out value="${a.ra }" /></td>
							<td><c:out value="${a.nome }" /></td>
							<td><c:out value="${a.nome_social }" /></td>
							<td><c:out value="${a.data_nascimento }" /></td>
							<td><c:out value="${a.email_pessoal }" /></td>
							<td><c:out value="${a.email_corporativo }" /></td>
							<td><c:out value="${a.conclusao_segundo_grau }" /></td>
							<td><c:out value="${a.instituicao_conclusao }" /></td>
							<td><c:out value="${a.pontuacao_vestibular }" /></td>
							<td><c:out value="${a.posicao_vestibular }" /></td>
							<td><c:out value="${a.ano_ingresso }" /></td>
							<td><c:out value="${a.ano_limite_graduacao }" /></td>
							<td><c:out value="${a.semestre_ingresso }" /></td>
							<td><c:out value="${a.semestre_limite_graduacao }" /></td>
							<td><c:out value="${a.curso.nome }" /></td>
							<td><button onclick="excluirAluno('${a.cpf}')">EXCLUIR</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	<script>
		function editarAluno(cpf) {
			window.location.href = 'aluno?cmd=alterar&cpf=' + cpf;
		}

		function excluirAluno(cpf) {
			if (confirm("Tem certeza que deseja excluir este Aluno?")) {
				window.location.href = 'aluno?cmd=excluir&cpf=' + cpf;
			}
		}
	</script>
</body>
</html>