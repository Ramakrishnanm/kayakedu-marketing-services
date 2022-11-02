package com.kayakedu.marketing.services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kayakedu.marketing.services.payload.model.VisaMailResponseModel;
import com.kayakedu.marketing.services.service.VisaService;

@RestController
public class VisaController {

	@Autowired
	private VisaService emailService;

	@GetMapping("/sendMail")
	public List<VisaMailResponseModel> sendMail() {
		return emailService.sendMail();
	}

}
