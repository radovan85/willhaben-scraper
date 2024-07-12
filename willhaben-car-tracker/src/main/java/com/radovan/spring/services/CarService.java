package com.radovan.spring.services;

import java.util.List;

import com.radovan.spring.dto.CarDto;

public interface  CarService {

	Boolean addCar(CarDto car);
	
	CarDto getCarById(Long carId);
	
	void removeCar(Long carId);

	List<CarDto> listAll();
	
	void refreshAllCars();
}
