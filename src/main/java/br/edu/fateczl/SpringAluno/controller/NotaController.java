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
import br.edu.fateczl.SpringAluno.model.Disciplina;
import br.edu.fateczl.SpringAluno.model.Notas;
import br.edu.fateczl.SpringAluno.persistence.AlunoDao;
import br.edu.fateczl.SpringAluno.persistence.DisciplinaDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;
import br.edu.fateczl.SpringAluno.persistence.NotaDao;

@Controller
public class NotaController {

    @Autowired
    GenericDao gDao;

    @Autowired
    AlunoDao aDao;

    @Autowired
    DisciplinaDao dDao;
    
    @Autowired
    NotaDao nDao;

    @RequestMapping(name = "nota", value = "/nota", method = RequestMethod.GET)
    public ModelAndView indexGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
    	 String cmd = allRequestParam.get("cmd");
    	    String disciplina = allRequestParam.get("disciplina");

    	    Notas n = new Notas();
    	    String saida = "";
    	    String erro = "";
    	    List<Notas> notas = new ArrayList<>();
    	    List<Aluno> alunos = new ArrayList<>();
    	    List<Disciplina> disciplinas = new ArrayList<>();

    	    try {
    	        if (cmd != null && cmd.contains("alterar")) {
    	            String codigo = allRequestParam.get("codigo");
    	            if (codigo != null && !codigo.isEmpty()) {
    	                n = buscarNota(Integer.parseInt(codigo));
    	            }
    	        }

    	        alunos = listarAlunos();
    	        disciplinas = listarDisciplinas();
    	        notas = listarNotas(disciplina); 

    	    } catch (NumberFormatException e) {
    	        erro = "Erro ao converter dados numéricos.";
    	    } catch (SQLException | ClassNotFoundException e) {
    	        erro = e.getMessage();
    	    } finally {
    	        model.addAttribute("saida", saida);
    	        model.addAttribute("erro", erro);
    	        model.addAttribute("nota", n);
    	        model.addAttribute("notas", notas);
    	        model.addAttribute("alunos", alunos);
    	        model.addAttribute("disciplinas", disciplinas);
    	    }

    	    return new ModelAndView("nota");
    }
    
    @RequestMapping(value = "/nota", method = RequestMethod.POST)
    public ModelAndView indexPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
    	  String cmd = allRequestParam.get("botao");
    	    String aluno = allRequestParam.get("aluno");
    	    String disciplina = allRequestParam.get("disciplina");
    	    String nota1 = allRequestParam.get("nota1");
    	    String nota2 = allRequestParam.get("nota2");
    	    String nota_recuperacao = allRequestParam.get("nota_recuperacao"); // Adicionar a recuperação

    	    String saida = "";
    	    String erro = "";
    	    Notas n = new Notas();

    	    List<Notas> notas = new ArrayList<>();
    	    List<Aluno> alunos = new ArrayList<>();
    	    List<Disciplina> disciplinas = new ArrayList<>();

    	    try {
    	        alunos = listarAlunos();
    	        disciplinas = listarDisciplinas();

    	        if (aluno != null && !aluno.isEmpty() && disciplina != null && !disciplina.isEmpty() && nota1 != null
    	                && !nota1.isEmpty() && nota2 != null && !nota2.isEmpty()) {
    	            n.setAluno(new Aluno());
    	            n.getAluno().setCpf(aluno);
    	            n.setDisciplina(new Disciplina());

    	            try {
    	                n.getDisciplina().setCodigo(Integer.parseInt(disciplina));
    	            } catch (NumberFormatException ex) {
    	                throw new IllegalArgumentException("Valor inválido para disciplina.");
    	            }

    	            try {
    	                if (nota1 != null && !nota1.isEmpty()) {
    	                    n.setNota1(nota1);
    	                }
    	                if (nota2 != null && !nota2.isEmpty()) {
    	                    n.setNota2(nota2);
    	                }
    	                if (nota_recuperacao != null && !nota_recuperacao.isEmpty()) {
    	                    n.setNota_recuperacao(nota_recuperacao);
    	                }
    	            } catch (NumberFormatException ex) {
    	                throw new IllegalArgumentException("Valor inválido para nota final.");
    	            }

    	            if (cmd != null && cmd.contains("Cadastrar")) {
    	                saida = cadastrarNota(n);
    	                n = null;
    	            } else if (cmd != null && cmd.contains("Alterar")) {
    	                saida = alterarNota(n); // Chamar o método para alterar as notas
    	                n = null;
    	            }
    	        } 
    	        if (cmd != null && cmd.contains("Listar")) {
    	            notas = listarNotas(disciplina);
    	        }
    	    } catch (NumberFormatException e) {
    	        erro = "Erro ao converter dados numéricos.";
    	    } catch (SQLException | ClassNotFoundException e) {
    	        erro = e.getMessage();
    	    } catch (IllegalArgumentException e) {
    	        erro = e.getMessage();
    	    } finally {
    	        model.addAttribute("saida", saida);
    	        model.addAttribute("erro", erro);
    	        model.addAttribute("nota", n);
    	        model.addAttribute("notas", notas);
    	        model.addAttribute("alunos", alunos);
    	        model.addAttribute("disciplinas", disciplinas);
    	    }

    	    return new ModelAndView("nota");
    }

    private String alterarNota(Notas n) throws SQLException, ClassNotFoundException {
        String saida = nDao.iudNotas("U", n);
        return saida;
    }

    private String cadastrarNota(Notas n) throws SQLException, ClassNotFoundException {
        String saida = nDao.iudNotas("I", n);
        return saida;
    }

    private List<Disciplina> listarDisciplinas() throws SQLException, ClassNotFoundException {
        List<Disciplina> disciplinas = dDao.listar();
        return disciplinas;
    }

    private List<Aluno> listarAlunos() throws SQLException, ClassNotFoundException {
        List<Aluno> alunos = aDao.listar();
        return alunos;
    }

    private List<Notas> listarNotas(String disciplina) throws SQLException, ClassNotFoundException {
        List<Notas> notas = nDao.listarNotas(disciplina);
        return notas;
    }

    private Notas buscarNota(int codigo) throws SQLException, ClassNotFoundException {
        Notas n = new Notas();
        n.setCodigo(codigo);
        return nDao.consultar(n);
    }
}