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
import br.edu.fateczl.SpringAluno.persistence.AlunoDao;
import br.edu.fateczl.SpringAluno.persistence.ChamadaDao;
import br.edu.fateczl.SpringAluno.persistence.DisciplinaDao;

@Controller
public class ChamadaController {

    @Autowired
    AlunoDao aDao;

    @Autowired
    DisciplinaDao dDao;

    @Autowired
    ChamadaDao cmDao;

    @RequestMapping(value = "/chamada", method = RequestMethod.GET)
    public ModelAndView ChamadaGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        String cmd = allRequestParam.get("cmd");
        String codigo = allRequestParam.get("codigo");
        String disciplina = allRequestParam.get("disciplina");

        if (cmd != null) {
            Chamada cm = new Chamada();
            cm.setCodigo(Integer.parseInt(codigo));
        }

        String saida = "";
        String erro = "";
        List<Chamada> chamadas = new ArrayList<>();
        List<Aluno> alunos = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();

        try {
            chamadas = listarChamadas(disciplina);
            alunos = listarAlunos();
            disciplinas = listarDisciplinas();

            if (cmd != null && cmd.contains("alterar") && codigo != null) {
                chamadas = buscarChamada(codigo);
            }

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
            model.addAttribute("chamadas", chamadas);
            model.addAttribute("alunos", alunos);
            model.addAttribute("disciplinas", disciplinas);
        }

        return new ModelAndView("chamada");
    }

    @RequestMapping(value = "/chamada", method = RequestMethod.POST)
    public ModelAndView indexPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        String cmd = allRequestParam.get("botao");
        String data = allRequestParam.get("data");
        String codigo = allRequestParam.get("codigo");
        String aluno = allRequestParam.get("aluno");
        String disciplina = allRequestParam.get("disciplina");
        String falta = allRequestParam.get("falta");

        String saida = "";
        String erro = "";
        Chamada cm = new Chamada();

        List<Chamada> chamadas = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();
    	List<Aluno> alunos = new ArrayList<>();

        if (cmd != null && !cmd.contains("Realizar Chamada")) {
            cm.setData(data);
        }

        try {

            disciplinas = listarDisciplinas();
            alunos = listarAlunos();


            if (cmd != null && cmd.equals("Cadastrar")) {
            	    // Iterar sobre os parâmetros da requisição para identificar os campos de falta
            	    for (Map.Entry<String, String> entry : allRequestParam.entrySet()) {
            	        String key = entry.getKey();
            	        String value = entry.getValue();
            	        // Verificar se o parâmetro começa com "falta" (representando os campos de falta)
            	        if (key.startsWith("falta")) {
            	            // Extrair o índice da chamada do nome do parâmetro
            	            String chamadaIndex = key.substring(5); // Remover o "falta" do início do nome do parâmetro
            	            
            	                // Criar uma nova chamada com os dados fornecidos
            	                Chamada novaChamada = new Chamada();
            	                novaChamada.setData(data);
            	                novaChamada.setCodigo(Integer.parseInt(codigo));

            	                String alunoKey = "aluno" + chamadaIndex;
            	                String alunoValue = allRequestParam.get(alunoKey);
            	                if (alunoValue != null && !alunoValue.isEmpty()) {
            	                    // Extrair o CPF do aluno do valor do parâmetro
            	                    String alunoCpf = alunoValue;
            	                    // Criar um novo aluno com o CPF fornecido
            	                    Aluno a = new Aluno();
            	                    a.setCpf(alunoCpf);
            	                    // Definir o aluno na chamada
            	                    novaChamada.setAluno(a);
            	                

            	                Disciplina d = new Disciplina();
            	                d.setCodigo(Integer.parseInt(disciplina));
            	                d = buscarDisciplina(d);
            	                novaChamada.setDisciplina(d);

            	                novaChamada.setFalta(Integer.parseInt(value));

            	                // Cadastrar a nova chamada
            	                try {
            	                    String resultadoCadastro = cadastrarChamada(novaChamada);
            	                    saida += resultadoCadastro + "<br>";
            	                } catch (SQLException | ClassNotFoundException e) {
            	                    saida += "Erro ao cadastrar chamada: " + e.getMessage() + "<br>";
            	                }
            	            }
            	        }
            	    }
            	
        
        } else if (cmd != null && cmd.equals("Realizar Chamada")) {
                chamadas = listarChamadas(disciplina);
                
            } else if (cmd != null && cmd.equals("Alterar") && codigo != null) {
            
            	 // Iterar sobre os parâmetros da requisição para identificar os campos de falta
        	    for (Map.Entry<String, String> entry : allRequestParam.entrySet()) {
        	        String key = entry.getKey();
        	        String value = entry.getValue();
        	        // Verificar se o parâmetro começa com "falta" (representando os campos de falta)
        	        if (key.startsWith("falta")) {
        	            // Extrair o índice da chamada do nome do parâmetro
        	            String chamadaIndex = key.substring(5); // Remover o "falta" do início do nome do parâmetro
        	            
        	          
        	                // Criar uma nova chamada com os dados fornecidos
        	                Chamada novaChamada = new Chamada();
        	                novaChamada.setData(data);
        	                novaChamada.setCodigo(Integer.parseInt(codigo));

        	                String alunoKey = "aluno" + chamadaIndex;
        	                String alunoValue = allRequestParam.get(alunoKey);
        	                if (alunoValue != null && !alunoValue.isEmpty()) {
        	                    // Extrair o CPF do aluno do valor do parâmetro
        	                    String alunoCpf = alunoValue;
        	                    // Criar um novo aluno com o CPF fornecido
        	                    Aluno a = new Aluno();
        	                    a.setCpf(alunoCpf);
        	                    // Definir o aluno na chamada
        	                    novaChamada.setAluno(a);
        	                

        	                Disciplina d = new Disciplina();
        	                d.setCodigo(Integer.parseInt(disciplina));
        	                d = buscarDisciplina(d);
        	                novaChamada.setDisciplina(d);

        	                novaChamada.setFalta(Integer.parseInt(value));

        	                // Cadastrar a nova chamada
        	                try {
        	                    String resultadoCadastro = alterarChamada(novaChamada);
        	                    saida += resultadoCadastro + "<br>";
        	                } catch (SQLException | ClassNotFoundException e) {
        	                    saida += "Erro ao cadastrar chamada: " + e.getMessage() + "<br>";
        	                }
        	            }
        	        }
        	    }
            
            } else if (cmd != null && cmd.equals("Buscar") && codigo != null) {
            	   chamadas = buscarChamada(codigo);
            }
                    

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();

        } finally {

            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
            model.addAttribute("chamadas", chamadas);
            model.addAttribute("alunos", alunos);
            model.addAttribute("disciplinas", disciplinas);
        }

        return new ModelAndView("chamada");

    }

    private String cadastrarChamada(Chamada cm) throws SQLException, ClassNotFoundException {
        String saida = cmDao.iudChamada("I", cm);
        return saida;

    }

    private List<Chamada> listarChamadas(String disciplina) throws SQLException, ClassNotFoundException {
        List<Chamada> chamadas = cmDao.listarChamada(disciplina);
        return chamadas;
    }

    private List<Chamada> buscarChamada(String codigo) throws SQLException, ClassNotFoundException {
        List<Chamada> chamadas = cmDao.consultar(codigo);
        return chamadas;
    }

    private Aluno buscarAluno(Aluno a) throws SQLException, ClassNotFoundException {
        a = aDao.consultar(a);
        return a;

    }

    private String alterarChamada(Chamada cm) throws SQLException, ClassNotFoundException {
        if (cm == null) {
            return "Chamada não encontrada.";
        }

        String saida = cmDao.iudChamada("U", cm);
        return saida;
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