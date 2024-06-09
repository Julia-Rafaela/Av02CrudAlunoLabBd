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
import br.edu.fateczl.SpringAluno.model.Dispensa;
import br.edu.fateczl.SpringAluno.persistence.AlunoDao;
import br.edu.fateczl.SpringAluno.persistence.DisciplinaDao;
import br.edu.fateczl.SpringAluno.persistence.DispensaDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;

@Controller
public class SolicitarDispensaController {
	@Autowired
	GenericDao gDao;

	@Autowired
	DisciplinaDao dDao;

	@Autowired
	DispensaDao dsDao;

	@Autowired
	AlunoDao aDao;

	@RequestMapping(name = "solicitarDispensa", value = "/solicitarDispensa", method = RequestMethod.GET)
	public ModelAndView solicitarDispensaGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		List<Aluno> alunos = new ArrayList<>();
		List<Disciplina> disciplinas = new ArrayList<>();

		try {
			alunos = listarAlunos();
			disciplinas = listarDisciplinas();
		} catch (SQLException | ClassNotFoundException e) {
			model.addAttribute("erro", e.getMessage());
		}

		model.addAttribute("alunos", alunos);
		model.addAttribute("disciplinas", disciplinas);

		return new ModelAndView("solicitarDispensa", model);
	}

	@RequestMapping(name = "solicitarDispensa", value = "/solicitarDispensa", method = RequestMethod.POST)
	public ModelAndView solicitarDispensaPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String cmd = allRequestParam.get("botao");
		String data = allRequestParam.get("data_s");
		String alunoCpf = allRequestParam.get("cpf");
		String disciplinacodigo = allRequestParam.get("disciplina");
		String instituicao = allRequestParam.get("instituicao");
		Dispensa ds = new Dispensa();

		String saida = "";
		String erro = "";

		List<Dispensa> dispensas = new ArrayList<>();
		List<Aluno> alunos = new ArrayList<>();
		List<Disciplina> disciplinas = new ArrayList<>();

		try {
			alunos = listarAlunos();
			disciplinas = listarDisciplinas();

			if (cmd != null && cmd.equals("Solicitar")) {
				if (data == null || alunoCpf == null || disciplinacodigo == null || instituicao == null) {
					throw new IllegalArgumentException("Todos os campos são obrigatórios.");
				}

				ds.setData_s(data);
				ds.setInstituicao(instituicao);

				Aluno aluno = new Aluno();
				aluno.setCpf(alunoCpf);
				aluno = buscarAluno(aluno);
				ds.setAluno(aluno);
	            int codigoDisciplina = Integer.parseInt(disciplinacodigo);
	            if (codigoDisciplina <= 0) {
	                throw new IllegalArgumentException("Código de disciplina inválido.");
	            }
	            
	            Disciplina disciplina = new Disciplina();
	            disciplina.setCodigo(codigoDisciplina);
	            disciplina = buscarDisciplina(disciplina);

	            if (disciplina == null) {
	                throw new IllegalArgumentException("Disciplina não encontrada.");
	            }

	            ds.setDisciplina(disciplina);

	            saida = cadastrarDispensa(ds);
	            ds = null;

	        } else if (cmd != null && cmd.equals("Listar")) {
	            dispensas = listarDispensa(alunoCpf);
	        }

	    } catch (SQLException | ClassNotFoundException | IllegalArgumentException e) {
	        e.printStackTrace();
	        erro = e.getMessage();
	    } finally {
	        model.addAttribute("saida", saida);
	        model.addAttribute("erro", erro);
	        model.addAttribute("dispensa", ds);
	        model.addAttribute("dispensas", dispensas);
	        model.addAttribute("alunos", alunos);
	        model.addAttribute("disciplinas", disciplinas);
	    }

	    return new ModelAndView("solicitarDispensa");
	}

	private String cadastrarDispensa(Dispensa ds) throws SQLException, ClassNotFoundException {
		String saida = dsDao.iudDispensa("I", ds);
		return saida;
	}

	private List<Dispensa> listarDispensa(String aluno) throws SQLException, ClassNotFoundException {
		List<Dispensa> dispensas = dsDao.listarDispensa(aluno);
		return dispensas;
	}

	private Aluno buscarAluno(Aluno a) throws SQLException, ClassNotFoundException {
		a = aDao.consultar(a);
		return a;
	}

	private List<Aluno> listarAlunos() throws SQLException, ClassNotFoundException {
		List<Aluno> alunos = aDao.listar();
		return alunos;
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
