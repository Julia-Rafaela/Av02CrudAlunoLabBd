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
public class DispensaDao  implements IDispensaDao{
	private GenericDao gDao;

	public DispensaDao(GenericDao gDao) {
		this.gDao = gDao;
	}
	 @Override
	 public String iudDispensa(String op, Dispensa ds) throws SQLException, ClassNotFoundException {
		 Connection c = gDao.getConnection();
		    String sql = "CALL iudDispensa(?,?,?,?,?,?)";
		    CallableStatement cs = c.prepareCall(sql);
		    cs.setString(1, op);
		    cs.setString(2, ds.getAluno().getCpf());
		    cs.setInt(3, ds.getDisciplina().getCodigo());
		    cs.setString(4, ds.getData_s());
		    cs.setString(5, ds.getInstituicao());
		    cs.registerOutParameter(6, Types.VARCHAR);
		    cs.execute();
		    String saida = cs.getString(6); 
		    cs.close();
		    c.close();
		    return saida;
		}

	    @Override
	    public List<Dispensa> listarDispensa(String aluno) throws SQLException, ClassNotFoundException {
	        List<Dispensa> dispensas = new ArrayList<>();
	        Connection c = gDao.getConnection();
	        String sql = "SELECT * FROM fn_solitacaoDispensa(?)";
	        PreparedStatement ps = c.prepareStatement(sql);
	        ps.setString(1, aluno);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Dispensa ds = new Dispensa();
	             
	            Aluno a = new Aluno();
	            a.setCpf(rs.getString("nome"));

	            Disciplina disciplina = new Disciplina();
	            disciplina.setNome(rs.getString("disciplina"));
	            
                ds.setCodigo(rs.getInt("codigo"));
	            ds.setData_s(rs.getString("data_solicitacao"));
	            ds.setInstituicao(rs.getString("instituicao"));


	            ds.setAluno(a);
	            ds.setDisciplina(disciplina);

	            dispensas.add(ds);
	        }
	        rs.close();
	        ps.close();
	        c.close();
	        return dispensas;
	    }


}
