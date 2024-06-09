package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.ConsultarChamada;

public interface IConsultaChamadaDao {


	List<ConsultarChamada> listarChamadas(String aluno) throws SQLException, ClassNotFoundException;

}