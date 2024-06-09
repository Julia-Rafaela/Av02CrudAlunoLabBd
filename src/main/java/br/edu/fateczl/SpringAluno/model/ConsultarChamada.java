package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultarChamada {
	
	Chamada chamada;
	Aluno aluno;
	Disciplina disciplina;
	
	@Override
	public String toString() {
		return "ConsultarChamada [chamada=" + chamada + ", aluno=" + aluno + ", disciplina=" + disciplina + "]";
	}
}