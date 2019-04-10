package db;

import entity.PoI;
import java.util.List;

public interface PoIDBConnection extends DBConnection {
    public boolean addPoint(PoI poi);
    public boolean deletePoint(int id);
    public boolean updatePoint(PoI poi);
    public PoI getPoI(int id);
    public List<PoI> getPoints(int planId);
    public boolean deletePoints(int planId);
}
