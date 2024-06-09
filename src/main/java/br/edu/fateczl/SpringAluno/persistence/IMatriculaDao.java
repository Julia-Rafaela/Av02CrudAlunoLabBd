package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Matricula;

public interface IMatriculaDao {
	
	public String iudMatricula (String acao, Matricula m) throws SQLException, ClassNotFoundException;
	public List<Matricula> listarMatricula(String aluno) throws SQLException, ClassNotFoundException;

}
