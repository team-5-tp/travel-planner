package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import db.DBConnection;
import entity.Poi;
import entity.Poi.PoiBuilder;
import java.util.List;
import java.util.ArrayList;

public class MySQLConnection implements DBConnection {
	private Connection conn;

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean userInsert(String username, String password) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "INSERT  INTO  user (username,password)  VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void userDelete(int user_id) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return;
		}

		try {
			String sql = "DELETE FROM user WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean userUpdate(String username_new, String password_new, String username, String password) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "UPDATA user SET user = ?, password =? WHERE username = ? and password ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username_new);
			ps.setString(2, password_new);
			ps.setString(3, username);
			ps.setString(4, password);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean userVerify(String username, String password) {
		if (conn == null) {
			return false;
		}
		try {
			String sql="select * from user where username = ? and password= ? ";
			PreparedStatement statement=conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rs=statement.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addPoint(Poi poi) {
		// TODO Auto-generated method stub
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			// help exceute sql in database
			String sql = "INSERT INTO poi VALUE(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// start with 1 not 0 it's the order of table column
			//ps.setInt(1, poi.getPoiId());
			ps.setString(2, poi.getPoiName());
			ps.setInt(3, poi.getVisitingOrder());
			ps.setInt(4, poi.getPlanId());
			ps.setString(5, poi.getVenueId());
			int result = ps.executeUpdate();
			return result == 1;
		}
			catch (SQLException e) {
				e.printStackTrace();
		}
		return false;
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
	public List<Poi> getPoints(int userId, int planId) {
		// TODO Auto-generated method stub
		if (conn == null) {
			return null;
		}
		List<Poi> results = new ArrayList<>();
		try {
			String sql = "SELECT * from poi WHERE user_id = ? AND plan_id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.setInt(2, planId);
			ResultSet rs = statement.executeQuery();
			PoiBuilder poiBuilder = new PoiBuilder();
			while (rs.next()) {
				poiBuilder.setPoiId(rs.getInt("poi_id"));
				poiBuilder.setPoiName(rs.getString("poi_name"));
				poiBuilder.setVisitingOrder(rs.getInt("visiting_order"));
				poiBuilder.setPlanId(rs.getInt("plan_id"));
				poiBuilder.setVenueId(rs.getString("venue_id"));
				poiBuilder.setUserId(rs.getInt("user_id"));
				results.add(poiBuilder.build());
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public boolean updatePoint(int poiId, int planId, int visitingOrder, int userId) {
		// TODO Auto-generated method stub
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "UPDATE poi SET visiting_order = ? WHERE poi_id = ? "
					+ "AND plan_id = ? AND user_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, visitingOrder);
			statement.setInt(2, poiId);
			statement.setInt(3, planId);
			statement.setInt(4, userId);
			return statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
