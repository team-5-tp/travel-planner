package db;

import entity.PoI;
import java.util.List;

public interface PoIDBConnection extends DBConnection {
    public String addPoint(PoI poi);
    public boolean deletePoint(int id, int planId);
    public List<PoI> getPoints(int planId);
    public boolean updatePoint(int id, int planId, int visitingOrder);
}
