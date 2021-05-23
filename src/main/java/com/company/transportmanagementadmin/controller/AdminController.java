package com.company.transportmanagementadmin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.transportmanagementadmin.model.Request;
import com.company.transportmanagementadmin.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class AdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	AdminService adminService;

	@GetMapping("/vehicle")
	public ResponseEntity getVehicleList() {
		LOGGER.info("Request received for getting vehicles List :: payload ");
		String responsePayload = "";
		Map<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("status", "ERROR");
		try {
			responsePayload = objectMapper.writeValueAsString(adminService.getVehicles());
			LOGGER.info(responsePayload);
		} catch (Exception exception) {
			errorResponse.put("message", exception.getMessage());
			LOGGER.error("Exception :: " + exception.getMessage());
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responsePayload);
	}

	@PostMapping("/vehicle/assignroute")
	public ResponseEntity assignRoute(@RequestBody String payload) {
		LOGGER.info("Request received for assigning route :: payload " + payload);
		Map<String, String> successResponse = new HashMap<String, String>();
		Map<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("status", "ERROR");
		Request request = new Request();

		try {

			request = objectMapper.readValue(payload, Request.class);
			adminService.assignRoute(request);
			successResponse.put("status", "SUCCESS");
		} catch (JsonMappingException exception) {
			errorResponse.put("message", exception.getMessage());
			LOGGER.error("payload mapping exception occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);

		} catch (JsonProcessingException exception) {
			errorResponse.put("message", exception.getMessage());
			LOGGER.error("payload processing error occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		} catch (Exception exception) {
			errorResponse.put("message", exception.getMessage());
			LOGGER.error("Exception :: " + exception.getMessage());
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(successResponse);
	}

	@PostMapping("/vehicle/addticket")
	public ResponseEntity addTicket(@RequestBody String payload) {
		LOGGER.info("Request received for ticket generation :: payload : " + payload);
		Map<String, String> successResponse = new HashMap<String, String>();
		Map<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("status", "ERROR");
		Request request = new Request();

		try {
			request = objectMapper.readValue(payload, Request.class);
			adminService.addTicket(request);
			successResponse.put("status", "SUCCESS");

		} catch (JsonMappingException exception) {
			errorResponse.put("message", exception.getMessage());
			LOGGER.error("payload mapping exception occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);

		} catch (JsonProcessingException exception) {
			errorResponse.put("message", exception.getMessage());
			LOGGER.error("payload processing error occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);

		} catch (Exception exception) {
			errorResponse.put("message", exception.getMessage());
			LOGGER.error(exception.getMessage());
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(successResponse);
	}

	@GetMapping("/vehicle/checkpassenger")
	public ResponseEntity getPassenger(@RequestBody String payload) {
		LOGGER.info("Request received for checking available seats " + payload);

		String responsePayload = "";
		Map<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("status", "ERROR");
		Request request = new Request();
		try {
			request = objectMapper.readValue(payload, Request.class);
			responsePayload = objectMapper.writeValueAsString(adminService.getAvailableSeat(request));
		} catch (JsonMappingException exception) {
			errorResponse.put("message", "payload mapping error occured" + exception);
			LOGGER.error("payload mapping exception occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);

		} catch (JsonProcessingException exception) {
			errorResponse.put("message", "payload processing error occured" + exception);
			LOGGER.error("payload processing error occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		} catch (Exception exception) {
			errorResponse.put("message", exception.getMessage());
			LOGGER.error(exception.getMessage());
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responsePayload);
	}

}
