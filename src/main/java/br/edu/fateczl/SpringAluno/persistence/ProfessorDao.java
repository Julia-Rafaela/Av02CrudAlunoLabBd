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

import br.edu.fateczl.SpringAluno.model.Professor;

@Repository
public class ProfessorDao implements ICrud<Professor>, IProfessorDao {

	private GenericDao gDao;

	public ProfessorDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public Professor consultar(Professor p) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome FROM Professor WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, p.getCodigo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			p.setNome(rs.getString("nome"));
		}
		rs.close();
		ps.close();
		c.close();
		return p;
	}

	@Override
	public List<Professor> listar() throws SQLException, ClassNotFoundException {

		List<Professor> professores = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM fn_professores()";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			Professor p = new Professor();
			p.setCodigo(rs.getInt("codigo"));
			p.setNome(rs.getString("nome"));
			professores.add(p);
		}
		rs.close();
		ps.close();
		c.close();
		return professores;
	}

	@Override
	public String iudProfessor(String acao, Professor p) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL GerenciarProfessor(?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, acao);
		cs.setInt(2, p.getCodigo());
		cs.setString(3, p.getNome());
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(4);
		cs.close();
		c.close();
		return saida;
	}

	// Listar Comum do ICRUD
	@Override
	public List<Professor> listarProfessor() throws SQLException, ClassNotFoundException {
		List<Professor> professores = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome FROM fn_professores()";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			Professor p = new Professor();
			p.setCodigo(rs.getInt("codigo"));
			p.setNome(rs.getString("nome"));
			professores.add(p);
		}
		rs.close();
		ps.close();
		c.close();
		return professores;
	}
}
	
