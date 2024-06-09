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
import br.edu.fateczl.SpringAluno.model.Matricula;
import br.edu.fateczl.SpringAluno.model.Notas;

@Repository
public class MatriculaDao implements ICrud<Matricula>, IMatriculaDao {

	private GenericDao gDao;

	public MatriculaDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public Matricula consultar(Matricula m) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT m.codigo AS codigo, m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
				+ "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula, m.status_m AS status, m.nota_final AS Media "
				+ "FROM Matricula m " + "INNER JOIN aluno a ON a.cpf = m.aluno "
				+ "INNER JOIN disciplina d ON d.codigo = m.disciplina ";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Aluno a = new Aluno();
			a.setCpf(rs.getString("cpfAluno"));
			a.setNome(rs.getString("nomeAluno"));

			Disciplina disciplina = new Disciplina();
			disciplina.setCodigo(rs.getInt("codigoDisciplina"));
			disciplina.setNome(rs.getString("nomeDisciplina"));

			m.setCodigo(rs.getInt("codigo"));
			m.setData_m(rs.getString("dataMatricula"));
			m.setStatus_m(rs.getString("status"));

			m.setAluno(a);
			m.setDisciplina(disciplina);
		}
		rs.close();
		ps.close();
		c.close();
		return m;
	}

	@Override
	public String iudMatricula(String op, Matricula m) throws SQLException, ClassNotFoundException {
		String saida = "";
	    try (Connection con = gDao.getConnection();
	         CallableStatement cs = con.prepareCall("{CALL GerenciarMatriculaD (?, ?, ?, ?, ?, ?)}")) {

	        cs.setString(1, op);
	        if (op.contains("I")) {
	            cs.setNull(2, Types.INTEGER); 
	        } else if (op.contains("U")) {
	           
	            cs.setInt(2, m.getCodigo());
	        }
	        cs.setString(3, m.getAluno().getCpf());
	        cs.setInt(4, m.getDisciplina().getCodigo());
	        cs.setString(5, m.getData_m());
	        cs.registerOutParameter(6, Types.VARCHAR);

	        cs.execute();
	        saida = cs.getString(6);
	    } 
	    return saida;
	}

	@Override
	public List<Matricula> listarMatricula(String aluno) throws SQLException, ClassNotFoundException {
		List<Matricula> matriculas = new ArrayList<>();
	    Connection c = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        c = gDao.getConnection();
	        String sql = "SELECT * FROM fn_matricula(?)";
	        ps = c.prepareStatement(sql);
	        ps.setString(1, aluno);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Matricula m = new Matricula();

	            Aluno a = new Aluno();
	            a.setNome(rs.getString("aluno"));

	            Disciplina disciplina = new Disciplina();
	            disciplina.setNome(rs.getString("disciplina"));

	            m.setData_m(rs.getString("data_m"));
	            m.setCodigo(rs.getInt("codigo"));
	            m.setStatus_m(rs.getString("status_m"));
	            
	            Notas n = new Notas();
	            n.setMedia(rs.getString("nota_final"));

	            m.setAluno(a);
	            m.setNota(n);
	            m.setDisciplina(disciplina);

	            matriculas.add(m);
	        }
	    } finally {
	        if (rs != null) {
	            rs.close();
	        }
	        if (ps != null) {
	            ps.close();
	        }
	        if (c != null) {
	            c.close();
	        }
	    }
	    return matriculas;
	}
	

	@Override
	public List<Matricula> listar() throws SQLException, ClassNotFoundException {
		List<Matricula> matriculas = new ArrayList<>();
	    Connection c = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        c = gDao.getConnection();
	        String sql = "SELECT * FROM Listar_matricula()";
	        ps = c.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Matricula m = new Matricula();

	            Aluno a = new Aluno();
	            a.setNome(rs.getString("aluno"));

	            Disciplina disciplina = new Disciplina();
	            disciplina.setNome(rs.getString("disciplina"));

	            m.setData_m(rs.getString("data_m"));
	            m.setCodigo(rs.getInt("codigo"));
	            m.setStatus_m(rs.getString("status_m"));
	            
	            Notas n = new Notas();
	            n.setMedia(rs.getString("nota_final"));

	            m.setAluno(a);
	            m.setNota(n);
	            m.setDisciplina(disciplina);

	            matriculas.add(m);
	        }
	    } finally {
	        if (rs != null) {
	            rs.close();
	        }
	        if (ps != null) {
	            ps.close();
	        }
	        if (c != null) {
	            c.close();
	        }
	    }
	    return matriculas;
	}

}
