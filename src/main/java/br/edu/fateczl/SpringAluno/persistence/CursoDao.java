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

import br.edu.fateczl.SpringAluno.model.Curso;

@Repository
public class CursoDao implements ICrud<Curso>, ICursoDao {

	private GenericDao gDao;

	public CursoDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public Curso consultar(Curso cs) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, carga_horaria, sigla, nota_enade FROM Curso WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cs.getCodigo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			cs.setNome(rs.getString("nome"));
			cs.setCarga_horaria(rs.getString("carga_horaria"));
			cs.setSigla(rs.getString("sigla"));
			cs.setNota_enade(rs.getDouble("nota_enade"));
		}
		rs.close();
		ps.close();
		c.close();
		return cs;
	}

	@Override
	public List<Curso> listar() throws SQLException, ClassNotFoundException {

		List<Curso> cursos = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM fn_cursos()";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			Curso cs = new Curso();
			cs.setCodigo(rs.getInt("codigo"));
			cs.setNome(rs.getString("nome"));
			cs.setCarga_horaria(rs.getString("carga_horaria"));
			cs.setSigla(rs.getString("sigla"));
			cs.setNota_enade(rs.getDouble("nota_enade"));
			cursos.add(cs);
		}
		rs.close();
		ps.close();
		c.close();
		return cursos;
	}

	@Override
	public String iudCurso(String acao, Curso cc) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL GerenciarCurso(?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, acao);
		cs.setInt(2, cc.getCodigo());
		cs.setString(3, cc.getNome());
		cs.setString(4, cc.getCarga_horaria());
		cs.setString(5, cc.getSigla());
		cs.setDouble(6, cc.getNota_enade());
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(7);
		cs.close();
		c.close();
		return saida;
	}

	// Listar Comum do ICRUD
		public List<Curso> listarCurso(int codigo) throws SQLException, ClassNotFoundException {
		    List<Curso> cursos = new ArrayList<>();
		    Connection c = gDao.getConnection();
		    String sql = "SELECT codigo, nome, carga_horaria, sigla, nota_enade FROM fn_cursos(?)";
		    PreparedStatement ps = c.prepareStatement(sql);
		    ps.setInt(1, codigo);
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        Curso cs = new Curso();
		        cs.setCodigo(rs.getInt("codigo"));
		        cs.setNome(rs.getString("nome"));
		        cs.setCarga_horaria(rs.getString("carga_horaria"));
		        cs.setSigla(rs.getString("sigla"));
		        cs.setNota_enade(rs.getDouble("nota_enade"));
		        cursos.add(cs);
		    }
		    rs.close();
		    ps.close();
		    c.close();
		    return cursos;
		}

	
}
	