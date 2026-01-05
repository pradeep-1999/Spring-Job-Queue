package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.JobRequest;
import com.example.demo.entities.Job;
import com.example.demo.service.JobService;

@RestController
@RequestMapping("/jobsV1")
public class JobController {

	@Autowired 
	JobService jobService;
	
	
	  @PostMapping
	    public ResponseEntity<Long> submitJob(@RequestBody JobRequest request) {
	        Long jobId = jobService.createJob(request);
	        return ResponseEntity.accepted().body(jobId);
	    }

	    @GetMapping("/{id}")
	    public Job getStatus(@PathVariable Long id) {
	        return jobService.getJob(id);
	    }
	
}
