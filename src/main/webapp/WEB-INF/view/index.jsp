<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Menu Sistema</title>
</head>
<body >  
<p class="title">
			<p class="cadastrar">Quem esta Acessando?</p>
			</p>
<div class="form">
<nav id="menu">
      <ul>
			<li><a href="menuAluno">Aluno</a></li>
			<li><a href="menuSecretaria">Secretaria</a></li>
			<li><a href="menuProfessor">Professor</a></li>
     </ul>
     </nav>
       <img class="aluna" src="./resources/imagens/menu.webp" alt="Girl">
		   <div class="circulo"> </div>
     </div>
</body>
<style>
@import url('https://fonts.googleapis.com/css2?family=Arbutus+Slab&family=Armata&family=Teko&family=Bebas+Neue&family=Raleway:ital,wght@0,100..900;1,100..900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');


* {
    margin: 0;
    padding: 0;
    border: 0;
    box-sizing: border-box;
    font-family: "Poppins";
    text-decoration: none;
    color: black;
  
}

ul{
    list-style: none;
}

#menu {
    gap: 2rem;
    cursor: pointer;
    font-weight: 600;
    font-size: 40px;
}

#menu ul {
	margin-top: 20px;
    margin-left: 0;
    margin-left: 20px;
    text-align: center;
		
}

.form {
  margin-top: 60px;
  margin-left: 370px;
  background-color:	#dcdcdc;
  width: 600px; 
  height: 300px;
  padding: 10px;
  border-radius: 30px; 
 }
 
 #menu ul li a{
	color: #5F9EA0;
	margin-top: 30px;
	
}

#menu ul li a:hover{
	color: #191970;
}

li {
 margin-bottom: 30px;}

 .cadastrar{
  text-align: center;
  margin-top: -40px;
  font-family: "Armata";
  text-transform: uppercase;
  font-weight: 600;
  margin-left: 10px;
  font-size: 20px;
  
  
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

.aluna  {
 height: 400px;
 width: 500px;
 margin-left: 480px;
 margin-top: -270px;

}
 
</style>
</html>