package db;

import entity.User;

public interface UserDBConnection extends DBConnection {
	public boolean create(User user);
	public boolean delete(int id);
	public boolean update(User user);
	public User getByUsernamePassword(String username, String password);
	public User getById(int id);
}
