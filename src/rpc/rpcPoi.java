package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.PoiDBConnection;
import db.PoiDBConnectionFactory;
import entity.Poi;
import entity.Poi.PoiBuilder;

/**
 * HttpServlet implementation class rpcPoi
 */
@WebServlet("/poi")
public class rpcPoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public rpcPoi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PoiDBConnection connection = PoiDBConnectionFactory.getConnection();
		  // Get the parameters required for fetching a plan --> user_id and plan_id
		List<Poi> poiList = new ArrayList<>();
		JSONArray result = new JSONArray();
		try {
			int planId = Integer.parseInt(request.getParameter("plan_id"));
			poiList = connection.getPoints(planId);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PoiDBConnection connection = PoiDBConnectionFactory.getConnection();
		  // Get the parameters required for fetching a plan --> user_id and plan_id
		PoiBuilder poiBuilder = new PoiBuilder();
		JSONObject obj = new JSONObject();
		try {
	        poiBuilder.setPoiName(request.getParameter("poi_name").toString());
	        poiBuilder.setVisitingOrder(Integer.parseInt(request.getParameter("visiting_order")));
	        poiBuilder.setVenueId(request.getParameter("venue_id").toString());
	        poiBuilder.setPlanId(Integer.parseInt(request.getParameter("plan_id")));
	        Poi poi = poiBuilder.build();
	        String poiId = connection.addPoint(poi);
	        if (!poiId.equals("")) {
	        	obj.put("result", "success");
	        	response.setStatus(200);
	        }
	        else {
	        	obj.put("result", "failure");
	        	response.setStatus(500);
	        }
	        obj.put("poi_id", poiId);
	        RpcHelper.writeJsonObject(response, obj);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        connection.close();
	    }
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PoiDBConnection connection = PoiDBConnectionFactory.getConnection();
		  // Get the parameters required for fetching a plan --> user_id and plan_id
		JSONObject obj = new JSONObject();
		try {
			int poiId = Integer.parseInt(request.getParameter("poi_id"));
	        int planId = Integer.parseInt(request.getParameter("plan_id"));
	        if (connection.deletePoint(poiId, planId)) {
	        	obj.put("result", "success");
	        	response.setStatus(200);
	        }
	        else {
	        	obj.put("result", "failure");
	        	response.setStatus(500);
	        }
	        RpcHelper.writeJsonObject(response, obj);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        connection.close();
	    }
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PoiDBConnection connection = PoiDBConnectionFactory.getConnection();
		  // Get the parameters required for fetching a plan --> user_id and plan_id
		JSONObject obj = new JSONObject();
		try {
	        int poiId = Integer.parseInt(request.getParameter("poi_id"));
	        int planId = Integer.parseInt(request.getParameter("plan_id"));
	        int visitingOrder = Integer.parseInt(request.getParameter("visiting_order"));
	        if (connection.updatePoint(poiId, planId, visitingOrder)) {
	        	obj.put("result", "success");
	        	response.setStatus(200);
	        }
	        else {
	        	obj.put("result", "failure");
	        	response.setStatus(500);
	        }
	        RpcHelper.writeJsonObject(response, obj);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        connection.close();
	    }
	}


}
