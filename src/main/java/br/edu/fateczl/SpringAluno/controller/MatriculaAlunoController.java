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
import br.edu.fateczl.SpringAluno.model.Chamada;
import br.edu.fateczl.SpringAluno.model.Disciplina;
import br.edu.fateczl.SpringAluno.model.MatriculaAluno;
import br.edu.fateczl.SpringAluno.model.Notas;
import br.edu.fateczl.SpringAluno.model.Professor;
import br.edu.fateczl.SpringAluno.persistence.AlunoDao;
import br.edu.fateczl.SpringAluno.persistence.ChamadaDao;
import br.edu.fateczl.SpringAluno.persistence.DisciplinaDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;
import br.edu.fateczl.SpringAluno.persistence.MatriculaAlunoDao;
import br.edu.fateczl.SpringAluno.persistence.NotaDao;
import br.edu.fateczl.SpringAluno.persistence.ProfessorDao;

@Controller
public class MatriculaAlunoController {
	    @Autowired
	    GenericDao gDao;

	    @Autowired
	    ProfessorDao pDao;

	    @Autowired
	    MatriculaAlunoDao maDao;
	    
	    @Autowired
	    ChamadaDao cDao;
	    
	    @Autowired
	    NotaDao nDao;
	    
	    @Autowired
	    DisciplinaDao dDao;
	    
	    @Autowired
	    AlunoDao aDao;
	    
	    @RequestMapping(name = "", value = "/matriculaAluno", method = RequestMethod.GET)
	    public ModelAndView indexGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

	    	  String cpf = allRequestParam.get("cpf");
	    	  String disciplina = allRequestParam.get("CodigoDisciplina");
	    	  
	    	  List<MatriculaAluno> historicosM = new ArrayList<>();
	          List<Disciplina> disciplinas = new ArrayList<>();
	          List<Professor> professores = new ArrayList<>();
	          List<Chamada> chamadas = new ArrayList<>();
	          List<Notas> notass = new ArrayList<>();
	          List<Aluno> alunos = new ArrayList<>();
	          
	          String erro = "";

	          try {
	              historicosM = maDao.listarMatriculaAluno(cpf);
	              disciplinas = dDao.listar();
	              professores = pDao.listar();
	              chamadas = cDao.listarChamada(disciplina);
	              alunos = aDao.listar();
	          } catch (SQLException | ClassNotFoundException e) {
	              erro = e.getMessage();
	          }

	          model.addAttribute("historicosM", historicosM);
	          model.addAttribute("disciplinas", disciplinas);
	          model.addAttribute("notass", notass);
	          model.addAttribute("chamadas", chamadas);
	          model.addAttribute("professores", professores);
	          model.addAttribute("alunos", alunos);
	          model.addAttribute("erro", erro);

	          return new ModelAndView("matriculaAluno");
	    }

	    @RequestMapping(name = "matriculaAlunoo", value = "/matriculaAluno", method = RequestMethod.POST)
	    public ModelAndView indexPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

	    	String alunoCpf = allRequestParam.get("aluno");
	    	String disciplina = allRequestParam.get("CodigoDisciplina");
	    	
	    	 List<MatriculaAluno> historicosM = new ArrayList<>();
	          List<Disciplina> disciplinas = new ArrayList<>();
	          List<Professor> professores = new ArrayList<>();
	          List<Chamada> chamadas = new ArrayList<>();
	          List<Notas> notass = new ArrayList<>();
	          List<Aluno> alunos = new ArrayList<>();
	         String erro = "";

	         try {
	        	 historicosM = maDao.listarMatriculaAluno(alunoCpf);
	              disciplinas = dDao.listar();
	              professores = pDao.listar();
	              chamadas = cDao.listarChamada(disciplina);
	              alunos = aDao.listar();
	         } catch (SQLException | ClassNotFoundException e) {
	             erro = e.getMessage();
	         }

	         model.addAttribute("historicosM", historicosM);
	          model.addAttribute("disciplinas", disciplinas);
	          model.addAttribute("notass", notass);
	          model.addAttribute("chamadas", chamadas);
	          model.addAttribute("professores", professores);
	          model.addAttribute("alunos", alunos);
	          model.addAttribute("erro", erro);

	         return new ModelAndView("matriculaAluno");
	     
	    }
}
