package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Disciplina;



public interface IDiciplinaDao {

	public String iudDisciplina (String acao, Disciplina d) throws SQLException, ClassNotFoundException;
	public List<Disciplina> listarDisciplina() throws SQLException, ClassNotFoundException;


}
