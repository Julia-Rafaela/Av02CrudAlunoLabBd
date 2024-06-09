package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;

import java.util.List;

import br.edu.fateczl.SpringAluno.model.Telefone;




public interface ITelefoneDao {
	
	public String iudTelefone (String acao, Telefone t) throws SQLException, ClassNotFoundException;
	public List<Telefone> listarTelefone(String cpf) throws SQLException, ClassNotFoundException;
	

}