package com.kayakedu.marketing.services.payload;

import java.util.List;

import com.kayakedu.marketing.services.payload.model.VisaMailResponseModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VisaMailPayloadResponse {

	private List<VisaMailResponseModel> visaMailResponse;
	
}