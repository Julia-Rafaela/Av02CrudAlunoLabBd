package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatriculaAluno {

	Disciplina disciplina;
	Notas nota;
	Chamada chamada;
	Professor professor;

	@Override
	public String toString() {
		return "MatriculaAluno [disciplina=" + disciplina + ", nota=" + nota + ", chamada=" + chamada + ", professor="
				+ professor + "]";
	}
}
