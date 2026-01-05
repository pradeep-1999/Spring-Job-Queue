package com.example.demo.entities;

import java.time.LocalDateTime;

import com.example.demo.Enums.JobStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "jobs")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "job_key", nullable=false, length=100)
	private String jobKey;
	
    @Column(name = "job_type", nullable = false, length=50)
	private String jobType;
	
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
	private JobStatus status;
	
	@Column(columnDefinition = "json")
	private String payload;
	
	 @Column(nullable = false)
	    private Integer priority = 0;

	    // Retry management
	 @Column(name = "retry_count", nullable = false)
	 private Integer retryCount = 0;

	 @Column(name = "max_retries", nullable = false)
	 private Integer maxRetries = 3;
  
	  // Debugging
	    @Column(name = "error_message", length = 500)
	    private String errorMessage;

	  @Column(name = "created_at", nullable = false, updatable = false)
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at", nullable = false)
	    private LocalDateTime updatedAt;
	    
	    protected void onCreate() {
	    	this.createdAt = LocalDateTime.now();
	    	this.updatedAt = this.createdAt;
	    	if (this.status == null) {
	            this.status = JobStatus.PENDING;
	        }
	    }
	    
	    @PreUpdate
	    protected void onUpdate() {
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public Job(String jobKey,
	               String jobType,
	               String payload,
	               Integer priority) {
	        this.jobKey = jobKey;
	        this.jobType = jobType;
	        this.payload = payload;
	        this.priority = priority;
	        this.status = JobStatus.PENDING;
	    }
	    
	    
}
