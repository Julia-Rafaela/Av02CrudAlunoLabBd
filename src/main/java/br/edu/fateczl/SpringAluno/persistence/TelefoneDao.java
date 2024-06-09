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
import br.edu.fateczl.SpringAluno.model.Telefone;



@Repository
public class TelefoneDao implements  ITelefoneDao {

	private GenericDao gDao;

	public TelefoneDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	

	
	
	@Override
	public String iudTelefone(String acao, Telefone t) throws SQLException, ClassNotFoundException {
		String saida = "";
        try (Connection c = gDao.getConnection();
             CallableStatement cs = c.prepareCall("{CALL GerenciarTelefone (?,?,?,?,?)}")) {
            cs.setString(1, acao);
            cs.setInt(2, t.getCodigo());
            cs.setString(3, (t.getAluno() != null) ? t.getAluno().getCpf() : null); 
            cs.setString(4, t.getTelefone());
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.execute();
            saida = cs.getString(5);
        }
        return saida;
	}

	@Override
	public List<Telefone> listarTelefone(String cpf) throws SQLException, ClassNotFoundException {
		 List<Telefone> telefones = new ArrayList<>();
		    Connection c = gDao.getConnection();
		    String sql = "SELECT codigo, telefone, aluno FROM fn_telefones(?)";
		    PreparedStatement ps = c.prepareStatement(sql);
		    ps.setString(1, cpf);
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        Telefone telefone = new Telefone();
		        telefone.setCodigo(rs.getInt("codigo"));
		        telefone.setTelefone(rs.getString("telefone"));
		        Aluno aluno = new Aluno();
		        aluno.setNome(rs.getString("aluno"));
		        telefone.setAluno(aluno);
		        telefones.add(telefone);
		    }
		    rs.close();
		    ps.close();
		    c.close();
		    return telefones;
	}

	
	
}

