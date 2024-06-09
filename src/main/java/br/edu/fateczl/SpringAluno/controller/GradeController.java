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

import br.edu.fateczl.SpringAluno.model.Curso;
import br.edu.fateczl.SpringAluno.model.Disciplina;
import br.edu.fateczl.SpringAluno.model.Grade;
import br.edu.fateczl.SpringAluno.persistence.CursoDao;
import br.edu.fateczl.SpringAluno.persistence.DisciplinaDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;
import br.edu.fateczl.SpringAluno.persistence.GradeDao;

@Controller
public class GradeController {

    @Autowired
    GenericDao gDao;

    @Autowired
    CursoDao cDao;

    @Autowired
    DisciplinaDao dDao;
    
    @Autowired
    GradeDao grDao;
    
    

    @RequestMapping(name = "grade", value = "/grade", method = RequestMethod.GET)
    public ModelAndView GradeGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

    	String cmd = allRequestParam.get("cmd");
		String codigo = allRequestParam.get("codigo");
		String curso = allRequestParam.get("curso");

		Grade g = new Grade();
		String saida = "";
		String erro = "";
		List<Grade> grades = new ArrayList<>();
		List<Curso> cursos = new ArrayList<>();
		List<Disciplina> disciplinas = new ArrayList<>();
		
		if (cmd != null && !cmd.contains("Listar")) {
			g.setCodigo(Integer.parseInt(codigo));
		}
		
		  try {
	            if (cmd != null) {
	                if (cmd.contains("alterar")) {
	                    g = buscarGrade(g);
	                } else if (cmd.contains("excluir")) {
	                    g = buscarGrade(g);
	                    saida = excluirGrade(g);
	                }
	            }

			grades = listarGrades(curso);
			cursos = listarCursos();
			disciplinas = listarDisciplinas();

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {

			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("grade", g);
			model.addAttribute("grades", grades);
			model.addAttribute("cursos", cursos);
			model.addAttribute("disciplinas", disciplinas);

		}

		return new ModelAndView("grade");
	}

	@RequestMapping(name = "grade", value = "/grade", method = RequestMethod.POST)
	public ModelAndView indexPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		// passar todos os parametros
		String cmd = allRequestParam.get("botao");
		String codigo = allRequestParam.get("codigo");
		String curso = allRequestParam.get("curso");
		String disciplina = allRequestParam.get("disciplina");

		// Saida
		String saida = "";
		String erro = "";
		Grade g = new Grade();

		List<Grade> grades = new ArrayList<>();
		List<Curso> cursos = new ArrayList<>();
		List<Disciplina> disciplinas = new ArrayList<>();

		if (cmd != null && !cmd.contains("Listar")) {
			g.setCodigo(Integer.parseInt(codigo));
		}

		try {

			cursos = listarCursos();
			disciplinas = listarDisciplinas();

			if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
				g.setCodigo(Integer.parseInt(codigo));

				Curso c = new Curso();
				c.setCodigo((Integer.parseInt(curso)));
				c = buscarCurso(c);
				g.setCurso(c);
				
				Disciplina d = new Disciplina();
				d.setCodigo((Integer.parseInt(disciplina)));
				d = buscarDisciplina(d);
				g.setDisciplina(d);
				
			}
			if (cmd.contains("Cadastrar")) {
				saida = cadastrarGrade(g);
				g = null;
			}
			if (cmd.contains("Alterar")) {
                saida = alterarGrade(g);
                g = null;
            }
			
			if (cmd.contains("Excluir")) {
				saida = excluirGrade(g);
				g = null;
			}
			if (cmd != null && cmd.contains("Listar")) {
				grades = listarGrades(curso);
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();

		} finally {

			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("grade", g);
			model.addAttribute("grades", grades);
			model.addAttribute("cursos", cursos);
			model.addAttribute("disciplinas", disciplinas);

		}

		return new ModelAndView("grade");

	}

	private String cadastrarGrade(Grade g) throws SQLException, ClassNotFoundException {
		String saida = grDao.iudGrade("I", g);
		return saida;

	}
	
	 private String alterarGrade(Grade g) throws SQLException, ClassNotFoundException {
	        String saida = grDao.iudGrade("U", g);
	        return saida;
	    }

	private String excluirGrade(Grade g) throws SQLException, ClassNotFoundException {
		String saida = grDao.iudGrade("D", g);
		return saida;
	}

	private List<Grade> listarGrades(String codigo) throws SQLException, ClassNotFoundException {
		List<Grade> grades = new ArrayList<>();
		grades = grDao.listarGrade(codigo);
		return grades;
	}
	
    private Grade buscarGrade(Grade g) throws SQLException, ClassNotFoundException {
        g = grDao.consultar(g);
        return g;
    }

	private Curso buscarCurso(Curso c) throws SQLException, ClassNotFoundException {
		c = cDao.consultar(c);
		return c;

	}

	private List<Curso> listarCursos() throws SQLException, ClassNotFoundException {
		List<Curso> cursos = cDao.listar();
		return cursos;
	}
	
	private Disciplina buscarDisciplina(Disciplina d) throws SQLException, ClassNotFoundException {
		d = dDao.consultar(d);
		return d;

	}

	private List<Disciplina> listarDisciplinas() throws SQLException, ClassNotFoundException {
		List<Disciplina> disciplinas = dDao.listar();
		return disciplinas;
	}

}
