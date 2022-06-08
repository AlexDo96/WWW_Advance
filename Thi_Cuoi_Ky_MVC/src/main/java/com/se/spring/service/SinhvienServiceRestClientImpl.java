package com.se.spring.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.se.spring.model.Sinhvien;

@Service
public class SinhvienServiceRestClientImpl implements SinhvienService {
	private RestTemplate restTemplate;
	private String crmRestSinhvienUrl;
	private String crmRestOneSinhvienUrl;
	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	public SinhvienServiceRestClientImpl(RestTemplate theRestTemplate,
			@Value("${crm.rest.sinhvien.url}") String sinhvienUrl,
			@Value("${crm.rest.one.sinhvien.url}") String oneSinhvienUrl) {
		restTemplate = theRestTemplate;
		crmRestSinhvienUrl = sinhvienUrl;
		crmRestOneSinhvienUrl = oneSinhvienUrl;
		logger.info("Loaded property: crm.rest.sinhvien.url=" + crmRestSinhvienUrl);
	}

	@Override
	public Sinhvien getSinhvien(int sinhvienId) {
		logger.info("in getSinhvien(): Calling REST API " + crmRestOneSinhvienUrl);
		Sinhvien theSinhvien = restTemplate.getForObject(crmRestOneSinhvienUrl + "/" + sinhvienId, Sinhvien.class);
		return theSinhvien;
	}

	@Override
	public void saveSinhvien(Sinhvien theSinhvien, int lopId) {
		logger.info("in saveSinhvien(): Calling REST API " + crmRestSinhvienUrl);
		restTemplate.postForEntity(crmRestSinhvienUrl + "/" + lopId, theSinhvien, String.class);
		logger.info("in saveSinhvien(): success");
	}

	@Override
	public void updateSinhvien(Sinhvien theSinhvien, int lopId) {
		logger.info("in saveSinhvien(): Calling REST API " + crmRestSinhvienUrl);
		restTemplate.put(crmRestSinhvienUrl + "/" + lopId, theSinhvien);
		logger.info("in updateSinhvien(): success");
	}

	@Override
	public void deleteSinhvien(int sinhvienId) {
		logger.info("in deleteSinhvien(): Calling REST API " + crmRestSinhvienUrl);
		restTemplate.delete(crmRestSinhvienUrl + "/" + sinhvienId);
		logger.info("in deleteSinhvien(): deleted sinhvien theId= " + sinhvienId);
	}
}
