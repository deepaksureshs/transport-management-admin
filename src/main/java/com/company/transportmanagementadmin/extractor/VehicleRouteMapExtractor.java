package com.company.transportmanagementadmin.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.company.transportmanagementadmin.model.VehicleRouteMap;

public class VehicleRouteMapExtractor implements RowMapper<VehicleRouteMap> {

	@Override
	public VehicleRouteMap mapRow(ResultSet rs, int rowNum) throws SQLException {
		VehicleRouteMap vehicleRouteMap = new VehicleRouteMap();
		vehicleRouteMap.setSeatAvailable(rs.getInt("seat_available"));
		vehicleRouteMap.setVehicleRouteId(rs.getInt("vehicle_route_id"));
		return vehicleRouteMap;
	}

}
