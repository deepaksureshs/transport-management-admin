package com.company.transportmanagementadmin.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.company.transportmanagementadmin.model.Vehicle;

public class VehicleListExtractor implements ResultSetExtractor<List<Vehicle>> {

	@Override
	public List<Vehicle> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		while (rs.next()) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicleId(rs.getInt("vehicle_id"));
			vehicle.setVehicleName(rs.getString("vehicle_name"));
			vehicle.setRegistrationNumber(rs.getString("registration_number"));
			vehicle.setVehicleType(rs.getString("vehicle_type"));
			vehicle.setCapacity(rs.getInt("capacity"));
			vehicle.setHealthStatus(rs.getString("health_status"));
			vehicle.setCreatedDate(rs.getString("created_date"));
			vehicle.setUpdatedDate(rs.getString("updated_date"));
			vehicleList.add(vehicle);
		}
		return vehicleList;
	}

}
