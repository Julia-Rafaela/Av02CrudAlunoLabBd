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

import br.edu.fateczl.SpringAluno.model.Professor;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;
import br.edu.fateczl.SpringAluno.persistence.ProfessorDao;
@Controller
public class ProfessorController {
	@Autowired
	GenericDao gDao;

	@Autowired
	ProfessorDao pDao;

	@RequestMapping(name = "professor", value = "/professor", method = RequestMethod.GET)
	public ModelAndView indexGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String cmd = allRequestParam.get("cmd");
		String codigo = allRequestParam.get("codigo");

		if (cmd != null) {
			Professor p = new Professor();
			p.setCodigo(Integer.parseInt(codigo));

			String saida = "";
			String erro = "";
			List<Professor> professores = new ArrayList<>();

			try {
				if (cmd.contains("alterar")) {
					p = buscarProfessor(p);
				} else if (cmd.contains("excluir")) {
					saida = excluirProfessor(p);
				}

				professores = listarProfessores();

			} catch (SQLException | ClassNotFoundException e) {
				erro = e.getMessage();
			} finally {

				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("professor", p);
				model.addAttribute("professores", professores);
		
			}
		}
		return new ModelAndView("professor");
	}

	@RequestMapping(name = "professor", value = "/professor", method = RequestMethod.POST)
	public ModelAndView indexPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		//passar todos os parametros
		String cmd = allRequestParam.get("botao");
		String codigo = allRequestParam.get("codigo");
        String nome = allRequestParam.get("nome");

		// Saida
		String saida = "";
		String erro = "";
		Professor p = new Professor();
		List<Professor> professores= new ArrayList<>();

		if (!cmd.contains("Listar")) {
			p.setCodigo(Integer.parseInt(codigo));
		}
		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			
			p.setNome(nome);
		}

		try {
			if (cmd.contains("Cadastrar")) {
				saida = cadastrarProfessor(p);
				p = null;
			}
			if (cmd.contains("Alterar")) {
				saida = alterarProfessor(p);
				p = null;
			}
			if (cmd.contains("Excluir")) {
				saida = excluirProfessor(p);
				p = null;
			}
			if (cmd.contains("Buscar")) {
				p = buscarProfessor(p);
			}
			if (cmd.contains("Listar")) {
				professores = listarProfessores();
			}

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("professor", p);
			model.addAttribute("professores", professores);
		}

		return new ModelAndView("professor");
	}

	
	private String cadastrarProfessor(Professor p) throws SQLException, ClassNotFoundException {
		String saida = pDao.iudProfessor("I", p);
		return saida;

	}

	private String alterarProfessor(Professor p) throws SQLException, ClassNotFoundException {
		String saida = pDao.iudProfessor("U", p);
		return saida;

	}

	private String excluirProfessor(Professor p) throws SQLException, ClassNotFoundException {
	    String saida = pDao.iudProfessor("D", p);
	    return saida;
	}

	private Professor buscarProfessor(Professor p) throws SQLException, ClassNotFoundException {
		p = pDao.consultar(p);
		return p;

	}

	//lista pelo sql
	private List<Professor> listarProfessores() throws SQLException, ClassNotFoundException {
		List<Professor> professores = pDao.listar();
		return professores;
	}

}
