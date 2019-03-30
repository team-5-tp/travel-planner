package entity;

import org.json.JSONException;
import org.json.JSONObject;


public class Poi {
	  	private int poiId;
	    private String poiName;
	    private int visitingOrder;
	    private int planId;
	    private String venueId;
	    private int userId;

	    private Poi(PoiBuilder builder) {
			// TODO Auto-generated constructor stub
			this.poiId= builder.poiId;
	        this.poiName = builder.poiName;
	        this.visitingOrder = builder.visitingOrder;
	        this.planId = builder.planId;
	        this.venueId = builder.venueId;
	        this.userId = builder.userId;
		}
	    
	    // This may not look necessary, but in case of future evolution, 
	    // use a builder pattern for instantiation for now
	
	    public static class PoiBuilder {
	    	private int poiId;
		    private String poiName;
		    private int visitingOrder;
		    private int planId;
		    private String venueId;
		    private int userId;

	        public void setPoiId(int poiId) {
	            this.poiId = poiId;
	        }

	        public void setPoiName(String poiName) {
	            this.poiName = poiName;
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

	        public Poi build() {
	            return new Poi(this);
	        }
	    }
	    public int getPoiId() {
	        return poiId;
	    }

	    public String getPoiName() {
	        return poiName;
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
	 			obj.put("poi_id", planId);
	 			obj.put("poi_name", poiName);
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
