package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;

import entity.Plan;

/**
 * Servlet implementation class UserPlan
 */
@WebServlet("/userplan")
public class UserPlan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPlan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the database reference
		DBConnection connection = DBConnectionFactory.getConnection();
		// Get the parameters required for fetching a plan --> user_id and plan_id
		int userId = Integer.parseInt(request.getParameter("user_id"));
		int planId = Integer.parseInt(request.getParameter("plan_id"));
		Plan plan = null;
		try {
			plan = connection.getPlan(userId, planId);
			JSONObject object = plan.toJSONObject();
			RpcHelper.writeJsonObject(response, object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
