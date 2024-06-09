package br.edu.fateczl.SpringAluno.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringAluno.model.Grade;



public interface IGradeDao {
	
	public String iudGrade (String acao, Grade grade) throws SQLException, ClassNotFoundException;
	public List<Grade> listarGrade(String codigo) throws SQLException, ClassNotFoundException;


}

