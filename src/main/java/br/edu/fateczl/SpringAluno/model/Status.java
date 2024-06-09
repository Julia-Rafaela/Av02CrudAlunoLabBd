package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {
	
	String nome;

	@Override
	public String toString() {
		return nome;
	}

}

