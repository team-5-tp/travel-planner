package db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            PreparedStatement statement = conn.prepareStatement(sql);
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
                planBuilder.setPlanId(resultSet.getInt("plan_id"));
                planBuilder.setPlanName(resultSet.getString("planname"));
                planBuilder.setUserId(resultSet.getInt("user_id"));
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
