package com.radovan.spring.restcontrollers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.CarDto;
import com.radovan.spring.services.CarService;
import com.radovan.spring.services.ScrapingService;

@RestController
@RequestMapping(value = "/api/cars")
@CrossOrigin(value = "*")
public class CarRestController {

	@Autowired
	private CarService carService;

	@Autowired
	ScrapingService scrapingService;

	@GetMapping
	public ResponseEntity<List<CarDto>> findAllCars() throws IOException {
		scrapingService.scrapeCars();
		carService.refreshAllCars();
		return new ResponseEntity<>(carService.listAll(), HttpStatus.OK);
	}

	@GetMapping(value="/isCarAdded")
	public ResponseEntity<Boolean> isCarAdded() {
		return new ResponseEntity<>(scrapingService.isCarAdded(), HttpStatus.OK);
	}

}
