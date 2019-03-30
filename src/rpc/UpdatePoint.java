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

/**
 * Servlet implementation class UpdatePoint
 */
@WebServlet("/UpdatePoint")
public class UpdatePoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePoint() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection connection = DBConnectionFactory.getConnection();
		  // Get the parameters required for fetching a plan --> user_id and plan_id
		JSONObject obj = new JSONObject();
		try {
	        int poiId = Integer.parseInt(request.getParameter("poi_id"));
	        int planId = Integer.parseInt(request.getParameter("plan_id"));
	        int visitingOrder = Integer.parseInt(request.getParameter("visiting_order"));
	        int userId = Integer.parseInt(request.getParameter("user_id"));
	        if (connection.updatePoint(poiId, planId, visitingOrder, userId)) {
	        	obj.put("result", "success");
	        }
	        else {
	        	obj.put("result", "failure");
	        }
	        RpcHelper.writeJsonObject(response, obj);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        connection.close();
	    }
	}

}
