package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Curso;


public interface ICursoDao {
	
	public String iudCurso (String acao, Curso c) throws SQLException, ClassNotFoundException;
	public List<Curso> listarCurso(int codigo) throws SQLException, ClassNotFoundException;
	
}