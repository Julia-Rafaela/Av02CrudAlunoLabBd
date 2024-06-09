package br.edu.fateczl.SpringAluno.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.fateczl.SpringAluno.model.Aluno;
import br.edu.fateczl.SpringAluno.model.Chamada;
import br.edu.fateczl.SpringAluno.model.ConsultarChamada;
import br.edu.fateczl.SpringAluno.model.Disciplina;

@Repository
public class ConsultaChamadaDao implements IConsultaChamadaDao {
    
    private GenericDao gDao;

    public ConsultaChamadaDao(GenericDao gDao) {
        this.gDao = gDao;
    }
    
    @Override
    public List<ConsultarChamada> listarChamadas(String alunoC) throws SQLException, ClassNotFoundException {
        List<ConsultarChamada> consultas = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT * FROM fn_consultaC(?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, alunoC);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ConsultarChamada cc = new ConsultarChamada(); // Corrija para ConsultarChamada

            Aluno aluno = new Aluno(); // Você pode precisar instanciar o aluno e a disciplina aqui também
            aluno.setCpf(rs.getString("aluno"));
            aluno.setNome(rs.getString("nomeAluno"));

            Disciplina disciplina = new Disciplina();
            disciplina.setNome(rs.getString("disciplina"));

            cc.setAluno(aluno); // Configure o aluno e a disciplina no ConsultarChamada
            cc.setDisciplina(disciplina);

            Chamada cm = new Chamada();
            cm.setFalta(rs.getInt("falta"));
            cm.setCodigo(rs.getInt("codigo"));
            cc.setChamada(cm);

            consultas.add(cc);
        }
        rs.close();
        ps.close();
        c.close();
        return consultas;
    }
}


