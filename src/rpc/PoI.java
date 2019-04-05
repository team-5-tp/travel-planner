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

/**
 * Servlet implementation class PoI
 */
@WebServlet("/poi/")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoIDBConnection connection = PoIDBConnectionFactory.getConnection();
		try {
			int planId = Integer.parseInt(request.getParameter("plan_id"));
			List<entity.PoI> poiList = connection.getPoints(planId);
			JSONArray result = new JSONArray();
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoIDBConnection connection = PoIDBConnectionFactory.getConnection();
		try {
			JSONObject obj = RpcHelper.readJSONObject(request);
			entity.PoI poi = entity.PoI.fromJSONObject(obj);
			if (connection.addPoint(poi)) {
				RpcHelper.writeJSONObject(response, poi.toJSONObject());
				response.setStatus(200);
			} else {
				response.setStatus(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoIDBConnection connection = PoIDBConnectionFactory.getConnection();

		try {
			JSONObject obj = RpcHelper.readJSONObject(request);
			entity.PoI poi = entity.PoI.fromJSONObject(obj);
			if (connection.updatePoint(poi)) {
				response.setStatus(200);
			} else {
				response.setStatus(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoIDBConnection connection = PoIDBConnectionFactory.getConnection();
		JSONObject obj = new JSONObject();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			if (connection.deletePoint(id)) {
				response.setStatus(200);
			} else {
				response.setStatus(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
}
