package com.example.demo.Consumer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.Enums.JobStatus;
import com.example.demo.entities.FailedJob;
import com.example.demo.entities.Job;
import com.example.demo.repos.FailedJobRepository;
import com.example.demo.repos.JobRepository;

import jakarta.transaction.Transactional;

@Component
public class JobWorker {

	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	FailedJobRepository failedJobRepository;
	
	@Scheduled(fixedDelay = 5000)
	@Async
	@Transactional
	public void processJobs() {
		
		List<Job> jobs = jobRepository.findPendingJobs(PageRequest.of(0, 5));
		
		for(Job job : jobs) {
			try {
				job.setStatus(JobStatus.RUNNING);
				jobRepository.save(job);
				
				executeJob(job);
				
				job.setStatus(JobStatus.COMPLETED);
                jobRepository.save(job);

				
			} catch(Exception e) {
				
                handleFailure(job, e);

			}
		}
	}
	
	private void executeJob(Job job) {
		
		switch(job.getJobType()) {
		case "EMAIL":
            System.out.println("Sending email...");
            break;
        case "REPORT":
            System.out.println("Generating report...");
            break;
        default:
            throw new IllegalArgumentException("Unknown job type");
		}
	}
	
	
    private void handleFailure(Job job, Exception ex) {

        job.setRetryCount(job.getRetryCount() + 1);
        job.setErrorMessage(ex.getMessage());

        if (job.getRetryCount() >= job.getMaxRetries()) {

            // Move to dead-letter table
            FailedJob failedJob = new FailedJob(
                    job.getId(),
                    job.getJobType(),
                    job.getPayload(),
                    ex.getMessage(),
                    job.getRetryCount()
            );

            failedJobRepository.save(failedJob);

            // Remove from main queue
            jobRepository.delete(job);

        } else {
            // Retry later
            job.setStatus(JobStatus.PENDING);
            jobRepository.save(job);
        }
    }

	
}
