package com.company.transportmanagementadmin.repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.company.transportmanagementadmin.extractor.VehicleListExtractor;
import com.company.transportmanagementadmin.extractor.VehicleRouteMapExtractor;
import com.company.transportmanagementadmin.model.Request;
import com.company.transportmanagementadmin.model.Tickets;
import com.company.transportmanagementadmin.model.Vehicle;
import com.company.transportmanagementadmin.model.VehicleRouteMap;

@Repository
public class AdminServiceRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceRepository.class);

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	NamedParameterJdbcTemplate jdbcTemplate;

	public List<Vehicle> getVehicleList() {

		String selectVehicleQuery = "SELECT vehicle_id,vehicle_name,registration_number,vehicle_type,capacity,health_status,created_date,updated_date\r\n"
				+ "FROM VEHICLE;";
		LOGGER.info("Getting vehicles List");
		List<Vehicle> vehiclList = jdbcTemplate.query(selectVehicleQuery, new VehicleListExtractor());
		return vehiclList;
	}

	public boolean checkIfRouteExist(int routeId, int vehicleId, Date routeDate) {
		LOGGER.info("proceeding to add checkIfRouteExist");

		String selectRouteQuery = "SELECT count(vehicle_route_id)\r\n"
				+ "FROM vehicle_route_map WHERE route_id=:route_id AND vehicle_id=:vehicle_id AND route_date=:route_date";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("route_id", routeId, Types.INTEGER).addValue("vehicle_id", vehicleId, Types.INTEGER)
				.addValue("route_date", routeDate, Types.DATE);

		int id = jdbcTemplate.queryForObject(selectRouteQuery, paramSource, Integer.class);
		if (id == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void addRoute(Request request, Date date) throws Exception {
		LOGGER.info("proceeding to add route");
		String addRouteQuery = "INSERT INTO VEHICLE_ROUTE_MAP (vehicle_id, route_id, route_date,total_capacity, seat_available, total_collection, created_date, updated_date)\r\n"
				+ " VALUES ( :vehicle_id, :route_id, :route_date, :total_capacity, :seat_available, :total_collection, sysdate(), sysdate())";
		String selectCapacity = "SELECT capacity FROM vehicle WHERE vehicle_id=:vehicle_id";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("route_id", request.getRoute().getRouteId(), Types.INTEGER)
				.addValue("vehicle_id", request.getVehicle().getVehicleId(), Types.INTEGER)
				.addValue("route_date", date, Types.DATE).addValue("total_collection", 0, Types.INTEGER);
		try {
			int capacity = jdbcTemplate.queryForObject(selectCapacity, paramSource, Integer.class);
			paramSource.addValue("total_capacity", capacity, Types.INTEGER).addValue("seat_available", capacity,
					Types.INTEGER);
			LOGGER.info("Assigning route : " + request.getRoute().getRouteId() + " to vehicle : "
					+ request.getVehicle().getVehicleId());
			LOGGER.info("" + paramSource);
			jdbcTemplate.update(addRouteQuery, paramSource);
		} catch (DataIntegrityViolationException exception) {
			LOGGER.error("Exception Assigning route  :: route id or vehicle id invalid " + exception);
			throw new Exception("Exception Assigning route :: route id or vehicle id invalid ");
		} catch (DataAccessException accessException) {
			LOGGER.error("Exception  ::  vehicle id invalid " + accessException);
			throw new Exception("Exception ::  vehicle id invalid ");
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void addTicket(Request request, Date routeDate) throws Exception {
		LOGGER.info("proceeding to add ticket");

		String checkAvailabiliy = " select vehicle_route_id,seat_available from vehicle_route_map\r\n"
				+ " where vehicle_id=:vehicle_id and route_id=:route_id and route_date=:route_date";
		String insertTicket = " INSERT INTO tickets ( vehicle_route_map_id,passenger_name, ticket_charge, created_date)\r\n"
				+ " VALUES (:vehicle_route_map_id,:passenger_name,:ticket_charge,:created_date)";
		String updateCollection = " UPDATE  vehicle_route_map\r\n"
				+ " SET total_collection = total_collection+:collection,seat_available=seat_available-1 \r\n"
				+ " WHERE (vehicle_route_id=:vehicle_route_id);";

		MapSqlParameterSource paramSourceCheck = new MapSqlParameterSource();
		paramSourceCheck.addValue("route_id", request.getRoute().getRouteId(), Types.INTEGER)
				.addValue("vehicle_id", request.getVehicle().getVehicleId(), Types.INTEGER)
				.addValue("route_date", routeDate, Types.DATE);

		VehicleRouteMap vehicRouteMap = new VehicleRouteMap();
		try {
			LOGGER.info("Checking vehicle exist for route : " + request.getRoute().getRouteId() + " at "
					+ request.getRouteDate());
			LOGGER.info("" + paramSourceCheck);
			vehicRouteMap = jdbcTemplate.queryForObject(checkAvailabiliy, paramSourceCheck,
					new VehicleRouteMapExtractor());
		} catch (DataAccessException accessException) {
			LOGGER.error("Exception ::  vehicle :" + request.getVehicle().getVehicleId() + " not found on route : "
					+ request.getRoute().getRouteId() + " at " + request.getRouteDate() + " :: " + accessException);
			throw new Exception("Exception ::  vehicle :" + request.getVehicle().getVehicleId()
					+ " not found on route : " + request.getRoute().getRouteId() + " at " + request.getRouteDate());
		}

		if (vehicRouteMap.getSeatAvailable() != 0) {
			MapSqlParameterSource paramSourceInsertTicket = new MapSqlParameterSource();
			paramSourceInsertTicket.addValue("vehicle_route_map_id", vehicRouteMap.getVehicleRouteId(), Types.INTEGER)
					.addValue("passenger_name", request.getTickets().getPassengerName(), Types.VARCHAR)
					.addValue("ticket_charge", request.getTickets().getTicketCharge(), Types.INTEGER)
					.addValue("created_date", request.getTickets().getCreatedDate(), Types.VARCHAR);
			LOGGER.info("Adding ticket");
			LOGGER.info("" + paramSourceInsertTicket);
			jdbcTemplate.update(insertTicket, paramSourceInsertTicket);
			MapSqlParameterSource paramSourceUpdate = new MapSqlParameterSource();
			paramSourceUpdate.addValue("vehicle_route_id", vehicRouteMap.getVehicleRouteId(), Types.INTEGER)
					.addValue("collection", request.getTickets().getTicketCharge(), Types.BIGINT);
			LOGGER.info("updating collection");
			LOGGER.info("" + paramSourceUpdate);
			int row = jdbcTemplate.update(updateCollection, paramSourceUpdate);
			if (row == 0) {
				LOGGER.error("Total collection updation failed");
				throw new Exception("Total Collection is not not updated check vehicle, route and route_date");
			}
		} else {
			LOGGER.error("Capacity is full");
			throw new Exception("Capacity is full. Seat not available");
		}

	}

	public VehicleRouteMap getAvailableSeat(int routeId, int vehicleId, Date routeDate) throws Exception {
		LOGGER.info("Getting available seat for vehicle :" + vehicleId + " & route  : " + routeId + " on route date : "
				+ routeDate);

		String selectSeatAvailableQuery = "SELECT  vehicle_route_id,seat_available FROM VEHICLE_ROUTE_MAP \r\n"
				+ "WHERE route_date = :route_date AND route_id=:route_id AND vehicle_id=:vehicle_id";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("route_id", routeId, Types.INTEGER).addValue("route_date", routeDate, Types.DATE)
				.addValue("vehicle_id", vehicleId, Types.INTEGER);
		VehicleRouteMap vehicleRouteMap = new VehicleRouteMap();
		try {
			vehicleRouteMap = jdbcTemplate.queryForObject(selectSeatAvailableQuery, paramSource,
					new VehicleRouteMapExtractor());
		} catch (DataAccessException accessException) {
			LOGGER.error("Exception ::  vehicle :" + vehicleId + " not found on route : " + routeId + " at " + routeDate
					+ " :: " + accessException);
			throw new Exception(
					"Exception ::  vehicle :" + vehicleId + " not found on route : " + routeId + " at " + routeDate);
		}
		LOGGER.info("available seats : " + vehicleRouteMap.getSeatAvailable());
		return vehicleRouteMap;

	}

}
