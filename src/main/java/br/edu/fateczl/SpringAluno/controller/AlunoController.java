package br.edu.fateczl.SpringAluno.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.SpringAluno.model.Aluno;
import br.edu.fateczl.SpringAluno.model.Curso;
import br.edu.fateczl.SpringAluno.persistence.AlunoDao;
import br.edu.fateczl.SpringAluno.persistence.CursoDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;

@Controller
public class AlunoController {

    @Autowired
    GenericDao gDao;

    @Autowired
    CursoDao cDao;

    @Autowired
    AlunoDao aDao;

    @RequestMapping(name = "aluno", value = "/aluno", method = RequestMethod.GET)
    public ModelAndView AlunoGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        String cmd = allRequestParam.get("cmd");
        String cpf = allRequestParam.get("cpf");

        Aluno a = new Aluno();
        a.setCpf(cpf);

        String saida = "";
        String erro = "";
        List<Aluno> alunos = new ArrayList<>();
        List<Curso> cursos = new ArrayList<>();

        try {
            if (cmd != null) {
                if (cmd.contains("alterar")) {
                    a = buscarAluno(a);
                } else if (cmd.contains("excluir")) {
                    a = buscarAluno(a);
                    saida = excluirAluno(a);
                }
            }

            alunos = listarAlunos();
            cursos = listarCursos();

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
            model.addAttribute("aluno", a);
            model.addAttribute("alunos", alunos);
            model.addAttribute("cursos", cursos);
        }

        return new ModelAndView("aluno");

    }

    @RequestMapping(name = "aluno", value = "/aluno", method = RequestMethod.POST)
    public ModelAndView AlunoPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        String cmd = allRequestParam.get("botao");
        String cpf = allRequestParam.get("cpf");
        String nome = allRequestParam.get("nome");
        String nome_social = allRequestParam.get("nome_social");
        String data_nascimento = allRequestParam.get("data_nascimento");
        String email_pessoal = allRequestParam.get("email_pessoal");
        String email_corporativo = allRequestParam.get("email_corporativo");
        String conclusao_segundo_grau = allRequestParam.get("conclusao_segundo_grau");
        String instituicao_conclusao = allRequestParam.get("instituicao_conclusao");
        String pontuacao_vestibular = allRequestParam.get("pontuacao_vestibular");
        String posicao_vestibular = allRequestParam.get("posicao_vestibular");
        String ano_ingresso = allRequestParam.get("ano_ingresso");
        String semestre_ingresso = allRequestParam.get("semestre_ingresso");
        String semestre_limite_graduacao = allRequestParam.get("semestre_limite_graduacao");
        String cursoCodigo = allRequestParam.get("curso");

        String saida = "";
        String erro = "";
        Aluno a = new Aluno();

        List<Aluno> alunos = new ArrayList<>();
        List<Curso> cursos = new ArrayList<>();

        if (!cmd.contains("Listar")) {
            a.setCpf(cpf);
        }

        try {
            cursos = listarCursos();

            if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
                a.setNome(nome);
                a.setNome_social(nome_social);
                a.setData_nascimento(data_nascimento);
                a.setEmail_pessoal(email_pessoal);
                a.setEmail_corporativo(email_corporativo);
                a.setConclusao_segundo_grau(conclusao_segundo_grau);
                a.setInstituicao_conclusao(instituicao_conclusao);

                double pontuacao = Double.parseDouble(pontuacao_vestibular);
                int posicao = Integer.parseInt(posicao_vestibular);
                int ano = Integer.parseInt(ano_ingresso);
                int semestre = Integer.parseInt(semestre_ingresso);
                int semestre_limite = Integer.parseInt(semestre_limite_graduacao);

                a.setPontuacao_vestibular(pontuacao);
                a.setPosicao_vestibular(posicao);
                a.setAno_ingresso(ano);
                a.setSemestre_ingresso(semestre);
                a.setSemestre_limite_graduacao(semestre_limite);

                Curso curso = new Curso();
                curso.setCodigo(Integer.parseInt(cursoCodigo));
                curso = buscarCurso(curso);
                a.setCurso(curso);
            }

            if (cmd.contains("Cadastrar")) {
                saida = cadastrarAluno(a);
                a = null;
            }
            if (cmd.contains("Alterar")) {
                saida = alterarAluno(a);
                a = null;
            }
            if (cmd.contains("Excluir")) {
                a = buscarAluno(a);
                saida = excluirAluno(a);
                a = null;
            }
            if (cmd.contains("Buscar")) {
                a = buscarAluno(a);
            }
            if (cmd.contains("Listar")) {
                alunos = listarAlunos();
            }
        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
            model.addAttribute("aluno", a);
            model.addAttribute("alunos", alunos);
            model.addAttribute("cursos", cursos);
        }

        return new ModelAndView("aluno");
    }

    private String cadastrarAluno(Aluno a) throws SQLException, ClassNotFoundException {
        String saida = aDao.iudAluno("I", a);
        return saida;
    }

    private String alterarAluno(Aluno a) throws SQLException, ClassNotFoundException {
        String saida = aDao.iudAluno("U", a);
        return saida;
    }

    private String excluirAluno(Aluno a) throws SQLException, ClassNotFoundException {
        if (a.getCurso() != null) {
            a.getCurso().getCodigo();
        }
        String saida = aDao.iudAluno("D", a);
        return saida;
    }

    private Aluno buscarAluno(Aluno a) throws SQLException, ClassNotFoundException {
        a = aDao.consultar(a);
        return a;
    }

    private List<Aluno> listarAlunos() throws SQLException, ClassNotFoundException {
        List<Aluno> alunos = aDao.listar();
        return alunos;
    }

    private Curso buscarCurso(Curso c) throws SQLException, ClassNotFoundException {
        c = cDao.consultar(c);
        return c;
    }

    private List<Curso> listarCursos() throws SQLException, ClassNotFoundException {
        List<Curso> cursos = cDao.listar();
        return cursos;
    }

}