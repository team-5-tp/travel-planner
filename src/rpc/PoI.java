package rpc;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.PlanDBConnection;
import db.PlanDBConnectionFactory;
import db.PoIDBConnection;
import db.PoIDBConnectionFactory;

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoIDBConnection poIDBConnection = PoIDBConnectionFactory.getConnection();
		PlanDBConnection planDBConnection = PlanDBConnectionFactory.getConnection();
		try {
			JSONObject obj = RpcHelper.readJSONObject(request);
			entity.PoI poi = entity.PoI.fromJSONObject(obj);
			entity.Plan plan = planDBConnection.getPlan(poi.getPlanId());
			if (plan.verify(request) && poIDBConnection.addPoint(poi)) {
				RpcHelper.writeJSONObject(response, poi.toJSONObject());
				response.setStatus(200);
			} else {
				response.setStatus(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			poIDBConnection.close();
			planDBConnection.close();
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoIDBConnection poIDBConnection = PoIDBConnectionFactory.getConnection();
		PlanDBConnection planDBConnection = PlanDBConnectionFactory.getConnection();
		try {
//			int id = Integer.parseInt(request.getParameter("id"));
//			int planId = Integer.parseInt(request.getParameter("plan_id"));
			JSONObject obj = RpcHelper.readJSONObject(request);
			int planId = obj.getInt("plan_id");
			System.out.println("planId = " + planId);
//			entity.PoI poi = poIDBConnection.getPoI(id);
			entity.Plan plan = planDBConnection.getPlan(planId);
			if (plan.verify(request) && poIDBConnection.deletePoints(planId)) {
				response.setStatus(200);
			} else {
				response.setStatus(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			poIDBConnection.close();
			planDBConnection.close();
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoIDBConnection poIDBConnection = PoIDBConnectionFactory.getConnection();
		PlanDBConnection planDBConnection = PlanDBConnectionFactory.getConnection();
		try {
			JSONObject obj = RpcHelper.readJSONObject(request);
			entity.PoI poi = entity.PoI.fromJSONObject(obj);
			entity.Plan plan = planDBConnection.getPlan(poi.getPlanId());
			if (plan.verify(request) && poIDBConnection.updatePoint(poi)) {
				response.setStatus(200);
			} else {
				response.setStatus(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			poIDBConnection.close();
			planDBConnection.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoIDBConnection poIDBConnection = PoIDBConnectionFactory.getConnection();
		PlanDBConnection planDBConnection = PlanDBConnectionFactory.getConnection();
		try {

			int planId = Integer.parseInt(request.getParameter("plan_id"));
			entity.Plan plan = planDBConnection.getPlan(planId);
			if (plan.verify(request)) {
				List<entity.PoI> poiList = poIDBConnection.getPoints(planId);
				JSONArray result = new JSONArray();
				for (entity.PoI poi : poiList) {
					result.put(poi.toJSONObject());
				}
				RpcHelper.writeJSONArray(response, result);
				response.setStatus(200);
			} else {
				response.setStatus(500);
			}
		} catch (Exception e) {
			response.setStatus(500);
			e.printStackTrace();
		} finally {
			poIDBConnection.close();
			planDBConnection.close();
		}
	}

}
