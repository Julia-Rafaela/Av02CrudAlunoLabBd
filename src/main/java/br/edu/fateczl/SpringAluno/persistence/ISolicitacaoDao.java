package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Dispensa;


public interface ISolicitacaoDao {
	public String solicitacao (String op, int codigo, String cpf) throws SQLException, ClassNotFoundException;
	public List<Dispensa> listarSolicitacao() throws SQLException, ClassNotFoundException;
}