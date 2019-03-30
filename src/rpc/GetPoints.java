package rpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.*;
import entity.Poi;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;

/**
 * Servlet implementation class GetPoints
 */
@WebServlet("/GetPoints")
public class GetPoints extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPoints() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection connection = DBConnectionFactory.getConnection();
		  // Get the parameters required for fetching a plan --> user_id and plan_id
		List<Poi> poiList = new ArrayList<>();
		JSONArray result = new JSONArray();
		try {
			int userId = Integer.parseInt(request.getParameter("user_id"));
			int planId = Integer.parseInt(request.getParameter("plan_id"));
			poiList = connection.getPoints(userId, planId);
			for (Poi poi: poiList) {
				JSONObject obj = poi.toJSONObject();
				result.put(obj);
			}
	        RpcHelper.writeJsonArray(response, result);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        connection.close();
	    }
	}

}
