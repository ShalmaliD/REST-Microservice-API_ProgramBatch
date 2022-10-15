package com.numpyninja.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.numpyninja.lms.repository.entity.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

}
