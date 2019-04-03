package db;
import entity.Poi;
import java.util.List;


public interface PoiDBConnection extends DBConnection {
	public String addPoint(Poi poi);
	public boolean deletePoint(int poiId, int planId);
	public List<Poi> getPoints(int planId);
	public boolean updatePoint(int poiId, int planId, int visitingOrder);
}

