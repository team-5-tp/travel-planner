package db.mysql;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import db.PoiDBConnection;
import entity.Poi;
import entity.Poi.PoiBuilder;
import java.util.List;
import java.util.ArrayList;

public class PoiMySQLConnection extends MySQLConnection implements PoiDBConnection {

	@Override
	public String addPoint(Poi poi) {
		// TODO Auto-generated method stub
		if (conn == null) {
			System.err.println("DB connection failed");
			return "";
		}
		String poiId = "";
		try {
			// help execute sql in database
			String sql = "INSERT INTO poi VALUE(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// start with 1 not 0 it's the order of table column
			//ps.setInt(1, poi.getPoiId());
			ps.setString(2, poi.getPoiName());
			ps.setInt(3, poi.getVisitingOrder());
			ps.setInt(4, poi.getPlanId());
			ps.setString(5, poi.getVenueId());
			int result = ps.executeUpdate();
			// Get the generated id of the inserted plan    
			ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                poiId = String.valueOf(resultSet.getInt(1));
            }
//            else {
//            	throw new Exception("no keys generated");
//            }
            if (result == 1) {
            	return poiId;
            }
            return "";
		}
		catch (SQLException e) {
				e.printStackTrace();
		}
		return "";
	}

	@Override
	public boolean deletePoint(int poiId, int planId) {
		// TODO Auto-generated method stub
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "DELETE * FROM poi WHERE poi_id = ? and plan_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, poiId);
			statement.setInt(2, planId);
			return statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;		
	}

	@Override
	public List<Poi> getPoints(int planId) {
		// TODO Auto-generated method stub
		if (conn == null) {
			return null;
		}
		List<Poi> results = new ArrayList<>();
		try {
			String sql = "SELECT * from poi WHERE plan_id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, planId);
			ResultSet rs = statement.executeQuery();
			PoiBuilder poiBuilder = new PoiBuilder();
			while (rs.next()) {
				poiBuilder.setPoiId(rs.getInt("poi_id"));
				poiBuilder.setPoiName(rs.getString("poi_name"));
				poiBuilder.setVisitingOrder(rs.getInt("visiting_order"));
				poiBuilder.setPlanId(rs.getInt("plan_id"));
				poiBuilder.setVenueId(rs.getString("venue_id"));
				results.add(poiBuilder.build());
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public boolean updatePoint(int poiId, int planId, int visitingOrder) {
		// TODO Auto-generated method stub
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "UPDATE poi SET visiting_order = ? WHERE poi_id = ? "
					+ "AND plan_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, visitingOrder);
			statement.setInt(2, poiId);
			statement.setInt(3, planId);
			return statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
