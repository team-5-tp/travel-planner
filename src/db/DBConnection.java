package db;

public interface DBConnection {
	public void close();
	public boolean userInsert(String username, String password);
	public void userDelete(int user_id);
	public boolean userUpdate(String username_new, String password_new,String username, String password);
	public boolean userVerify(String username, String password);
}

