package com.company.transportmanagementadmin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Request {

	private String routeDate;
	private Route route;
	private Vehicle vehicle;
	private Tickets tickets;

	public Tickets getTickets() {
		return tickets;
	}

	public void setTickets(Tickets tickets) {
		this.tickets = tickets;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getRouteDate() {
		return routeDate;
	}

	public void setRouteDate(String routeDate) {
		this.routeDate = routeDate;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "Request [routeDate=" + routeDate + ", route=" + route + ", vehicle=" + vehicle + ", tickets=" + tickets
				+ "]";
	}

}
