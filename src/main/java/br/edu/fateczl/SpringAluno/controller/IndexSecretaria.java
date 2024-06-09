package br.edu.fateczl.SpringAluno.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexSecretaria {

	@RequestMapping(name = "menuSecretaria", value = "/menuSecretaria", method = RequestMethod.GET)
	public ModelAndView indexGet(ModelMap model) {
		return new ModelAndView("menuSecretaria");
	}

	@RequestMapping(name = "menuSecretaria", value = "/menuSecretaria", method = RequestMethod.POST)
	public ModelAndView indexPost(ModelMap model) {
		return new ModelAndView("menuSecretaria");
	}

}