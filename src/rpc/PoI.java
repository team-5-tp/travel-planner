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

import db.PoIDBConnection;
import db.PoIDBConnectionFactory;

import entity.PoI.PoIBuilder;

/**
 * Servlet implementation class PoI
 */
@WebServlet("/poi")
public class PoI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PoI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PoIDBConnection connection = PoIDBConnectionFactory.getConnection();
        List<entity.PoI> poiList = new ArrayList<>();
        JSONArray result = new JSONArray();
        try {
            int planId = Integer.parseInt(request.getParameter("plan_id"));
            poiList = connection.getPoints(planId);
            for (entity.PoI poi : poiList) {
                result.put(poi.toJSONObject());
            }
            RpcHelper.writeJSONArray(response, result);
            response.setStatus(200);
        } catch (Exception e) { 
        	response.setStatus(500);
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PoIDBConnection connection = PoIDBConnectionFactory.getConnection();
        PoIBuilder poiBuilder = new PoIBuilder();
        JSONObject obj = new JSONObject();
        
        try {
            poiBuilder.setName(request.getParameter("name").toString());
            poiBuilder.setVisitingOrder(Integer.parseInt(request.getParameter("visiting_order")));
            poiBuilder.setVenueId(request.getParameter("venue_id").toString());
            poiBuilder.setPlanId(Integer.parseInt(request.getParameter("plan_id")));
            entity.PoI poi = poiBuilder.build();
            String id = connection.addPoint(poi);
            // TODO(MZ): id --> int
            if (!id.equals("")) {
                obj.put("result", "success");
                response.setStatus(200);
            }
            else {
                obj.put("result", "failure");
                response.setStatus(500);
            }
            obj.put("id", id);
            RpcHelper.writeJSONObject(response, obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PoIDBConnection connection = PoIDBConnectionFactory.getConnection();
        JSONObject obj = new JSONObject();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int planId = Integer.parseInt(request.getParameter("plan_id"));
            int visitingOrder = Integer.parseInt(request.getParameter("visiting_order"));
            if (connection.updatePoint(id, planId, visitingOrder)) {
                obj.put("result", "success");
                response.setStatus(200);
            }
            else {
                obj.put("result", "failure");
                response.setStatus(500);
            }
            RpcHelper.writeJSONObject(response, obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    PoIDBConnection connection = PoIDBConnectionFactory.getConnection();
	    JSONObject obj = new JSONObject();
	    
	    try {
	        int id = Integer.parseInt(request.getParameter("id"));
	        int planId = Integer.parseInt(request.getParameter("plan_id"));
	        if (connection.deletePoint(id, planId)) {
	            obj.put("result", "success");
	            response.setStatus(200);
	        } else {
	            obj.put("result", "failure");
	            response.setStatus(500);
	        }
	        RpcHelper.writeJSONObject(response, obj);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        connection.close();
	    }
	}
}
