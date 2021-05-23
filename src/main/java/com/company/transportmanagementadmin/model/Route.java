package com.company.transportmanagementadmin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Route {
	private Integer routeId;
	private String routeSource;
	private String routeDestination;
	private String createdDate;
	private String updatedDate;

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getRouteSource() {
		return routeSource;
	}

	public void setRouteSource(String routeSource) {
		this.routeSource = routeSource;
	}

	public String getRouteDestination() {
		return routeDestination;
	}

	public void setRouteDestination(String routeDestination) {
		this.routeDestination = routeDestination;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "Route [routeId=" + routeId + ", routeSource=" + routeSource + ", routeDestination=" + routeDestination
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}

}
