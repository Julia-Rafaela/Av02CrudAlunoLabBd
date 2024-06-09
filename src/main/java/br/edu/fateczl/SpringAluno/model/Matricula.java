package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Matricula {
	
	int codigo;
	Aluno aluno;
	Disciplina disciplina;
	String data_m;
	String status_m;
    Notas nota;
	@Override
	public String toString() {
		return "Matricula [codigo=" + codigo + ", aluno=" + aluno + ", disciplina=" + disciplina + ", data_m=" + data_m
				+ ", status_m=" + status_m + "]";
	}
}