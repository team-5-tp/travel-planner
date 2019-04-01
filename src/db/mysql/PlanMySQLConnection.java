package db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.PlanDBConnection;
import entity.Plan;
import entity.Plan.PlanBuilder;

public class PlanMySQLConnection extends MySQLConnection implements PlanDBConnection {

    @Override
    public boolean insertPlan(Plan plan) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }
        
        try {
            String sql = "INSERT IGNORE INTO plan VALUES(?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, plan.getPlanId());
            statement.setString(2, plan.getPlanName());
            statement.setInt(3, plan.getUserId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Plan getPlan(int planId) {
        Plan plan = null;
        if (conn == null) {
            System.err.println("DB connection failed");
            return plan;
        }
        
        try {
            String sql = "SELECT * FROM plan WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, planId);
            
            ResultSet resultSet = statement.executeQuery();
            PlanBuilder planBuilder = new PlanBuilder();
            while (resultSet.next()) {
                planBuilder.setPlanId(resultSet.getInt("id"));
                planBuilder.setPlanName(resultSet.getString("name"));
                planBuilder.setUserId(resultSet.getInt("user_id"));
            }
            plan = planBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;
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
                PlanBuilder planBuilder = new PlanBuilder();
                planBuilder.setPlanId(resultSet.getInt("id"));
                planBuilder.setPlanName(resultSet.getString("name"));
                planBuilder.setUserId(resultSet.getInt("user_id"));
                allPlans.add(planBuilder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPlans;
    }

    @Override
    public boolean deletePlan(int planId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }
        
        try {
            String sql = "DELETE FROM plan WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, planId);
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePlan(int planId, String newName) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }
        
        try {
            String sql = "UPDATE plan SET name = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setInt(2, planId);
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
