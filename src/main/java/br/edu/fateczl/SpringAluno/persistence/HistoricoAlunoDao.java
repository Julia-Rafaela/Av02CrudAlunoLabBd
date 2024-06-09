package br.edu.fateczl.SpringAluno.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.fateczl.SpringAluno.model.Aluno;
import br.edu.fateczl.SpringAluno.model.Curso;
import br.edu.fateczl.SpringAluno.model.HistoricoAluno;
import br.edu.fateczl.SpringAluno.model.Matricula;

@Repository
public class HistoricoAlunoDao {

	 private GenericDao gDao;

	    public HistoricoAlunoDao(GenericDao gDao) {
	        this.gDao = gDao;
	    }

	
	public List<HistoricoAluno> listarHistoricoAluno(String cpf) throws SQLException, ClassNotFoundException {
		 List<HistoricoAluno> historico = new ArrayList<>();
	        Connection c = gDao.getConnection();
	        String sql = "SELECT RA, NomeCompleto, PontuacaoVestibular, PosicaoVestibular, NomeCurso, DataPrimeiraMatricula FROM ConsultarHistoricoAluno(?)";
	        PreparedStatement ps = c.prepareStatement(sql);
	        ps.setString(1, cpf);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            HistoricoAluno ha = new HistoricoAluno();

	            Aluno a = new Aluno();
	            a.setRa(rs.getInt("RA"));
	            a.setNome(rs.getString("NomeCompleto"));
	            a.setPontuacao_vestibular(rs.getDouble("PontuacaoVestibular"));
	            a.setPosicao_vestibular(rs.getInt("PosicaoVestibular"));

	            Curso cs = new Curso();
	            cs.setNome(rs.getString("NomeCurso"));

	            Matricula m = new Matricula();
	            m.setData_m(rs.getString("DataPrimeiraMatricula"));

	            ha.setAluno(a);
	            ha.setCurso(cs);
	            ha.setMatricula(m);

	            historico.add(ha);
	        }
	        rs.close();
	        ps.close();
	        c.close();
	        return historico;
	    }
}
