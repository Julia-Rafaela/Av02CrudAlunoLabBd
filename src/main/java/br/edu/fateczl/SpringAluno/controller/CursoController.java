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
import br.edu.fateczl.SpringAluno.persistence.CursoDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;


@Controller
public class CursoController {

	@Autowired
	GenericDao gDao;

	@Autowired
	CursoDao pDao;

	@RequestMapping(name = "curso", value = "/curso", method = RequestMethod.GET)
	public ModelAndView indexGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String cmd = allRequestParam.get("cmd");
		String codigo = allRequestParam.get("codigo");

		if (cmd != null) {
			Curso cs = new Curso();
			cs.setCodigo(Integer.parseInt(codigo));

			String saida = "";
			String erro = "";
			List<Curso> cursos = new ArrayList<>();

			try {
				if (cmd.contains("alterar")) {
					cs = buscarCurso(cs);
				} else if (cmd.contains("excluir")) {
					saida = excluirCurso(cs);
				}

				cursos = listarCursos();

			} catch (SQLException | ClassNotFoundException e) {
				erro = e.getMessage();
			} finally {

				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("curso", cs);
				model.addAttribute("cursos", cursos);
		
			}
		}
		return new ModelAndView("curso");
	}

	@RequestMapping(name = "curso", value = "/curso", method = RequestMethod.POST)
	public ModelAndView indexPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		//passar todos os parametros
		String cmd = allRequestParam.get("botao");
		String codigo = allRequestParam.get("codigo");
        String nome = allRequestParam.get("nome");
        String carga_horaria = allRequestParam.get("carga_horaria");
        String sigla = allRequestParam.get("sigla");
        String nota_enade = allRequestParam.get("nota_enade");

		// Saida
		String saida = "";
		String erro = "";
		Curso cs = new Curso();
		List<Curso> cursos= new ArrayList<>();

		if (!cmd.contains("Listar")) {
			cs.setCodigo(Integer.parseInt(codigo));
		}
		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			//todos os parametros menos o codigo
			cs.setNome(nome);
			cs.setCarga_horaria(carga_horaria);
			cs.setSigla(sigla);
			cs.setNota_enade(Float.parseFloat(nota_enade));
		}

		try {
			if (cmd.contains("Cadastrar")) {
				saida = cadastrarCurso(cs);
				cs = null;
			}
			if (cmd.contains("Alterar")) {
				saida = alterarCurso(cs);
				cs = null;
			}
			if (cmd.contains("Excluir")) {
				saida = excluirCurso(cs);
				cs = null;
			}
			if (cmd.contains("Buscar")) {
				cs = buscarCurso(cs);
			}
			if (cmd.contains("Listar")) {
				cursos = listarCursos();
			}

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("curso", cs);
			model.addAttribute("cursos", cursos);
		}

		return new ModelAndView("curso");
	}

	
	private String cadastrarCurso(Curso cs) throws SQLException, ClassNotFoundException {
		String saida = pDao.iudCurso("I", cs);
		return saida;

	}

	private String alterarCurso(Curso cs) throws SQLException, ClassNotFoundException {
		String saida = pDao.iudCurso("U", cs);
		return saida;

	}

	private String excluirCurso(Curso cs) throws SQLException, ClassNotFoundException {
	    String saida = pDao.iudCurso("D", cs);
	    return saida;
	}

	private Curso buscarCurso(Curso cs) throws SQLException, ClassNotFoundException {
		cs = pDao.consultar(cs);
		return cs;

	}

	//lista pelo sql
	private List<Curso> listarCursos() throws SQLException, ClassNotFoundException {
		List<Curso> cursos = pDao.listar();
		return cursos;
	}

}
