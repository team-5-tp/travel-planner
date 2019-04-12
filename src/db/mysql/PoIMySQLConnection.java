package db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import db.PoIDBConnection;
import entity.PoI;

public class PoIMySQLConnection extends MySQLConnection implements PoIDBConnection {

	@Override
	public boolean addPoint(PoI poi) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}

		try {
			// help execute sql in database
			String sql = "INSERT INTO poi (name,visiting_order,plan_id) VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, poi.getName());
			ps.setInt(2, poi.getVisitingOrder());
			ps.setInt(3, poi.getPlanId());
//			ps.setString(4, poi.getVenueId());
			int result = ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				poi.setId(resultSet.getInt(1));
			}
			return result == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deletePoint(int id) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "DELETE FROM poi WHERE id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			return statement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deletePoints(int planId) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "DELETE FROM poi WHERE plan_id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, planId);
			return statement.executeUpdate() >= 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updatePoint(PoI poi) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "UPDATE poi SET visiting_order = ? WHERE id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, poi.getVisitingOrder());
			statement.setInt(2, poi.getId());
			return statement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PoI> getPoints(int planId) {
		if (conn == null) {
			return null;
		}
		List<PoI> results = new ArrayList<>();
		try {
			String sql = "SELECT * from poi WHERE plan_id = ? order by visiting_order";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, planId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				PoI poI = new PoI();
				poI.setId(rs.getInt("id"));
				poI.setName(rs.getString("name"));
				poI.setVisitingOrder(rs.getInt("visiting_order"));
				poI.setPlanId(rs.getInt("plan_id"));
//				poI.setVenueId(rs.getString("venue_id"));
				results.add(poI);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public PoI getPoI(int id) {
		PoI poi = null;
		if (conn == null) {
			System.err.println("DB connection failed");
			return poi;
		}

		try {
			String sql = "SELECT * FROM poi WHERE id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				poi = new PoI();
				poi.setId(resultSet.getInt("id"));
				poi.setName(resultSet.getString("name"));
				poi.setVisitingOrder(resultSet.getInt("visiting_order"));
				poi.setPlanId(resultSet.getInt("plan_id"));
//				poi.setVenueId(resultSet.getString("venue_id"));
			}
			return poi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
