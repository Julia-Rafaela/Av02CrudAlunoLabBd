package br.edu.fateczl.SpringAluno.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.fateczl.SpringAluno.model.Aluno;
import br.edu.fateczl.SpringAluno.model.Disciplina;
import br.edu.fateczl.SpringAluno.model.Dispensa;

@Repository
public class SolicitacaoDao implements ISolicitacaoDao{
	private GenericDao gDao;

	public SolicitacaoDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public String solicitacao(String op, int codigo, String cpf) throws SQLException, ClassNotFoundException {
		 Connection c = gDao.getConnection();
		    String sql = "CALL iudSolicitacao(?, ?, ?, ?)"; 
		    CallableStatement cs = c.prepareCall(sql);
		    cs.setString(1, op); 
		    cs.setInt(2, codigo);
		    cs.setString(3, cpf);
		    cs.registerOutParameter(4, Types.VARCHAR); 

		    cs.execute();
		  
		   
		    String saida = cs.getString(4); 
		    
		    cs.close();
		    c.close();
		    return saida;
	}

	@Override
	public List<Dispensa> listarSolicitacao() throws SQLException, ClassNotFoundException {
	    List<Dispensa> dispensas = new ArrayList<>();
	    Connection c = gDao.getConnection();
	    String sql = "SELECT * FROM fn_solitacao()";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        Dispensa ds = new Dispensa();
	        Aluno aluno = new Aluno(); 
	        aluno.setCpf(rs.getString("aluno")); 
	        Disciplina disciplina = new Disciplina();
	        disciplina.setNome(rs.getString("disciplina"));
	        
	        ds.setCodigo(rs.getInt("codigo"));
	        ds.setData_s(rs.getString("data_solicitacao"));
	        ds.setInstituicao(rs.getString("instituicao"));

	        ds.setAluno(aluno); 
	        ds.setDisciplina(disciplina);

	        dispensas.add(ds);
	    }
	    rs.close();
	    ps.close();
	    c.close();
	    return dispensas;
	}
}
