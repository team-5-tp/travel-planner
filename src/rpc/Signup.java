package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.UserDBConnectionFactory;
import db.UserDBConnection;

/**
 * Servlet implementation class Register
 */
@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDBConnection connection = UserDBConnectionFactory.getConnection();
		try {
			JSONObject input = RpcHelper.readJSONObject(request);
			entity.User user=entity.User.fromJSONObject(input);
			if (connection.create(user)) {                                                                        
				response.setStatus(201);
			} else {
				response.setStatus(409);
			}
		} catch (Exception e) {
			response.setStatus(500);
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

    
}
