package com.company.transportmanagementadmin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class VehicleRouteMap {

	private Integer vehicleRouteId;
	private Integer seatAvailable;
	public Integer getVehicleRouteId() {
		return vehicleRouteId;
	}
	public void setVehicleRouteId(Integer vehicleRouteId) {
		this.vehicleRouteId = vehicleRouteId;
	}
	public Integer getSeatAvailable() {
		return seatAvailable;
	}
	public void setSeatAvailable(Integer seatAvailable) {
		this.seatAvailable = seatAvailable;
	}

	@Override
	public String toString() {
		return "VehicleRouteMap [vehicleRouteId=" + vehicleRouteId + ", seatAvailable=" + seatAvailable + "]";
	}
}
