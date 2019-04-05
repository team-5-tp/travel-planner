package db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.PlanDBConnection;
import entity.Plan;

public class PlanMySQLConnection extends MySQLConnection implements PlanDBConnection {

	@Override
	public boolean insertPlan(Plan plan) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}

		try {
			String sql = "INSERT INTO plan (name, city, user_id) VALUES (?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, plan.getName());
			statement.setString(2, plan.getCity());
			statement.setInt(3, plan.getUserId());
			int result = statement.executeUpdate();
			// Get the generated id of the inserted plan
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				plan.setId(resultSet.getInt(1));
			} else {
				throw new Exception("no keys generated");
			}
			return result == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Plan getPlan(int id) {
		Plan plan = null;
		if (conn == null) {
			System.err.println("DB connection failed");
			return plan;
		}

		try {
			String sql = "SELECT * FROM plan WHERE id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				plan = new Plan();
				plan.setId(resultSet.getInt("id"));
				plan.setName(resultSet.getString("name"));
				plan.setCity(resultSet.getString("city"));
				plan.setUserId(resultSet.getInt("user_id"));
			}
			return plan;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Plan> getAllPlans(int userId) {
		List<Plan> allPlans = new ArrayList<>();
		if (conn == null) {
			System.err.println("DB connection failed");
			return allPlans;
		}

		try {
			String sql = "SELECT * FROM plan WHERE user_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userId);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Plan plan = new Plan();
				plan.setId(resultSet.getInt("id"));
				plan.setName(resultSet.getString("name"));
				plan.setCity(resultSet.getString("city"));
				plan.setUserId(resultSet.getInt("user_id"));
				allPlans.add(plan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allPlans;
	}

	@Override
	public boolean deletePlan(int id) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			conn.setAutoCommit(false);
			String sql1 = "DELETE FROM poi WHERE plan_id = ?";
			PreparedStatement stat1 = conn.prepareStatement(sql1);
			stat1.setInt(1, id);
			stat1.executeUpdate();
			String sql2 = "DELETE FROM plan WHERE id = ?";
			PreparedStatement stat2 = conn.prepareStatement(sql2);
			stat2.setInt(1, id);
			stat2.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean updatePlan(Plan plan) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}

		try {
			String sql = "UPDATE plan SET name = ? , city = ? WHERE id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, plan.getName());
			statement.setString(2, plan.getCity());
			statement.setInt(3, plan.getId());
			return statement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
