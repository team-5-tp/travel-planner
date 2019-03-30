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
import entity.Poi.PoiBuilder;

/**
 * Servlet implementation class AddPoint
 */
@WebServlet("/addPoint")
public class AddPoint extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AddPoint() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection connection = DBConnectionFactory.getConnection();
		  // Get the parameters required for fetching a plan --> user_id and plan_id
		PoiBuilder poiBuilder = new PoiBuilder();
		JSONObject obj = new JSONObject();
		try {
	        poiBuilder.setPoiName(request.getParameter("poi_name").toString());
	        poiBuilder.setVisitingOrder(Integer.parseInt(request.getParameter("visiting_order")));
	        poiBuilder.setVenueId(request.getParameter("venue_id").toString());
	        poiBuilder.setPlanId(Integer.parseInt(request.getParameter("plan_id")));
	        Poi poi = poiBuilder.build();
	        if (connection.addPoint(poi)) {
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
