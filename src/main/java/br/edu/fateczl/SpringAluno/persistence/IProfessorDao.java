package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Professor;

public interface IProfessorDao {
	public String iudProfessor (String acao, Professor p) throws SQLException, ClassNotFoundException;
	public List<Professor> listarProfessor() throws SQLException, ClassNotFoundException;

}
