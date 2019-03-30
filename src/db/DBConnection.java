package db;
import entity.Poi;
import java.util.List;


public interface DBConnection {
	public void close();
	public boolean userInsert(String username, String password);
	public void userDelete(int user_id);
	public boolean userUpdate(String username_new, String password_new,String username, String password);
	public boolean userVerify(String username, String password);
	public boolean addPoint(Poi poi);
	public boolean deletePoint(int poiId, int planId);
	public List<Poi> getPoints(int userId, int planId);
	public boolean updatePoint(int poiId, int planId, int visitingOrder, int userId);
}

