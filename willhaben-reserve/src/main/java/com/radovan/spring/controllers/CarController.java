package com.radovan.spring.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.radovan.spring.services.CarService;

@Controller
@RequestMapping(value = "/mvc/cars")
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping(value = "/allCars")
	public String listAllCars(ModelMap map) throws IOException {
		carService.refreshAllCars();
		map.put("allCars", carService.listAll());
		return "fragments/carsList :: fragmentContent";
	}
}
