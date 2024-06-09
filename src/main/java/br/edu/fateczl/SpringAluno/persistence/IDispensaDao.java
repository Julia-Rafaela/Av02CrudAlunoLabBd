package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Dispensa;

public interface IDispensaDao {
	public String iudDispensa (String acao, Dispensa ds) throws SQLException, ClassNotFoundException;
	public List<Dispensa> listarDispensa(String aluno) throws SQLException, ClassNotFoundException;
}
