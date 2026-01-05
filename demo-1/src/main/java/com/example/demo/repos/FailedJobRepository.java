package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.FailedJob;

@Repository
public interface FailedJobRepository extends JpaRepository<FailedJob,Long>{

}
