package me.jpomykala.yahoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
@Controller
public class HomeController {

	@RequestMapping(value = {"/", "", "api", "/api"})
	public String redirectToSwagger() {
		return "redirect:/swagger-ui.html";
	}

}
