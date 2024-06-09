<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tela Secretaria</title>
</head>
<body
	class="tela_aluno">
	<div class="menu">
		<jsp:include page="menuC.jsp" />
	</div>
	 <div class=title align="center">
     <H1><b >Area da Secretaria</b></H1>
     <h2>Manter Cadastros</h2>
     </div>
	<br />
	<div align="center" class="container">
		<form action="cadastros" method="post">
		  <img class="aluna" src="./resources/imagens/menuSecretaria.webp" alt="Girl">
		   <div class="circulo"> </div>
		</form>
	</div>
</body>
<style>



 
 * {
    margin: 0;
    padding: 0;
    border: 0;
    box-sizing: border-box;
    font-family: "Poppins";
    text-decoration: none;
    color: black;
  
}

body {
	background-color: #ffffff;
	
}


.menu {
background-color: #f0f0f0;
padding:20px;

}

.aluna  {
 height: 400px;
 width: 500px;
 margin-left: 700px;
 margin-top: -220px;

}


b {
font-family: "Poppins";
font-size: 30px;
text-transform: uppercase;
font-weight: 600;

}

.title {
   margin-top: 180px;
   margin-left: 50px;
   text-align: left;
   
 }

.circulo {
    position: absolute;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    background: linear-gradient(45deg, rgb(157, 205, 236), rgb(48, 103, 105));
    clip-path: circle(40% at right 90%);
    z-index: -1; /*deixa atras a posiação */


}
</style>
</html>