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
import br.edu.fateczl.SpringAluno.model.Notas;
@Repository
public class NotaDao implements ICrud<Notas>, INotaDao{

	private GenericDao gDao;

	public NotaDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	
	@Override
	public String iudNotas(String op, Notas n) throws SQLException, ClassNotFoundException {
		 String saida = "";
		    try (Connection con = gDao.getConnection();
		         CallableStatement cs = con.prepareCall("{CALL InserirNotas (?, ?, ?, ?, ?, ?, ?)}")) {

		        cs.setString(1, op);
		        cs.setInt(2, n.getDisciplina().getCodigo());
		        cs.setString(3, n.getAluno().getCpf());
		        cs.setString(4, n.getNota1());
		        cs.setString(5, n.getNota2());
		        cs.setString(6, n.getNota_recuperacao());
		        cs.registerOutParameter(7, Types.VARCHAR);

		        cs.execute();
		        saida = cs.getString(7);
		    }
		    return saida;
	}

	@Override
	public List<Notas> listarNotas(String disciplina) throws SQLException, ClassNotFoundException {
		List<Notas> notas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM ListarNotasEMedias(?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, disciplina);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Notas n = new Notas();

			Aluno a = new Aluno();
			a.setNome(rs.getString("aluno"));

			
			n.setCodigo(rs.getInt("codigo"));
			n.setNota1(rs.getString("nota1"));
			n.setNota2(rs.getString("nota2"));
			n.setNota_recuperacao(rs.getString("notaRecuperacao"));
			n.setMedia(rs.getString("media"));

			n.setAluno(a);
		

			notas.add(n);
		}
		rs.close();
		ps.close();
		c.close();
		return notas;
	}

	@Override
	public Notas consultar(Notas n) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT m.codigo AS codigo, m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
				+ "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula, m.status_m AS status, m.nota_final AS Media "
				+ "FROM Matricula m " + "INNER JOIN aluno a ON a.cpf = m.aluno "
				+ "INNER JOIN disciplina d ON d.codigo = m.disciplina ";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
            Notas no = new Notas();

            Aluno a = new Aluno();
            a.setCpf(rs.getString("cpfAluno"));
            a.setNome(rs.getString("nomeAluno"));

            Disciplina disciplina = new Disciplina();
            disciplina.setCodigo(rs.getInt("codigoDisciplina"));
            disciplina.setNome(rs.getString("nomeDisciplina"));

            no.setCodigo(rs.getInt("codigo"));
            n.setNota1(rs.getString("nota1"));
			n.setNota2(rs.getString("nota2"));
			n.setNota_recuperacao(rs.getString("notaRecuperacao"));
			n.setMedia(rs.getString("media"));
            no.setAluno(a);
            no.setDisciplina(disciplina);
		}
		rs.close();
		ps.close();
		c.close();
		return n;
	
	}

	@Override
	public List<Notas> listar() throws SQLException, ClassNotFoundException {
		List<Notas> notas = new ArrayList<>();
		Connection c = gDao.getConnection();
		 String sql = "SELECT n.codigo AS codigo, n.aluno AS cpfAluno, n.disciplina AS codigoDisciplina, " +
                 "a.nome AS nomeAluno, d.nome AS nomeDisciplina, " +
                 "n.nota1 AS nota1, n.nota2 AS nota2, n.notaRecuperacao AS notaRecuperacao, n.media AS media " +
                 "FROM Notas n " +
                 "INNER JOIN Aluno a ON a.cpf = n.aluno " +
                 "INNER JOIN Disciplina d ON d.codigo = n.disciplina";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		  while (rs.next()) {
		        Notas n = new Notas();

		        Aluno a = new Aluno();
		        a.setCpf(rs.getString("cpfAluno"));
		        a.setNome(rs.getString("nomeAluno"));

		        Disciplina disciplina = new Disciplina();
		        disciplina.setCodigo(rs.getInt("codigoDisciplina"));
		        disciplina.setNome(rs.getString("nomeDisciplina"));

		        n.setCodigo(rs.getInt("codigo"));
		        n.setNota1(rs.getString("nota1"));
				n.setNota2(rs.getString("nota2"));
				n.setNota_recuperacao(rs.getString("notaRecuperacao"));
				n.setMedia(rs.getString("media"));

		        n.setAluno(a);
		        n.setDisciplina(disciplina);

		        notas.add(n);
		  }
			rs.close();
			ps.close();
			c.close();
			return notas;
	}

}
