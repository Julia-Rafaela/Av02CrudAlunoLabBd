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

import br.edu.fateczl.SpringAluno.model.Disciplina;
import br.edu.fateczl.SpringAluno.model.Professor;
import br.edu.fateczl.SpringAluno.persistence.DisciplinaDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;
import br.edu.fateczl.SpringAluno.persistence.ProfessorDao;

@Controller
public class DisciplinaController {

    @Autowired
    GenericDao gDao;

    @Autowired
    ProfessorDao pDao;

    @Autowired
    DisciplinaDao dDao;

    @RequestMapping(name = "disciplina", value = "/disciplina", method = RequestMethod.GET)
    public ModelAndView AlunoGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        String cmd = allRequestParam.get("cmd");
        String codigo = allRequestParam.get("codigo");

        Disciplina d = new Disciplina();

        if (codigo != null && !codigo.isEmpty()) {
            try {
                d.setCodigo(Integer.parseInt(codigo));
            } catch (NumberFormatException e) {
                String erroMsg = "O código da disciplina deve ser um número inteiro.";
                model.addAttribute("erro", erroMsg);
                return new ModelAndView("pagina_de_erro");
            }
        }

        String saida = "";
        String erro = "";
        List<Disciplina> disciplinas = new ArrayList<>();
        List<Professor> professores = new ArrayList<>();

        try {
            if (cmd != null) {
                if (cmd.contains("alterar")) {
                    d = buscarDisciplina(d);
                } else if (cmd.contains("excluir")) {
                    d = buscarDisciplina(d);
                    saida = excluirDisciplina(d);
                }
            }

            disciplinas = listarDisciplinas();
            professores = listarProfessores();

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
            model.addAttribute("disciplina", d);
            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("professores", professores);
        }

        return new ModelAndView("disciplina");

    }

    @RequestMapping(name = "disciplina", value = "/disciplina", method = RequestMethod.POST)
    public ModelAndView AlunoPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        String cmd = allRequestParam.get("botao");
        String codigo = allRequestParam.get("codigo");
		String nome = allRequestParam.get("nome");
		String horas_inicio = allRequestParam.get("horas_inicio");
		String duracao = allRequestParam.get("duracao");
		String dia_semana = allRequestParam.get("dia_semana");
		String professorCodigo = allRequestParam.get("professor");

        String saida = "";
        String erro = "";
        Disciplina d = new Disciplina();

        List<Disciplina> disciplinas = new ArrayList<>();
        List<Professor> professores = new ArrayList<>();

        if (!cmd.contains("Listar")) {
            d.setCodigo(Integer.parseInt(codigo));
        }

        try {
            professores = listarProfessores();

            if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
            	 d.setNome(nome);
    	    	 d.setHoras_inicio(horas_inicio);
    	    	 d.setDuracao(Integer.parseInt(duracao));
    	    	 d.setDia_semana(dia_semana);

                Professor p = new Professor();
                p.setCodigo(Integer.parseInt(professorCodigo));
                p = buscarProfessor(p);
                d.setProfessor(p);
            }

            if (cmd.contains("Cadastrar")) {
                saida = cadastrarDisciplina(d);
                d = null;
            }
            if (cmd.contains("Alterar")) {
                saida = alterarDisciplina(d);
                d = null;
            }
            if (cmd.contains("Excluir")) {
                d = buscarDisciplina(d);
                saida = excluirDisciplina(d);
                d = null;
            }
            if (cmd.contains("Buscar")) {
                d = buscarDisciplina(d);
            }
            if (cmd.contains("Listar")) {
                disciplinas = listarDisciplinas();
            }
        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
            model.addAttribute("disciplina", d);
            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("professores", professores);
        }

        return new ModelAndView("disciplina");
    }

    private String cadastrarDisciplina(Disciplina d) throws SQLException, ClassNotFoundException {
        String saida = dDao.iudDisciplina("I", d);
        return saida;
    }

    private String alterarDisciplina(Disciplina d) throws SQLException, ClassNotFoundException {
        String saida = dDao.iudDisciplina("U", d);
        return saida;
    }

    private String excluirDisciplina(Disciplina d) throws SQLException, ClassNotFoundException {
        if (d.getProfessor() != null) {
            d.getProfessor().getCodigo();
        }
        String saida = dDao.iudDisciplina("D", d);
        return saida;
    }

    private Disciplina buscarDisciplina(Disciplina d) throws SQLException, ClassNotFoundException {
        d = dDao.consultar(d);
        return d;
    }

    private List<Disciplina> listarDisciplinas() throws SQLException, ClassNotFoundException {
        List<Disciplina> disciplinas = dDao.listar();
        return disciplinas;
    }

    private Professor buscarProfessor(Professor p) throws SQLException, ClassNotFoundException {
        p = pDao.consultar(p);
        return p;
    }

    private List<Professor> listarProfessores() throws SQLException, ClassNotFoundException {
        List<Professor> professores = pDao.listar();
        return professores;
    }

}