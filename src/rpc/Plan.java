package rpc;

import java.io.IOException;
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

@WebServlet("/plan")
public class Plan extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Plan() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     *
     *      GET method is used to fetch a specific plan
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the database reference
        PlanDBConnection connection = PlanDBConnectionFactory.getConnection();
        try {
            // 2 cases:
            //   1. get the plan with a specified plan id
            //   2. get all saved plans of a user if no plan id is given
            String requestedId = request.getParameter("id");
            int userId = JwtToken.getUserId(request);
            if (requestedId == null) {
                getAllPlans(request, response, connection, userId);
            } else {
                getPlanById(request, response, connection, Integer.parseInt(requestedId), userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    private void getAllPlans(HttpServletRequest request, HttpServletResponse response, PlanDBConnection connection, int userId)
            throws Exception, IOException {
        // Get all the saved plans of the user
        JSONArray array = new JSONArray();
        // Convert all saved plans into JSON object and put them all into the JSON array
        List<entity.Plan> allPlans = connection.getAllPlans(userId);
        for (entity.Plan plan : allPlans) {
            array.put(plan.toJSONObject());
        }
        RpcHelper.writeJSONArray(response, array);
        response.setStatus(200);
    }
    
    private void getPlanById(HttpServletRequest request, HttpServletResponse response, PlanDBConnection connection, int id, int userId)
            throws Exception, IOException {
        // Get the specified plan of the given id
        entity.Plan plan = connection.getPlan(id);
        RpcHelper.writeJSONObject(response,plan==null? (JSONObject)JSONObject.NULL: plan.toJSONObject());
        response.setStatus(200);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     *
     *      POST method is used to save a plan
     * 
     * @param request  the HTTP request that contains the plan info to be saved
     * @param response the HTTP response generated after saving the plan
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlanDBConnection connection = PlanDBConnectionFactory.getConnection();
        // Send a request to add a plan
        try {
            JSONObject obj = RpcHelper.readJSONObject(request);
            entity.Plan plan=entity.Plan.fromJSONObject(obj);
            int userId=JwtToken.getUserId(request);
            plan.setUserId(userId);
            // Insert the plan entry into the table
            if (connection.insertPlan(plan)) {
                // Upon successful insertion, the plan object will get an autogenerated id
                RpcHelper.writeJSONObject(response, plan.toJSONObject());
                response.setStatus(200);
            } else {
                response.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    /**
     * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
     * 
     *      DELETE method is used to delete a user plan
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlanDBConnection connection = PlanDBConnectionFactory.getConnection();
        // Send a request to delete a plan
        try {
            int id =Integer.parseInt(request.getParameter("id"));
            entity.Plan dbPlan=connection.getPlan(id);
            if (dbPlan.verify(request) && connection.deletePlan(id)) {
                response.setStatus(200);
            } else {
                response.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     * 
     *      PUT method is used to update an existing plan
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlanDBConnection connection = PlanDBConnectionFactory.getConnection();

        try {
            JSONObject obj = RpcHelper.readJSONObject(request);
            entity.Plan plan=entity.Plan.fromJSONObject(obj);
            entity.Plan dbPlan=connection.getPlan(plan.getId());
            if (dbPlan.verify(request) && connection.updatePlan(plan)) {
                response.setStatus(200);
            } else {
                response.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
