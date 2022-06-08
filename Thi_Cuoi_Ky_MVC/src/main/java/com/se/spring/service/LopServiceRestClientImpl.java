package com.se.spring.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.se.spring.model.Lop;
import com.se.spring.model.Sinhvien;

@Service
public class LopServiceRestClientImpl implements LopService {
	private RestTemplate restTemplate;
	private String crmRestLopUrl, crmRestSinhvienUrl;
	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	public LopServiceRestClientImpl(RestTemplate theRestTemplate, @Value("${crm.rest.lop.url}") String lopUrl,
			@Value("${crm.rest.sinhvien.url}") String sinhvienUrl) {
		restTemplate = theRestTemplate;
		crmRestLopUrl = lopUrl;
		crmRestSinhvienUrl = sinhvienUrl;
		logger.info("Loaded property: crm.rest.lop.url=" + crmRestLopUrl);
		logger.info("Loaded property: crm.rest.sinhvien.url=" + crmRestSinhvienUrl);
	}

	@Override
	public List<Lop> getLops() {
		logger.info("in getLops(): Calling REST API " + crmRestLopUrl);
		ResponseEntity<List<Lop>> responseEntity = restTemplate.exchange(crmRestLopUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Lop>>() {
				});
		List<Lop> lops = responseEntity.getBody();
		logger.info("in getLops(): lops" + lops);
		return lops;
	}

	@Override
	public void saveLop(Lop theLop) {
		logger.info("in saveLop(): Calling REST API " + crmRestLopUrl);
		int lopId = theLop.getId();
		if (lopId == 0) {
			restTemplate.postForEntity(crmRestLopUrl, theLop, String.class);
		} else {
			restTemplate.put(crmRestLopUrl, theLop);
		}
		logger.info("in saveLop(): success");
	}

	@Override
	public Lop getLop(int lopId) {
		logger.info("in getLop(): Calling REST API " + crmRestLopUrl);
		Lop theLop = restTemplate.getForObject(crmRestLopUrl + "/" + lopId, Lop.class);
		logger.info("in saveLop() : theLop=" + theLop);
		return theLop;
	}

	@Override
	public void deleteLop(int lopId) {
		logger.info("in deleteLop(): Calling REST API " + crmRestLopUrl);
		restTemplate.delete(crmRestLopUrl + "/" + lopId);
		logger.info("in deleteLop(): deleted lop theId= " + lopId);
	}

	@Override
	public List<Sinhvien> getSinhviens(int lopId) {
		logger.info("in getSinhviens(): Calling REST API " + crmRestSinhvienUrl);
		ResponseEntity<List<Sinhvien>> responseEntity = restTemplate.exchange(crmRestSinhvienUrl + "/" + lopId,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Sinhvien>>() {
				});
		List<Sinhvien> sinhviens = responseEntity.getBody();
		logger.info("in getSinhviens(): sinhviens" + sinhviens);
		return sinhviens;
	}

	@Override
	public Sinhvien getSinhvien(int lopId, int sinhvienId) {
		logger.info("in getSinhvien(): Calling REST API " + crmRestSinhvienUrl);
		Sinhvien theSinhvien = restTemplate.getForObject(crmRestSinhvienUrl + "/" + lopId, Sinhvien.class, sinhvienId);
		return theSinhvien;
	}
}
