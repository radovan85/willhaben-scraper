package com.radovan.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long>{

	List<CarEntity> findAllByOrderByPublishedDesc();
}
