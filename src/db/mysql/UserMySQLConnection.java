package db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.PlanDBConnection;
import db.PlanDBConnectionFactory;
import db.PoIDBConnectionFactory;
import entity.Plan;
import entity.User;

public class UserMySQLConnection extends MySQLConnection implements db.UserDBConnection{
//	public static void main(String[] args) {
//		UserDBConnection connection=new UserDBConnection();
//		Integer id=null;
//		connection.insert("1111", "1111", id);
//		System.out.println(id);
//	}
	@Override
	public boolean create(User user) {
		if (this.conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "INSERT INTO user (username,password)  VALUES (?, ?)";
			PreparedStatement ps = this.conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			
			int result= ps.executeUpdate() ;
			ResultSet rs = ps.getGeneratedKeys();  
			if (rs.next()) {
				user.setId(rs.getInt(1));
				//System.out.println(id);
			} else {
				throw new Exception("return no id");
			}
			return result==1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		if (this.conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			PlanDBConnection planConnection=PlanDBConnectionFactory.getConnection();
			List<Plan> plans=planConnection.getAllPlans(id);
			for (Plan plan : plans) {
				planConnection.deletePlan(plan.getId());
			}
			String sql = "DELETE FROM user WHERE id = ?";
			PreparedStatement ps = this.conn.prepareStatement(sql);
			ps.setInt(1, id);
			return  ps.executeUpdate()==1 ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(User user) {
		if (this.conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "UPDATE user SET username = ?, password = ? WHERE id = ?";
			PreparedStatement ps = this.conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getId());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User getByUsernamePassword(String username, String password) {
		if (this.conn == null) {
			return null;
		}
		try {
			String sql="select * from user where username = ? and password= ? ";
			PreparedStatement statement=this.conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rs=statement.executeQuery();
			while (rs.next()) {
				User user=new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getById(int id) {
		if (this.conn == null) {
			return null;
		}
		try {
			String sql="select * from user where id = ? ";
			PreparedStatement statement=this.conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs=statement.executeQuery();
			while (rs.next()) {
				User user=new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		} catch (SQLException e){ 
			e.printStackTrace();
		}
		return null;
	}
}
