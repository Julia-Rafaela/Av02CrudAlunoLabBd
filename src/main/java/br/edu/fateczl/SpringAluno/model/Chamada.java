package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chamada {
	
	
	int codigo;
	Disciplina disciplina;
	Aluno aluno;
	int falta;
	String data;
	
	
	@Override
	public String toString() {
		return "Chamada [codigo=" + codigo + ", disciplina=" + disciplina + ", aluno=" + aluno + ", falta=" + falta
				+ ", data=" + data + "]";
	}
}

