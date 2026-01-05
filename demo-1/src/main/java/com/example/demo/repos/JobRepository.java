package com.example.demo.repos;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Job;

import jakarta.persistence.LockModeType;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {


	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("SELECT * FROM jobs WHERE status = 'PENDING' ORDER BY priority DESC, created_at ASC")
	List<Job> findPendingJobs(PageRequest pageRequest);
	
	
}
