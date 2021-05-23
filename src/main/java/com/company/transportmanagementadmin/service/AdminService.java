package com.company.transportmanagementadmin.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.transportmanagementadmin.model.Request;
import com.company.transportmanagementadmin.model.Vehicle;
import com.company.transportmanagementadmin.model.VehicleRouteMap;
import com.company.transportmanagementadmin.repository.AdminServiceRepository;

@Service
public class AdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	AdminServiceRepository adminServiceRepository;

	public List<Vehicle> getVehicles() {
		return adminServiceRepository.getVehicleList();
	}

	public void assignRoute(Request request) throws Exception {

		Date date = null;
		try {
			date = format.parse(request.getRouteDate());
			if (adminServiceRepository.checkIfRouteExist(request.getRoute().getRouteId(),
					request.getVehicle().getVehicleId(), date)) {
				LOGGER.error("Route : " + request.getRoute().getRouteId() + " already assigned for vehicle : "
						+ request.getVehicle().getVehicleId() + " on route date : " + request.getRouteDate());
				throw new Exception("Route : " + request.getRoute().getRouteId() + " already assigned for vehicle : "
						+ request.getVehicle().getVehicleId() + " on route date : " + request.getRouteDate());
			} else {
				adminServiceRepository.addRoute(request, date);
				LOGGER.info("Route : " + request.getRoute().getRouteId() + "  assigned for vehicle : "
						+ request.getVehicle().getVehicleId() + " on route date : " + request.getRouteDate());
			}

		} catch (ParseException e) {
			LOGGER.error("Exception :: Invalid route-date recived " + e);
			throw new Exception("Invalid route-date recived");
		} catch (NullPointerException exception) {
			LOGGER.error("Exception :: route id,vehicle id or route_date is missing ");
			throw new Exception("Exception :: route id,vehicle id or route_date is missing ");
		}

	}

	public void addTicket(Request request) throws Exception {
		Date date = null;
		try {
			date = format.parse(request.getRouteDate());
			adminServiceRepository.addTicket(request, date);
		} catch (ParseException exception) {
			LOGGER.error("Exception ::  Invalid route-date recived " + exception);
			throw new Exception("Invalid route-date recived");
		} catch (NullPointerException exception) {
			LOGGER.error("Exception :: route id,vehicle id or route_date is missing " + exception);
			throw new Exception("route id,vehicle id or route_date is missing ");
		} catch (Exception exception) {
			LOGGER.error("Exception " + exception);
			throw new Exception(exception);
		}
	}

	public VehicleRouteMap getAvailableSeat(Request request) throws Exception {
		Date date = null;
		VehicleRouteMap vehicleRouteMap = new VehicleRouteMap();
		try {
			date = format.parse(request.getRouteDate());
			vehicleRouteMap = adminServiceRepository.getAvailableSeat(request.getRoute().getRouteId(),
					request.getVehicle().getVehicleId(), date);
		} catch (ParseException e) {
			LOGGER.error("Exception Invalid route-date recived " + e);
			throw new Exception("Invalid route-date recived");
		} catch (NullPointerException exception) {
			LOGGER.error("Exception route id,vehicle id or route_date is missing ");
			throw new Exception("Exception route id,vehicle id or route_date is missing ");
		} catch (Exception exception) {
			LOGGER.error("Exception " + exception);
			throw new Exception("Exception " + exception);
		}
		return vehicleRouteMap;

	}
}
