package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private Integer id;
	private String username;
	private String password;

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("username", username);
			obj.put("password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static User fromJSONObject(JSONObject obj) {
		User user = new User();
		try {
			user.id = obj.getInt("id");
		} catch (JSONException e) {
			user.id = null;
		}
		try {
			user.username = obj.getString("username");
			user.password = obj.getString("password");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
}
