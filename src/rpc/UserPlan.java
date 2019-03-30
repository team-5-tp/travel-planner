package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import db.PlanDBConnection;
import db.PlanDBConnectionFactory;

import entity.Plan;
import entity.Plan.PlanBuilder;

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
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     *
     * GET method is used to fetch a user's plan
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the database reference
        PlanDBConnection connection = PlanDBConnectionFactory.getConnection();
        try {
            // Get the parameters required for fetching a plan --> user_id and plan_id
//            int userId = Integer.parseInt(request.getParameter("user_id"));
            int planId = Integer.parseInt(request.getParameter("plan_id"));
            Plan plan = null;
            plan = connection.getPlan(planId);
            JSONObject object = plan.toJSONObject();
            RpcHelper.writeJSONObject(response, object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     *
     * POST method is used to save a user's plan
     * 
     * @param request   the HTTP request that contains the plan info to be saved
     * @param response  the HTTP response generated after saving the plan
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDBConnection connection = PlanDBConnectionFactory.getConnection();
        // Send a request to add a plan
        try {
            JSONObject requestBody = RpcHelper.readJSONObject(request);
            PlanBuilder builder = new PlanBuilder();
            builder.setPlanId(requestBody.getInt("plan_id"));
            builder.setPlanName(requestBody.getString("planname"));
            builder.setUserId(requestBody.getInt("user_id"));
            // Insert the plan entry into the table
            JSONObject result = new JSONObject();
            if (connection.insertPlan(builder.build())) {
                result.put("result", "SUCCESS");
            } else {
                result.put("result", "FAILED");
            }
            RpcHelper.writeJSONObject(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
    
    /**
     * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
     * 
     * DELETE method is used to delete a user plan
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDBConnection connection = PlanDBConnectionFactory.getConnection();
        // Send a request to delete a plan
        try {
            JSONObject requestBody = RpcHelper.readJSONObject(request);
//            PlanBuilder builder = new PlanBuilder();
//            builder.setPlanId(requestBody.getInt("plan_id"));
//            builder.setPlanName(requestBody.getString("planname"));
//            builder.setUserId(requestBody.getInt("user_id"));
            // Insert the plan entry into the table
            JSONObject result = new JSONObject();
            if (connection.deletePlan(requestBody.getInt("plan_id"))) {
                result.put("result", "SUCCESS");
            } else {
                result.put("result", "FAILED");
            }
            RpcHelper.writeJSONObject(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
