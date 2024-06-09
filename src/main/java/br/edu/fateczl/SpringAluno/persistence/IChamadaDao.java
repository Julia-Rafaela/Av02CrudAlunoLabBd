package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Chamada;


public interface IChamadaDao {
	
	public String iudChamada (String acao, Chamada c) throws SQLException, ClassNotFoundException;
	public List<Chamada> listarChamada(String disciplina) throws SQLException, ClassNotFoundException;
	List<Chamada> consultar( String codigo) throws SQLException, ClassNotFoundException;

}
