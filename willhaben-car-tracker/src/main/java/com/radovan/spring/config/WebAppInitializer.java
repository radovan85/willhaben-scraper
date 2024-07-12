package com.radovan.spring.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		// TODO Auto-generated method stub

		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(SpringMvcConfiguration.class);
		ServletRegistration.Dynamic initializer = servletContext.addServlet("Spring Initializer",
				new DispatcherServlet(webContext));

		initializer.setLoadOnStartup(1);
		initializer.addMapping("/");

	}

}