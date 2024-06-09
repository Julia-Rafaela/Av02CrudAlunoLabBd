package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dispensa {
	
	int codigo;
	Aluno aluno;
	Disciplina disciplina;
	String data_s;
	String instituicao;
	@Override
	public String toString() {
		return "Dispensa [codigo=" + codigo + ", aluno=" + aluno + ", disciplina=" + disciplina + ", data_s=" + data_s
				+ ", instituicao=" + instituicao + "]";
	}
	
	

}
