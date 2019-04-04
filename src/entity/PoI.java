package entity;

import org.json.JSONException;
import org.json.JSONObject;


public class PoI {
    private int id;
    private String name;
    private int visitingOrder;
    private int planId;
    private String venueId;
    private int userId;

    private PoI(PoIBuilder builder) {
        this.id= builder.id;
        this.name = builder.name;
        this.visitingOrder = builder.visitingOrder;
        this.planId = builder.planId;
        this.venueId = builder.venueId;
        this.userId = builder.userId;
    }

    public static class PoIBuilder {
        private int id;
        private String name;
        private int visitingOrder;
        private int planId;
        private String venueId;
        private int userId;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
        
        public void setPlanId(int planId) {
            this.planId = planId;
        }

        public void setVenueId(String venueId) {
            this.venueId = venueId;
        }
        public void setVisitingOrder(int visitingOrder) {
            this.visitingOrder = visitingOrder;
        }
        public void setUserId(int userId) {
            this.userId = userId;
        }

        public PoI build() {
            return new PoI(this);
        }
    }
    
    public int getid() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public int getPlanId() {
        return planId;
    }

    public String getVenueId() {
        return venueId;
    }
    public int getVisitingOrder() {
        return visitingOrder;
    }
    public int getUserId() {
        return userId;
    }
    
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("name", name);
            obj.put("visiting_order", visitingOrder);
            obj.put("plan_id", planId);
            obj.put("venue_id", venueId);
            obj.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}