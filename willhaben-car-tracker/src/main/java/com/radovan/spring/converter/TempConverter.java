package com.radovan.spring.converter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.radovan.spring.dto.CarDto;
import com.radovan.spring.entity.CarEntity;

@Component
public class TempConverter {

	@Autowired
	private ModelMapper mapper;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private ZoneId zoneId = ZoneId.of("Europe/Vienna");

	public Timestamp getCurrentAustriaTimestamp() {
		ZonedDateTime currentTime = Instant.now().atZone(ZoneId.of("Europe/Vienna"));
		return Timestamp.valueOf(currentTime.toLocalDateTime());
	}

	public CarDto carEntityToDto(CarEntity car) {
		CarDto returnValue = mapper.map(car, CarDto.class);
		Optional<Timestamp> publishedOptional = Optional.ofNullable(car.getPublished());
		if (publishedOptional.isPresent()) {
			ZonedDateTime publishedTime = publishedOptional.get().toLocalDateTime().atZone(zoneId);
			returnValue.setPublished(publishedTime.toLocalDateTime().format(formatter));
		}

		return returnValue;
	}

	public CarEntity carDtoToEntity(CarDto car) {
		return mapper.map(car, CarEntity.class);

	}
}
