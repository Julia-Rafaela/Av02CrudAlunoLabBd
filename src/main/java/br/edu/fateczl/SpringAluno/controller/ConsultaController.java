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
import br.edu.fateczl.SpringAluno.model.ConsultarChamada;
import br.edu.fateczl.SpringAluno.persistence.AlunoDao;
import br.edu.fateczl.SpringAluno.persistence.ConsultaChamadaDao;

@Controller
public class ConsultaController {

	 @Autowired
	    AlunoDao aDao;
	
	
    @Autowired
    ConsultaChamadaDao ccDao;
    @RequestMapping(value = "/consulta", method = RequestMethod.GET)
    public ModelAndView consultaGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) throws ClassNotFoundException, SQLException {

        String saida = "";
        String erro = "";
        List<ConsultarChamada> consultas = new ArrayList<>();
        List<Aluno> alunos = new ArrayList<>();
        alunos = listarAlunos();

        model.addAttribute("saida", saida);
        model.addAttribute("erro", erro);
        model.addAttribute("alunos", alunos);
        model.addAttribute("consultas", consultas);

        return new ModelAndView("consulta");
    }

    @RequestMapping(value = "/consulta", method = RequestMethod.POST)
    public ModelAndView consultaPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        String cmd = allRequestParam.get("botao");
        String aluno = allRequestParam.get("aluno");
        String saida = "";
        String erro = "";
        List<ConsultarChamada> consultas = new ArrayList<>();
        List<Aluno> alunos = new ArrayList<>();

        try {
        	
        	alunos = listarAlunos();
        	
            if (cmd != null && cmd.contains("Listar")) {
                consultas = listarChamadas(aluno);
            }

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
            model.addAttribute("alunos", alunos);
            model.addAttribute("consultas", consultas);
        }

        return new ModelAndView("consulta");
    }


    private List<ConsultarChamada> listarChamadas(String aluno) throws SQLException, ClassNotFoundException {
        List<ConsultarChamada> consultas = ccDao.listarChamadas(aluno);
        return consultas;
    }
    
    private List<Aluno> listarAlunos() throws SQLException, ClassNotFoundException {
        List<Aluno> alunos = aDao.listar();
        return alunos;
    }
}

