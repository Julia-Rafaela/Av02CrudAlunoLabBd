package br.edu.fateczl.SpringAluno.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class MenuProfessor {
	@RequestMapping(name = "professor", value = "/professor", method = RequestMethod.GET)
	public ModelAndView indexGet(ModelMap model) {
		return new ModelAndView("professor");
	}

	@RequestMapping(name = "professor", value = "/professor", method = RequestMethod.POST)
	public ModelAndView indexPost(ModelMap model) {
		return new ModelAndView("professor");
	}
}
