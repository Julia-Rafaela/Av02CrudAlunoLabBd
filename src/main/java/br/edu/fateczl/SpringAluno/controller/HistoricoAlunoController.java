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
import br.edu.fateczl.SpringAluno.model.HistoricoAluno;
import br.edu.fateczl.SpringAluno.model.Matricula;
import br.edu.fateczl.SpringAluno.persistence.AlunoDao;
import br.edu.fateczl.SpringAluno.persistence.CursoDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;
import br.edu.fateczl.SpringAluno.persistence.HistoricoAlunoDao;
import br.edu.fateczl.SpringAluno.persistence.MatriculaDao;

@Controller
public class HistoricoAlunoController {
	    @Autowired
	    GenericDao gDao;

	    @Autowired
	    AlunoDao aDao;

	    @Autowired
	    HistoricoAlunoDao hDao;
	    
	    @Autowired
	    MatriculaDao mDao;
	    
	    @Autowired
	    CursoDao cDao;
	    @RequestMapping(name = "historicoAluno", value = "/historicoAluno", method = RequestMethod.GET)
	    public ModelAndView indexGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

	    	  String cpf = allRequestParam.get("cpf");
	    	  List<HistoricoAluno> historicos = new ArrayList<>();
	          List<Aluno> alunos = new ArrayList<>();
	          List<Curso> cursos = new ArrayList<>();
	          List<Matricula> matriculas = new ArrayList<>();
	          String erro = "";

	          try {
	              historicos = hDao.listarHistoricoAluno(cpf);
	              alunos = aDao.listar();
	              cursos = cDao.listar();
	              matriculas = mDao.listar();
	          } catch (SQLException | ClassNotFoundException e) {
	              erro = e.getMessage();
	          }

	          model.addAttribute("historicos", historicos);
	          model.addAttribute("alunos", alunos);
	          model.addAttribute("cursos", cursos);
	          model.addAttribute("matriculas", matriculas);
	          model.addAttribute("erro", erro);

	          return new ModelAndView("historicoAluno");
	    }

	    @RequestMapping(name = "historicoAluno", value = "/historicoAluno", method = RequestMethod.POST)
	    public ModelAndView indexPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

	    	String alunoCpf = allRequestParam.get("aluno");
	    	 List<HistoricoAluno> historicos = new ArrayList<>();
	         List<Aluno> alunos = new ArrayList<>();
	         List<Curso> cursos = new ArrayList<>();
	         List<Matricula> matriculas = new ArrayList<>();
	         String erro = "";

	         try {
	             historicos = hDao.listarHistoricoAluno(alunoCpf);
	             alunos = aDao.listar();
	             cursos = cDao.listar();
	             matriculas = mDao.listar();
	         } catch (SQLException | ClassNotFoundException e) {
	             erro = e.getMessage();
	         }

	         model.addAttribute("historicos", historicos);
	         model.addAttribute("alunos", alunos);
	         model.addAttribute("cursos", cursos);
	         model.addAttribute("matriculas", matriculas);
	         model.addAttribute("erro", erro);

	         return new ModelAndView("historicoAluno");
	     
	    }
}

