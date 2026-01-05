package com.example.demo.DTOs;

import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class JobRequest {

	private String type;
    private Map<String, Object> payload; 
			
}
