package com.radovan.spring.services;

import java.io.IOException;

public interface ScrapingService {

	void scrapeCars() throws IOException;
	
	Boolean isCarAdded();

}
