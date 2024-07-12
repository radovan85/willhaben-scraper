package com.radovan.spring.config;

import java.sql.Timestamp;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import com.radovan.spring.converter.StringToTimestampConverter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.radovan.spring")
@EnableScheduling
public class SpringMvcConfiguration implements WebMvcConfigurer {

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver returnValue = new SpringResourceTemplateResolver();
		returnValue.setPrefix("/WEB-INF/templates/");
		returnValue.setSuffix(".html");
		returnValue.setCacheable(false);
		return returnValue;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine returnValue = new SpringTemplateEngine();
		returnValue.setTemplateResolver(templateResolver());
		returnValue.setEnableSpringELCompiler(true);
		return returnValue;
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		registry.viewResolver(resolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
	}
	
	@Bean
	public ModelMapper getMapper() {
		ModelMapper returnValue = new ModelMapper();
		returnValue.getConfiguration().setAmbiguityIgnored(true)
				.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
		returnValue.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Converter<String, Timestamp> stringToTimestampConverter = new StringToTimestampConverter();
	    returnValue.addConverter(stringToTimestampConverter);
		return returnValue;
	}

}