package com.kayakedu.marketing.services.service;

import java.util.List;

import com.kayakedu.marketing.services.payload.model.VisaMailResponseModel;

//Interface
public interface VisaService {

	// To send an email with or without attachment
	public List<VisaMailResponseModel> sendMail();
}
