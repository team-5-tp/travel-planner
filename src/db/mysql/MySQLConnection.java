package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.DBConnection;
import entity.Plan;
import entity.Plan.PlanBuilder;

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
			String sql = "INSERT IGNORE INTO user VALUES (?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			return statement.executeUpdate() == 1;
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
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, user_id);
			statement.execute();
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
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username_new);
			statement.setString(2, password_new);
			statement.setString(3, username);
			statement.setString(4, password);
			return statement.executeUpdate() == 1;
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
	public boolean insertPlan(Plan plan) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		
		try {
			String sql = "INSERT IGNORE INTO plan VALUES(?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, plan.getUserId());
			statement.setInt(2, plan.getPlanId());
			statement.setString(3, plan.getPlanName());
			statement.setString(4, plan.getUserName());
			return statement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Plan getPlan(int userId, int planId) {
		Plan plan = null;
		if (conn == null) {
			System.err.println("DB connection failed");
			return plan;
		}
		
		try {
			String sql = "SELECT * FROM plan WHERE user_id = ? and plan_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.setInt(2, planId);
			
			ResultSet resultSet = statement.executeQuery();
			PlanBuilder planBuilder = new PlanBuilder();
			while (resultSet.next()) {
				planBuilder.setUserId(resultSet.getInt("user_id"));
				planBuilder.setPlanId(resultSet.getInt("plan_id"));
				planBuilder.setUserName(resultSet.getString("username"));
				planBuilder.setPlanName(resultSet.getString("planname"));
			}
			plan = planBuilder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plan;
	}

	@Override
	public boolean deletePlan(Plan plan) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		
		try {
			String sql = "DELETE FROM plan WHERE user_id = ? and plan_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, plan.getUserId());
			statement.setInt(2, plan.getPlanId());
			return statement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;		
	}

	@Override
	public boolean updatePlan(Plan oldPlan, Plan newPlan) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		// Delete the old plan from the table and add the new plan
		deletePlan(oldPlan);
		return insertPlan(newPlan);
	}
}
