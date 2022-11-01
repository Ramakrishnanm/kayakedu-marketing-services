package com.kayakedu.marketing.services.mapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class FreeMarkerTemplateMapper {

	@Autowired
	private Configuration templateConfig;

	/**
	 * return mapped values from .tdl file with given input
	 */
	public Map<String, Object> getMappedValues(String templateName, Object data) throws TemplateException, IOException {

		StringWriter out = new StringWriter();
		Map<String, Object> mappedValues = null;

		Template template = templateConfig.getTemplate(templateName);

		// Mapping the object with template
		template.process(data, out);

		ObjectMapper mapper = new ObjectMapper();
		mappedValues = mapper.reader().forType(Map.class).readValue(out.toString());

		return mappedValues;
	}

	/**
	 * Transform template to string for given data
	 */

	public String getMappedString(String templateName, Object data) throws TemplateException, IOException {

		StringWriter out = new StringWriter();

		Template template = templateConfig.getTemplate(templateName);

		// Mapping the object with template
		template.process(data, out);

		return out.toString();
	}
}
