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
    
    // This may not look necessary, but in case of future evolution, 
    // use a builder pattern for instantiation for now
    private Plan(PlanBuilder builder) {
        this.planId = builder.planId;
        this.planName = builder.planName;
        this.userId = builder.userId;
        this.userName = builder.userName;
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
    
    /**
     * Convert the plan to a JSON object such that the app can understand
     * 
     * @return a JSON object containing the plan info in the format
     *         {
     *           plan_id: 
     *           planname:
     *           user_id:
     *           username:
     *         }
     */
 	public JSONObject toJSONObject() {
 		JSONObject obj = new JSONObject();
 		try {
 			obj.put("plan_id", planId);
 			obj.put("planname", planName);
 			obj.put("user_id", userId);
 			obj.put("username", userName);
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

        public Plan build() {
            return new Plan(this);
        }
    }
}