package com.kayakedu.marketing.services.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SlovakVisaMailPayloadRequest {
	private String name;
	private String surname;
	private String passportNumber;
	private String categoryoftheApplication;
	private String employerCompanyName;
	private String nameofthePositions;
	private Integer numericcodeofthePositions;
	private String attachment;
	private String emailId;
	private String password;
	private String hostname;
	
}