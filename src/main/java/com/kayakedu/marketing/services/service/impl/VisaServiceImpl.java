package com.kayakedu.marketing.services.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kayakedu.marketing.services.config.SlovakConfig;
import com.kayakedu.marketing.services.mapper.FreeMarkerTemplateMapper;
import com.kayakedu.marketing.services.payload.SlovakVisaMailPayload;
import com.kayakedu.marketing.services.service.VisaService;
import com.kayakedu.marketing.services.util.ExcelToPojoUtils;

@Service
public class VisaServiceImpl implements VisaService {

	@Autowired
	private SlovakConfig slovakConfig;

	@Autowired
	private FreeMarkerTemplateMapper freeMarkerTemplate;

	public String sendMail() {

		try {
			//step 1: Get the Input data
			List<SlovakVisaMailPayload> applicants = ExcelToPojoUtils.toPojo(SlovakVisaMailPayload.class, slovakConfig.getVisaInputFileData());
			
			//Step 2: constructAndSendEmail
			for(SlovakVisaMailPayload applicant: applicants) {
				constructAndSendEmail(applicant);
			}
			
			return "Mail sent Successfully";
		} catch (Exception e) {
			return "Error while sending mail!!!";
		}
	}

	private void constructAndSendEmail(SlovakVisaMailPayload applicant) throws Exception {
		Map<String, Object> content = prepareMailContent(applicant);
		
		// Construct EMAIL
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(applicant.getHostname());
			email.setSmtpPort(587);
			email.setStartTLSEnabled(true);
			
			email.setAuthentication(applicant.getEmailId(), applicant.getPassword());
			email.setFrom(applicant.getEmailId());
			email.addTo(slovakConfig.getEmail().getReceiver());
			email.setSubject(slovakConfig.getEmail().getSubject());
			email.setHtmlMsg(freeMarkerTemplate.getMappedString(slovakConfig.getVisaTemplate(), content));
			
			if(!applicant.getAttachment().equalsIgnoreCase("NA")) {		
				File file = new File(applicant.getAttachment());
				email.embed(file.toURI().toURL(), file.getName());
			}
			
			email.send();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	private Map<String, Object> prepareMailContent(SlovakVisaMailPayload applicant) {

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

}
