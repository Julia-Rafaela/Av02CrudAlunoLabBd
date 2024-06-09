package br.edu.fateczl.SpringAluno.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.fateczl.SpringAluno.model.Chamada;
import br.edu.fateczl.SpringAluno.model.Disciplina;
import br.edu.fateczl.SpringAluno.model.MatriculaAluno;
import br.edu.fateczl.SpringAluno.model.Notas;
import br.edu.fateczl.SpringAluno.model.Professor;

@Repository
public class MatriculaAlunoDao {
	
	private GenericDao gDao;

    public MatriculaAlunoDao(GenericDao gDao) {
        this.gDao = gDao;
    }


public List<MatriculaAluno> listarMatriculaAluno(String cpf) throws SQLException, ClassNotFoundException {
	 List<MatriculaAluno> historicosM = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT CodigoDisciplina, NomeDisciplina, NomeProfessor, NotaFinal, QuantidadeFaltas FROM ListarMatriculasAprovadasAluno(?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, cpf);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
           MatriculaAluno ma = new MatriculaAluno();

           Disciplina d = new Disciplina();
           d.setCodigo(rs.getInt("CodigoDisciplina"));
           d.setNome(rs.getString("NomeDisciplina"));
           
           Professor p = new Professor();
           p.setNome(rs.getString("NomeProfessor"));
           
           Notas n = new Notas();
           n.setMedia(rs.getString("NotaFinal"));;
           
            
            Chamada cm = new Chamada();
            cm.setFalta(rs.getInt("QuantidadeFaltas"));

            ma.setDisciplina(d);
            ma.setProfessor(p);
            ma.setNota(n);
            ma.setChamada(cm);
            

            historicosM.add(ma);
        }
        rs.close();
        ps.close();
        c.close();
        return historicosM;
    }
}