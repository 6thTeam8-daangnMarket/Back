package com.sparta.clone_backend.repository;


import com.sparta.clone_backend.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {
}
