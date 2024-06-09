package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grade {

	int codigo;
	Curso curso;
	Disciplina disciplina;

	@Override
	public String toString() {
		return "Grade [curso=" + curso + ", disciplina=" + disciplina + "]";
	}

}