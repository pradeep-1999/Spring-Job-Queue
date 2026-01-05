package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.JobRequest;
import com.example.demo.Enums.JobStatus;
import com.example.demo.entities.Job;
import com.example.demo.repos.JobRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JobService {

	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public Long createJob(JobRequest jobRequest) {
		
		Job job = new Job();
		 job.setJobType(jobRequest.getType());
		 
		 try {
		        String jsonPayload = objectMapper.writeValueAsString(jobRequest.getPayload());
		        job.setPayload(jsonPayload);
		    } catch (JsonProcessingException e) {
		        throw new RuntimeException("Invalid JSON payload");
		    }
		 
	     job.setStatus(JobStatus.PENDING);
	     job.setCreatedAt(LocalDateTime.now());
	     job.setUpdatedAt(LocalDateTime.now());
		
	     return jobRepository.save(job).getId();
	     	
	}

	public Job getJob(Long id) {
		
		return jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
		
	}
	
	
	
}
