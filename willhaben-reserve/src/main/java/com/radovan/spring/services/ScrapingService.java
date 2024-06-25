package com.radovan.spring.services;

import java.io.IOException;
import java.util.List;

import com.radovan.spring.dto.CarDto;

public interface ScrapingService {

	List<CarDto> scrapeCars() throws IOException;
	
	//CarDto scrapeCarDetails(String carUrl) throws IOException;

}
