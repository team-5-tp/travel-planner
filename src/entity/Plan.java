package entity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Representation of each generated travel plan
 */
public class Plan {
    // Data fields of the ID and name of each plan as well as
    // 1. the user_id of the user who creates this plan
    // 2. the points of interest included in this plan
    private int id;
    private String name;
    private int userId;
    
    // This may not look necessary, but in case of future evolution, 
    // use a builder pattern for instantiation for now
    private Plan(PlanBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.userId = builder.userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }
    
    public int getUserId() {
        return userId;
    }
    
    
    
    /**
     * Convert the plan to a JSON object such that the app can understand
     * 
     * @return a JSON object containing the plan info in the format
     *         {
     *           id: 
     *           name:
     *           user_id:
     *         }
     */
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    public static class PlanBuilder {
        private int id;
        private String name;
        private int userId;
//        private List<POI> pointsOfInterest; 

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
        
        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Plan build() {
            return new Plan(this);
        }
    }
}
