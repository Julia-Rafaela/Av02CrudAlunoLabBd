package br.edu.fateczl.SpringAluno.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Aluno {

	private int ra;
	private String cpf;
	private String nome;
	private String nome_social;
	private String data_nascimento;
	private String email_pessoal;
	private String email_corporativo;
	private String conclusao_segundo_grau;
	private String instituicao_conclusao;
	private double pontuacao_vestibular;
	private int posicao_vestibular;
	private int ano_ingresso;
	private int ano_limite_graduacao;
	private int semestre_ingresso;
	private int semestre_limite_graduacao;
	Curso curso;

	@Override
	public String toString() {
		return nome;
	}

}