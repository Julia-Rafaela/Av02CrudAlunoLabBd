package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professor {

	int codigo;
	String nome;

	@Override
	public String toString() {
		return nome;
	}

}
