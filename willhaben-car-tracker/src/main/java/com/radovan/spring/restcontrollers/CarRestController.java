package com.radovan.spring.restcontrollers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.CarDto;
import com.radovan.spring.services.CarService;
import com.radovan.spring.services.ScrapingService;

@RestController
@RequestMapping(value = "/api/cars")
public class CarRestController {

	@Autowired
	private ScrapingService scrapingService;

	@Autowired
	private CarService carService;

	@GetMapping
	public ResponseEntity<List<CarDto>> findAllCars() throws IOException {
		scrapingService.scrapeCars();
		return new ResponseEntity<>(carService.listAll(), HttpStatus.OK);
	}

	/*
	 * @GetMapping(value = "/carDetails/{carId}") public ResponseEntity<CarDto>
	 * getCarDetails(@PathVariable("carId") Long carId) throws IOException { CarDto
	 * car = carService.getCarById(carId); return new
	 * ResponseEntity<>(scrapingService.scrapeCarDetails(car.getSeoUrl()),
	 * HttpStatus.OK); }
	 */
}
