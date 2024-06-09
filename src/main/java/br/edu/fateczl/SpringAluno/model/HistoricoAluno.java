package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricoAluno {

	Aluno aluno;
	Matricula matricula;
	Curso curso;
	@Override
	public String toString() {
		return "HistoricoAluno [aluno=" + aluno + ", matricula=" + matricula + ", curso=" + curso + "]";
	}
	
}
