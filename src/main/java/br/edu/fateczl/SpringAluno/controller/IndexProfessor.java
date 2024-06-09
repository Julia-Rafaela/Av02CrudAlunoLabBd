package br.edu.fateczl.SpringAluno.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexProfessor {

	@RequestMapping(name = "menuProfessor", value = "/menuProfessor", method = RequestMethod.GET)
	public ModelAndView indexGet(ModelMap model) {
		return new ModelAndView("menuProfessor");
	}

	@RequestMapping(name = "menuProfessor", value = "/menuProfessor", method = RequestMethod.POST)
	public ModelAndView indexPost(ModelMap model) {
		return new ModelAndView("menuProfessor");
	}

}