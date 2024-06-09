package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notas {
	int codigo;
	String nota1;
	String nota2;
	String nota_recuperacao;
	String media;
	Aluno aluno;
	Disciplina disciplina;
	@Override
	public String toString() {
		return "Notas [codigo=" + codigo + ", nota1=" + nota1 + ", nota2=" + nota2 + ", nota_recuperacao="
				+ nota_recuperacao + ", media=" + media + ", aluno=" + aluno + ", disciplina=" + disciplina + "]";
	}

}
