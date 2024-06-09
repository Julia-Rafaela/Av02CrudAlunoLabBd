package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Notas;

public interface INotaDao {

	public String iudNotas (String acao, Notas n) throws SQLException, ClassNotFoundException;
	List<Notas> listarNotas(String disciplina) throws SQLException, ClassNotFoundException;
}
