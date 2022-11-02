package com.kayakedu.marketing.services.payload.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VisaMailResponseModel {
	private String sender;
	private String recevier[];
	private String country;
	private String processStarted;
	private String processCompleted;
	private String status;
	
}