package com.numpyninja.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.numpyninja.lms.repository.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {

}
