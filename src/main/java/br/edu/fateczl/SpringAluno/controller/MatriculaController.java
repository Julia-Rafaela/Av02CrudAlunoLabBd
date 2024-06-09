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
import br.edu.fateczl.SpringAluno.model.Matricula;
import br.edu.fateczl.SpringAluno.model.Notas;
import br.edu.fateczl.SpringAluno.persistence.AlunoDao;
import br.edu.fateczl.SpringAluno.persistence.DisciplinaDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;
import br.edu.fateczl.SpringAluno.persistence.MatriculaDao;
import br.edu.fateczl.SpringAluno.persistence.NotaDao;

@Controller
public class MatriculaController {

    @Autowired
    GenericDao gDao;

    @Autowired
    AlunoDao aDao;
    
    @Autowired
    NotaDao nDao;

    @Autowired
    DisciplinaDao dDao;

    @Autowired
    MatriculaDao mDao;
    
    


    @RequestMapping(value = "/matricula", method = RequestMethod.GET)
    public ModelAndView matriculaGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        String cmd = allRequestParam.get("cmd");
        String alunoId = allRequestParam.get("aluno");

        Matricula matricula = new Matricula();
        String saida = "";
        String erro = "";
        List<Matricula> matriculas = new ArrayList<>();
        List<Aluno> alunos = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();
        List<Notas> notas = new ArrayList<>();

        try {
            if (cmd != null && cmd.contains("alterar")) {
                String codigo = allRequestParam.get("codigo");
                if (codigo != null && !codigo.isEmpty()) {
                    matricula = buscarMatricula(Integer.parseInt(codigo));
                }
            }

            matriculas = listarMatriculas(alunoId);
            alunos = listarAlunos();
            disciplinas = listarDisciplinas();
            notas = listarNotas();
        } catch (NumberFormatException e) {
            erro = "Erro ao converter dados numéricos.";
        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
            model.addAttribute("matricula", matricula);
            model.addAttribute("matriculas", matriculas);
            model.addAttribute("alunos", alunos);
            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("notas", notas);
        }

        return new ModelAndView("matricula");
    }

    @RequestMapping(value = "/matricula", method = RequestMethod.POST)
    public ModelAndView matriculaPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        String cmd = allRequestParam.get("botao");
        String alunoId = allRequestParam.get("aluno");
        String disciplinaId = allRequestParam.get("disciplina");
        String data = allRequestParam.get("data_m");
        String codigo = allRequestParam.get("codigo");

        String saida = "";
        String erro = "";
        Matricula matricula = new Matricula();

        List<Matricula> matriculas = new ArrayList<>();
        List<Aluno> alunos = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();
        List<Notas> notas = new ArrayList<>();

        try {
            alunos = listarAlunos();
            disciplinas = listarDisciplinas();
            notas = listarNotas();

            if (alunoId != null && !alunoId.isEmpty() && disciplinaId != null && !disciplinaId.isEmpty()) {
                if (cmd != null && (cmd.contains("Cadastrar") || cmd.contains("Alterar"))) {
                    matricula.setAluno(new Aluno());
                    matricula.getAluno().setCpf(alunoId);
                    matricula.setDisciplina(new Disciplina());

                    try {
                        matricula.getDisciplina().setCodigo(Integer.parseInt(disciplinaId));
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Valor inválido para disciplina.");
                    }

                    matricula.setData_m(data);

                    if (cmd.contains("Cadastrar")) {
                        saida = cadastrarMatricula(matricula);
                        matricula = null;
                    } else if (cmd.contains("Alterar")) {
                        if (codigo != null && !codigo.isEmpty()) {
                            try {
                                matricula.setCodigo(Integer.parseInt(codigo));
                                saida = alterarMatricula(matricula);
                            } catch (NumberFormatException ex) {
                                throw new IllegalArgumentException("Valor inválido para código.");
                            }
                        } else {
                            throw new IllegalArgumentException("Código não fornecido.");
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Aluno ou disciplina não especificados.");
            }

            if (cmd != null && cmd.contains("Listar")) {
                matriculas = listarMatriculas(alunoId);
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
            model.addAttribute("matricula", matricula);
            model.addAttribute("matriculas", matriculas);
            model.addAttribute("alunos", alunos);
            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("notas", notas);
        }

        return new ModelAndView("matricula");
    }

    private List<Notas> listarNotas() throws ClassNotFoundException, SQLException {
    	  List<Notas> notas = nDao.listar();
          return notas;
	}

	private Matricula buscarMatricula(int codigo) throws SQLException, ClassNotFoundException {
        Matricula m = new Matricula();
        m.setCodigo(codigo);
        return mDao.consultar(m);
    }
    private String cadastrarMatricula(Matricula m) throws SQLException, ClassNotFoundException {
        String saida = mDao.iudMatricula("I", m);
        return saida;

    }

    private String alterarMatricula(Matricula m) throws SQLException, ClassNotFoundException {
        String saida = mDao.iudMatricula("U", m);
        return saida;
    }

    private List<Matricula> listarMatriculas(String aluno) throws SQLException, ClassNotFoundException {
        List<Matricula> matriculas = mDao.listarMatricula(aluno);
        return matriculas;
    }


    private List<Aluno> listarAlunos() throws SQLException, ClassNotFoundException {
        List<Aluno> alunos = aDao.listar();
        return alunos;
    }


    private List<Disciplina> listarDisciplinas() throws SQLException, ClassNotFoundException {
        List<Disciplina> disciplinas = dDao.listar();
        return disciplinas;
    }

}