package com.kayakedu.marketing.services.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kayakedu.marketing.services.config.SlovakConfig;
import com.kayakedu.marketing.services.exception.ErrorScenario;
import com.kayakedu.marketing.services.exception.ServiceException;
import com.kayakedu.marketing.services.mapper.FreeMarkerTemplateMapper;
import com.kayakedu.marketing.services.payload.SlovakVisaMailPayloadRequest;
import com.kayakedu.marketing.services.payload.model.VisaMailResponseModel;
import com.kayakedu.marketing.services.service.VisaService;
import com.kayakedu.marketing.services.util.ExcelToPojoUtils;

import freemarker.template.TemplateException;

@Service
public class VisaServiceImpl implements VisaService {

	@Autowired
	private SlovakConfig slovakConfig;

	@Autowired
	private FreeMarkerTemplateMapper freeMarkerTemplate;

	public List<VisaMailResponseModel> sendMail() {

		List<VisaMailResponseModel> results = new ArrayList<>();

		try {
			// step 1: Get the Input data
			List<SlovakVisaMailPayloadRequest> applicants = ExcelToPojoUtils.toPojo(SlovakVisaMailPayloadRequest.class,
					slovakConfig.getVisaInputFileData());
			
			// Step 2: constructAndSendEmail
			for (SlovakVisaMailPayloadRequest applicant : applicants) {
				VisaMailResponseModel visaMailResponseModel = new VisaMailResponseModel();
				visaMailResponseModel.setProcessStarted(getDate());
				try {
					constructAndSendEmail(applicant);
					prepareResponse(visaMailResponseModel, applicant, "Mail has been sent.");
				} catch (EmailException | TemplateException e) {
					prepareResponse(visaMailResponseModel, applicant, "Applicat input data error: " +e.getMessage());
				}
				results.add(visaMailResponseModel);
			}
			return results;
		} catch (IOException e) {
			throw new ServiceException("Excel Path (or) Input Data are Invalid: " + e.getMessage(), ErrorScenario.INVALID_DATA,
					ErrorScenario.INVALID_DATA.name());
		}
	}
	
	private VisaMailResponseModel prepareResponse(VisaMailResponseModel visaMailResponseModel, SlovakVisaMailPayloadRequest applicant, String status) {		
		visaMailResponseModel.setSender(applicant.getEmailId());
		visaMailResponseModel.setRecevier(slovakConfig.getEmail().getReceiver());
		visaMailResponseModel.setStatus(status);
		visaMailResponseModel.setProcessCompleted(getDate());
		visaMailResponseModel.setCountry("slovak");	
		return visaMailResponseModel;
	}

	private void constructAndSendEmail(SlovakVisaMailPayloadRequest applicant) throws EmailException, TemplateException, IOException {
		Map<String, Object> content = prepareMailContent(applicant);

		HtmlEmail email = new HtmlEmail();
		email.setHostName(applicant.getHostname());
		email.setSmtpPort(587);
		email.setStartTLSEnabled(true);

		email.setAuthentication(applicant.getEmailId(), applicant.getPassword());
		email.setFrom(applicant.getEmailId());
		email.addTo(slovakConfig.getEmail().getReceiver());
		email.setSubject(slovakConfig.getEmail().getSubject());
		email.setHtmlMsg(freeMarkerTemplate.getMappedString(slovakConfig.getVisaTemplate(), content));

		if (!applicant.getAttachment().equalsIgnoreCase("NA")) {
			File file = new File(applicant.getAttachment());
			email.embed(file.toURI().toURL(), file.getName());
		}

		email.send();
	}

	private Map<String, Object> prepareMailContent(SlovakVisaMailPayloadRequest applicant) {

		// Prepare content for the mail body
		Map<String, Object> content = new HashMap<>();

		content.put("name", applicant.getName());
		content.put("surname", applicant.getSurname());
		content.put("passportNumber", applicant.getPassportNumber());
		content.put("categoryoftheApplication", applicant.getCategoryoftheApplication());
		content.put("employerCompanyName", applicant.getEmployerCompanyName());
		content.put("nameofthePositions", applicant.getNameofthePositions());
		content.put("numericcodeofthePositions", applicant.getNumericcodeofthePositions().toString());

		return content;
	}

	private String getDate() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return LocalDateTime.now().format(format);
	}

}
