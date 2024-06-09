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

import br.edu.fateczl.SpringAluno.model.Disciplina;
import br.edu.fateczl.SpringAluno.model.Professor;

@Repository
public class DisciplinaDao implements ICrud<Disciplina>, IDiciplinaDao {

	private GenericDao gDao;

	public DisciplinaDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public Disciplina consultar(Disciplina d) throws SQLException, ClassNotFoundException {
	    String sql = "SELECT d.codigo AS codigo, d.nome AS nome, d.horas_inicio, d.duracao, "
	            + "d.dia_semana, d.professor AS codigoProfessor, p.nome AS nomeProfessor "
	            + "FROM Disciplina d "
	            + "INNER JOIN Professor p ON d.professor = p.codigo "
	            + "WHERE d.codigo = ?";

	    try (Connection c = gDao.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
	        ps.setInt(1, d.getCodigo());
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            d.setCodigo(rs.getInt("codigo"));
	            d.setNome(rs.getString("nome"));
	            d.setHoras_inicio(rs.getString("horas_inicio"));
	            d.setDuracao(rs.getInt("duracao"));
	            d.setDia_semana(rs.getString("dia_semana"));

	            Professor p = new Professor();
	            p.setCodigo(rs.getInt("codigoProfessor"));
	            p.setNome(rs.getString("nomeProfessor"));

	            d.setProfessor(p);
	        }
	    }
	    return d;
	}

	@Override
	public List<Disciplina> listar() throws SQLException, ClassNotFoundException {
		List<Disciplina> disciplinas = new ArrayList<>();
		String sql = "SELECT * FROM fn_disciplinas()";

		try (Connection c = gDao.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setCodigo(rs.getInt("codigo"));
				d.setNome(rs.getString("nome"));
				d.setHoras_inicio(rs.getString("horas_inicio"));
				d.setDuracao(rs.getInt("duracao"));
				d.setDia_semana(rs.getString("dia_semana"));

				Professor p = new Professor();
				p.setCodigo(rs.getInt("codigoProfessor"));
				p.setNome(rs.getString("nomeProfessor"));

				d.setProfessor(p);

				disciplinas.add(d);
			}
		}
		return disciplinas;
	}

	@Override
	public String iudDisciplina(String op, Disciplina d) throws SQLException, ClassNotFoundException {
		String sql = "CALL GerenciarDisciplina(?,?,?,?,?,?,?,?)";

		try (Connection c = gDao.getConnection(); CallableStatement cs = c.prepareCall(sql)) {
			cs.setString(1, op);
			cs.setInt(2, d.getCodigo());
			cs.setString(3, d.getNome());
			cs.setString(4, d.getHoras_inicio());
			cs.setInt(5, d.getDuracao());
			cs.setString(6, d.getDia_semana());
			cs.setInt(7, d.getProfessor().getCodigo());
			cs.registerOutParameter(8, Types.VARCHAR);
			cs.execute();
			return cs.getString(8);
		}
	}

	@Override
	public List<Disciplina> listarDisciplina() throws SQLException, ClassNotFoundException {
		List<Disciplina> disciplinas = new ArrayList<>();
		String sql = "SELECT codigo, nome, horas_inicio, duracao, dia_semana, codigoProfessor, nomeProfessor"
				+ "FROM fn_disciplinas()";

		try (Connection c = gDao.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setCodigo(rs.getInt("codigo"));
				d.setNome(rs.getString("nome"));
				d.setHoras_inicio(rs.getString("horas_inicio"));
				d.setDuracao(rs.getInt("duracao"));
				d.setDia_semana(rs.getString("dia_semana"));

				Professor p = new Professor();
				p.setCodigo(rs.getInt("codigoProfessor"));
				p.setNome(rs.getString("nomeProfessor"));

				d.setProfessor(p);

				disciplinas.add(d);
			}
		}
		return disciplinas;
	}
}
