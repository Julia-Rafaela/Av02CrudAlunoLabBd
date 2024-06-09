package br.edu.fateczl.SpringAluno.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Curso {
	
	int codigo;
	String nome;
	String carga_horaria;
	String sigla;
	double nota_enade;
	
	@Override
	public String toString() {
		return nome;
	}
	
	
	}
	
	

