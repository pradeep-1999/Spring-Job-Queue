package com.example.demo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "failed_jobs")
public class FailedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to original job
    @Column(name = "original_job_id", nullable = false)
    private Long originalJobId;

    @Column(name = "job_type", nullable = false)
    private String jobType;

    @Column(columnDefinition = "json", nullable = false)
    private String payload;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Column(name = "failed_at", nullable = false)
    private LocalDateTime failedAt = LocalDateTime.now();

    // Optional: retry attempt count at failure time
    @Column(name = "retry_count")
    private Integer retryCount;

    // Optional: which worker failed it
    @Column(name = "worker_name")
    private String workerName;

    // --- Constructors ---

    public FailedJob(Long originalJobId,
                     String jobType,
                     String payload,
                     String errorMessage,
                     Integer retryCount) {
        this.originalJobId = originalJobId;
        this.jobType = jobType;
        this.payload = payload;
        this.errorMessage = errorMessage;
        this.retryCount = retryCount;
        this.failedAt = LocalDateTime.now();
    }

    }
