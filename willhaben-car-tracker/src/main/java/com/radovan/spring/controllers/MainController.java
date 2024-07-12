package com.radovan.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping
	public String index() {
		return "index";
	}
	
	@GetMapping(value="/home")
	public String home() {
		return "fragments/homePage :: fragmentContent";
	}
}
