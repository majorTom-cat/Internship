package com.ojtsin.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojtsin.demo.dto.ScmsDTO;

public interface ScmsRepository extends JpaRepository<ScmsDTO, Long>{

	Optional<ScmsDTO> findByScmName(String scmName);

}
