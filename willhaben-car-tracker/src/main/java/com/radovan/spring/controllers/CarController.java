package com.radovan.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mvc/cars")
public class CarController {

	

	@GetMapping(value = "/allCars")
	public String listAllCars(ModelMap map) {
		return "fragments/carsList :: fragmentContent";
	}
}
