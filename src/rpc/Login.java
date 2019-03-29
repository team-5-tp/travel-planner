package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import db.UserDBConnection;
import db.UserDBConnectionFactory;
import entity.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDBConnection connection = UserDBConnectionFactory.getConnection();
		try {
			JSONObject input = RpcHelper.readJSONObject(request);
			String username = input.getString("username");
			String password = input.getString("password");
			User user=connection.getByUsernamePassword(username, password);
			JSONObject obj = new JSONObject();
			if (user!=null) {
				obj.put("status", "OK").put("token", JwtToken.createToken(user));
			} else {
				response.setStatus(401);
				obj.put("status", "User Doesn't Exists");
			}
			RpcHelper.writeJSONObject(response, obj);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

}
