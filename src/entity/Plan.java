package entity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Representation of each generated travel plan
 */
public class Plan {
    // Data fields of the ID and name of each plan as well as
    // 1. the user_id and username of the user who creates this plan
    // 2. the points of interest included in this plan
    private int planId;
    private String planName;
    private int userId;
    private String userName;
    // TODO(MZ): need to update after POI has been created
//    private List<POI> pointsOfInterest;
    
    // This may not look necessary, but in case of future evolution, 
    // use a builder pattern for instantiation for now
    private Plan(PlanBuilder builder) {
        this.planId = builder.planId;
        this.planName = builder.planName;
        this.userId = builder.userId;
        this.userName = builder.userName;
//        this.pointsOfInterest = builder.pointsOfInterest;
    }

    public int getPlanId() {
        return planId;
    }

    public String getPlanName() {
        return planName;
    }
    
    public int getUserId() {
    	return userId;
    }

    public String getUserName() {
        return userName;
    }

//    public List<POI> getPointsOfInterest() {
//        return pointsOfInterest;
//    }
    
    /**
     * Convert the plan to a JSON object such that the app can understand
     */
 	public JSONObject toJSONObject() {
 		JSONObject obj = new JSONObject();
 		try {
 			obj.put("user_id", userId);
 			obj.put("plan_id", planId);
 			obj.put("username", userName);
 			obj.put("planname", planName);
 		} catch (JSONException e) {
 			e.printStackTrace();
 		}
 		return obj;
 	}
    
    public static class PlanBuilder {
        private int planId;
        private String planName;
        private int userId;
        private String userName;
//        private List<POI> pointsOfInterest; 

        public void setPlanId(int planId) {
            this.planId = planId;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }
        
        public void setUserId(int userId) {
        	this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

//        public void setPointsOfInterest(List<POI> pointsOfInterest) {
//            this.pointsOfInterest = pointsOfInterest;
//        }
        
        public Plan build() {
            return new Plan(this);
        }
    }
}