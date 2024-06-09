package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Telefone {
	
	int codigo;
	String telefone;
	Aluno aluno;
	
	
	@Override
	public String toString() {
		return "Telefone [codigo=" + codigo + ", telefone=" + telefone + ", aluno=" + aluno + "]";
	}


	
	
	


}

