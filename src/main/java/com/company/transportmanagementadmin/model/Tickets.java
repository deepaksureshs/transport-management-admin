package com.company.transportmanagementadmin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Tickets {

	private Integer ticketId;
	private Integer vehicleRouteMapId;
	private String passengerName;
	private Integer ticketCharge;
	private String createdDate;

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getVehicleRouteMapId() {
		return vehicleRouteMapId;
	}

	public void setVehicleRouteMapId(Integer vehicleRouteMapId) {
		this.vehicleRouteMapId = vehicleRouteMapId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Integer getTicketCharge() {
		return ticketCharge;
	}

	public void setTicketCharge(Integer ticketCharge) {
		this.ticketCharge = ticketCharge;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Tickets [ticketId=" + ticketId + ", vehicleRouteMapId=" + vehicleRouteMapId + ", passengerName="
				+ passengerName + ", ticketCharge=" + ticketCharge + ", createdDate=" + createdDate + "]";
	}

}
