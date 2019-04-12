package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class PoI {
	private Integer id;
	private String name;
	private int visitingOrder;
	private int planId;
//	private String venueId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

//	public String getVenueId() {
//		return venueId;
//	}

//	public void setVenueId(String venueId) {
//		this.venueId = venueId;
//	}

	public int getVisitingOrder() {
		return visitingOrder;
	}

	public void setVisitingOrder(int visitingOrder) {
		this.visitingOrder = visitingOrder;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("name", name);
			obj.put("visiting_order", visitingOrder);
			obj.put("plan_id", planId);
//			obj.put("venue_id", venueId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static PoI fromJSONObject(JSONObject obj) {
		PoI poi = new PoI();
		try {
			poi.id = obj.getInt("id");
		} catch (JSONException e) {
			poi.id = null;
		}
		try {
			poi.name = obj.getString("name");
			poi.visitingOrder = obj.getInt("visiting_order");
			poi.planId = obj.getInt("plan_id");
//			poi.venueId=obj.getString("venue_id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return poi;
	}
}