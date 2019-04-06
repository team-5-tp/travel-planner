package entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Representation of each generated travel plan
 */
public class Plan {
	// Data fields of the ID and name of each plan as well as
	// 1. the user_id of the user who creates this plan
	// 2. the points of interest included in this plan
	private Integer id;
	private String name;
	private String city;
	private Integer userId;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("name", name);
			obj.put("city", city);
			obj.put("user_id", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static Plan fromJSONObject(JSONObject obj) {
		Plan plan = new Plan();
		try {
			plan.id = obj.getInt("id");
		} catch (JSONException e) {
			plan.id = null;
		}
		try {
			plan.name = obj.getString("name");
			plan.city = obj.getString("city");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			plan.userId = obj.getInt("user_id");
		} catch (JSONException e) {
			plan.userId = null;
			e.printStackTrace();
		}
		return plan;
	}
}
