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
import br.edu.fateczl.SpringAluno.model.Disciplina;
import br.edu.fateczl.SpringAluno.model.Grade;

@Repository
public class GradeDao implements ICrud<Grade>, IGradeDao {

    private GenericDao gDao;

    public GradeDao(GenericDao gDao) {
        this.gDao = gDao;
    }

    @Override
    public Grade consultar(Grade g) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "SELECT g.codigo AS codigo, g.curso AS codigoCurso, g.disciplina AS codigoDisciplina, "
                + "c.nome AS nomeCurso, d.nome AS nomeDisciplina "
                + "FROM Grade g "
                + "INNER JOIN curso c ON c.codigo = g.curso "
                + "INNER JOIN disciplina d ON d.codigo = g.disciplina "
                + "WHERE g.codigo = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, g.getCodigo());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Curso curso = new Curso();
            curso.setCodigo(rs.getInt("codigoCurso"));
            curso.setNome(rs.getString("nomeCurso"));

            Disciplina disciplina = new Disciplina();
            disciplina.setCodigo(rs.getInt("codigoDisciplina"));
            disciplina.setNome(rs.getString("nomeDisciplina"));

            g.setCodigo(rs.getInt("codigo"));

            g.setCurso(curso);
            g.setDisciplina(disciplina);
        }
        rs.close();
        ps.close();
        c.close();
        return g;
    }

    
    @Override
    public String iudGrade(String op, Grade g) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "CALL GerenciarGrade(?,?,?,?,?)";
        CallableStatement cs = c.prepareCall(sql);
        cs.setString(1, op);
        cs.setInt(2, g.getCodigo());
        cs.setInt(3, g.getCurso().getCodigo());
        cs.setInt(4, g.getDisciplina().getCodigo());
        cs.registerOutParameter(5, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(5);
        cs.close();
        c.close();
        return saida;
    }

    // Listar Grade com filtro de curso
    @Override
    public List<Grade> listarGrade(String curso) throws SQLException, ClassNotFoundException {
        List<Grade> grades = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT * FROM fn_grade(?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, curso);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Grade grade = new Grade();
            Curso cr = new Curso();
            cr.setNome(rs.getString("curso"));

            Disciplina disciplina = new Disciplina();
            disciplina.setNome(rs.getString("disciplina"));

            grade.setCodigo(rs.getInt("codigo"));
            grade.setCurso(cr);
            grade.setDisciplina(disciplina);

            grades.add(grade);
        }
        rs.close();
        ps.close();
        c.close();
        return grades;
    }

    // Listar todas as grades
    @Override
    public List<Grade> listar() throws SQLException, ClassNotFoundException {
        List<Grade> grades = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT g.codigo, c.nome AS curso, d.nome AS disciplina "
                + "FROM Grade g "
                + "INNER JOIN curso c ON c.codigo = g.curso "
                + "INNER JOIN disciplina d ON d.codigo = g.disciplina";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Grade grade = new Grade();
            Curso curso = new Curso();
            curso.setNome(rs.getString("curso"));

            Disciplina disciplina = new Disciplina();
            disciplina.setNome(rs.getString("disciplina"));

            grade.setCodigo(rs.getInt("codigo"));
            grade.setCurso(curso);
            grade.setDisciplina(disciplina);

            grades.add(grade);
        }
        rs.close();
        ps.close();
        c.close();
        return grades;
    }

}

