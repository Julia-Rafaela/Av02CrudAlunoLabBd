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
import br.edu.fateczl.SpringAluno.model.Dispensa;
import br.edu.fateczl.SpringAluno.model.Matricula;
import br.edu.fateczl.SpringAluno.persistence.DispensaDao;
import br.edu.fateczl.SpringAluno.persistence.GenericDao;
import br.edu.fateczl.SpringAluno.persistence.MatriculaDao;
import br.edu.fateczl.SpringAluno.persistence.SolicitacaoDao;

@Controller
public class SolicitacaoController {

	    @Autowired
	    GenericDao gDao;

	    @Autowired
	    SolicitacaoDao sDao;

	    @Autowired
	    DispensaDao dsDao;

	    @Autowired
	    MatriculaDao mDao;

	    @RequestMapping(value = "/solicitacao", method = RequestMethod.GET)
	    public ModelAndView solicitacaoGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
	        String cmd = allRequestParam.get("botao");
	        String erro = "";
	        List<Dispensa> dispensas = new ArrayList<>();
	        List<Matricula> matriculas = new ArrayList<>();
	        try {
	            if (cmd != null && "Solicitações".contains(cmd)) {
	                dispensas = listarDispensas();
	            }
	            if (cmd != null && "Listar".contains(cmd)) {
	                matriculas = listarMatriculas();
	            }         
	        } catch (SQLException | ClassNotFoundException e) {
	            erro = e.getMessage();
	        }
	        model.addAttribute("dispensas", dispensas);
	        model.addAttribute("matriculas", matriculas);
	        model.addAttribute("erro", erro);
	        return new ModelAndView("solicitacao", model);
	    }

	    @RequestMapping(value = "/solicitacao", method = RequestMethod.POST)
	    public ModelAndView solicitacaoPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
	        String cmd = allRequestParam.get("botao");
	        String saida = "";
	        String erro = "";
	        List<Dispensa> dispensas = new ArrayList<>();
	        List<Matricula> matriculas = new ArrayList<>();
	        
	        try {
	            if (cmd != null && cmd.contains("Aceitar")) {
	                String codigo = allRequestParam.get("codigo");
	                if (codigo != null && !codigo.isEmpty()) {
	                    int codigoSolicitacao = Integer.parseInt(codigo);
	                    Dispensa ds = new Dispensa();
	                    ds.setCodigo(codigoSolicitacao);
	                    saida = aceitarSolicitacao(ds);
	                }
	            } else if ("Solicitações".contains(cmd)) {
	                dispensas = listarDispensas();
	            } else if ("Listar".contains(cmd)) {
	                matriculas = listarMatriculas();
	            }
	        } catch (SQLException | ClassNotFoundException | IllegalArgumentException e) {
	            e.printStackTrace();
	            erro = e.getMessage();
	        }

	        // Atualizar o modelo
	        model.addAttribute("dispensas", dispensas);
	        model.addAttribute("matriculas", matriculas);
	        model.addAttribute("erro", erro);
	        model.addAttribute("saida", saida);

	        return new ModelAndView("solicitacao", model);
	    }

	    @RequestMapping(value = "/aceitarSolicitacao", method = RequestMethod.POST)
	    public ModelAndView aceitarSolicitacao(@RequestParam("codigo") int codigo, @RequestParam("aluno") String cpf) {
	        ModelAndView modelAndView = new ModelAndView("redirect:/solicitacao");
	        try {
	            Dispensa ds = new Dispensa();
	            ds.setCodigo(codigo);
	            Aluno a = new Aluno(); // Supondo que você tenha uma classe Aluno com um método setCodigo
	            a.setCpf(cpf);
	            ds.setAluno(a);
	            String saida = aceitarSolicitacao(ds);
	            modelAndView.addObject("saida", saida);
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	            modelAndView.addObject("erro", e.getMessage());
	        }
	        return modelAndView;
	    }

	    private String aceitarSolicitacao(Dispensa ds) {
	        try {
	            String resultado = sDao.solicitacao("U", ds.getCodigo(), ds.getAluno().getCpf());
	            
	            if (resultado.equals("Solicitação atualizada com sucesso.")) {
	                return "Operação de aceitar solicitação concluída com sucesso.";
	            } else {
	                return resultado;
	            }
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	            return "Erro ao aceitar solicitação: " + e.getMessage();
	        }
	    }

	    private List<Dispensa> listarDispensas() throws SQLException, ClassNotFoundException {
	        return sDao.listarSolicitacao();
	    }

	    private List<Matricula> listarMatriculas() throws SQLException, ClassNotFoundException {
	        return mDao.listar();
	    }
}