package com.radovan.spring.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.CarDto;
import com.radovan.spring.entity.CarEntity;
import com.radovan.spring.exceptions.InstanceUndefinedException;
import com.radovan.spring.repositories.CarRepository;
import com.radovan.spring.services.CarService;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	@Transactional
	public CarDto addCar(CarDto car) {
		// TODO Auto-generated method stub
		Optional<CarEntity> carOptional = carRepository.findById(car.getId());
		if (carOptional.isPresent()) {
			System.out.println("This car exists already");
			return null;
		} else {
			System.out.println("Attempt to store the car");
			CarEntity storedCar = carRepository.saveAndFlush(tempConverter.carDtoToEntity(car));
			System.out.println("Car has been stored");
			return tempConverter.carEntityToDto(storedCar);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public CarDto getCarById(Long carId) {
		// TODO Auto-generated method stub
		CarEntity carEntity = carRepository.findById(carId)
				.orElseThrow(() -> new InstanceUndefinedException(new Error("The car has not been found")));
		return tempConverter.carEntityToDto(carEntity);
	}

	@Override
	@Transactional
	public void removeCar(Long carId) {
		// TODO Auto-generated method stub
		getCarById(carId);
		carRepository.deleteById(carId);
		carRepository.flush();
	}

	@Override
	@Transactional(readOnly = true)
	public List<CarDto> listAll() {
		// TODO Auto-generated method stub

		List<CarEntity> allCars = carRepository.findAllByOrderByPublishedDesc();
		System.out.println("All cars size: " + allCars.size());
		return allCars.stream().map(tempConverter::carEntityToDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void refreshAllCars() {
		// TODO Auto-generated method stub
		List<CarEntity> allCars = carRepository.findAll();
		LocalDateTime austrianTime = tempConverter.getCurrentAustriaTimestamp().toLocalDateTime();
		LocalDateTime limitPublishedTime = austrianTime.minusHours(1L);
		allCars.forEach((carEntity) -> {
			LocalDateTime publishedTime = carEntity.getPublished().toLocalDateTime();
			if (publishedTime.isBefore(limitPublishedTime)) {
				removeCar(carEntity.getId());
			}
		});
	}

}
