package db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.PoIDBConnection;

import entity.PoI;
import entity.PoI.PoIBuilder;

import java.util.List;
import java.util.ArrayList;

public class PoIMySQLConnection extends MySQLConnection implements PoIDBConnection {

    @Override
    public String addPoint(PoI poi) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return "";
        }
        
        String id = "";
        // TODO(MZ): the id should be an integer rather than a string
        try {
            // help execute sql in database
            String sql = "INSERT INTO poi VALUE(?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // start with 1 not 0 it's the order of table column
            //ps.setInt(1, poi.getid());
            ps.setString(2, poi.getName());
            ps.setInt(3, poi.getVisitingOrder());
            ps.setInt(4, poi.getPlanId());
            ps.setString(5, poi.getVenueId());
            int result = ps.executeUpdate();
            // Get the generated id of the inserted plan    
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                id = String.valueOf(resultSet.getInt(1));
            }
            // TODO(MZ): try to figure out a way to make this consistent with the methods in other rpc classes
//            else {
//            	throw new Exception("no keys generated");
//            }
            if (result == 1) {
                return id;
            }
            return "";
        }
        catch (SQLException e) {
                e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean deletePoint(int id, int planId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }
        try {
            String sql = "DELETE * FROM poi WHERE id = ? and plan_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setInt(2, planId);
            return statement.execute();
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
            String sql = "SELECT * from poi WHERE plan_id = ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, planId);
            ResultSet rs = statement.executeQuery();
            PoIBuilder poiBuilder = new PoIBuilder();
            while (rs.next()) {
                poiBuilder.setId(rs.getInt("id"));
                poiBuilder.setName(rs.getString("name"));
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
    public boolean updatePoint(int id, int planId, int visitingOrder) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }
        try {
            String sql = "UPDATE poi SET visiting_order = ? WHERE id = ? "
                    + "AND plan_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, visitingOrder);
            statement.setInt(2, id);
            statement.setInt(3, planId);
            return statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
