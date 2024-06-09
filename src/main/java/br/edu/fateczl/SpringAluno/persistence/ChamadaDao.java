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
import br.edu.fateczl.SpringAluno.model.Chamada;
import br.edu.fateczl.SpringAluno.model.Disciplina;

@Repository
public class ChamadaDao implements IChamadaDao {

    private GenericDao gDao;

    public ChamadaDao(GenericDao gDao) {
        this.gDao = gDao;
    }

    @Override
    public List<Chamada> consultar(String codigo) throws SQLException, ClassNotFoundException {
        List<Chamada> chamadas = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT * FROM fn_chamadasAlter(?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Chamada cm = new Chamada();
            cm.setCodigo(rs.getInt("codigo"));

            Aluno a = new Aluno();
            a.setNome(rs.getString("aluno"));
            a.setCpf(rs.getString("nomeAluno"));

            Disciplina disciplina = new Disciplina();
            disciplina.setNome(rs.getString("disciplina"));

            cm.setAluno(a);
            cm.setDisciplina(disciplina);
            cm.setFalta(rs.getInt("falta"));

            chamadas.add(cm);
        }
        rs.close();
        ps.close();
        c.close();
        return chamadas;
    }


    @Override
    public String iudChamada(String op, Chamada cm) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "CALL GerenciarChamada(?,?,?,?,?,?,?)";
        CallableStatement cs = c.prepareCall(sql);
        cs.setString(1, op);
        cs.setInt(2, cm.getCodigo());
        cs.setInt(3, cm.getDisciplina().getCodigo());
        cs.setString(4, cm.getAluno().getCpf());
        cs.setInt(5, cm.getFalta());
        cs.setString(6, cm.getData());
        cs.registerOutParameter(7, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(7);
        System.out.println(cm.getFalta());
        System.out.println(cm.getAluno());
        cs.close();
        c.close();
        return saida;
    }

    @Override
    public List<Chamada> listarChamada(String codigoDisciplina) throws SQLException, ClassNotFoundException {
        List<Chamada> chamadas = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT * FROM fn_chamadas(?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, codigoDisciplina);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Chamada cm = new Chamada();

            Aluno a = new Aluno();
            a.setCpf(rs.getString("aluno"));
            
            a.setNome(rs.getString("nomeAluno"));
            

            Disciplina disciplina = new Disciplina();
            disciplina.setNome(rs.getString("disciplina"));

            cm.setAluno(a);
            cm.setDisciplina(disciplina);

            chamadas.add(cm);
        }
        rs.close();
        ps.close();
        c.close();
        return chamadas;
    }

}