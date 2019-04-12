package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.UserDBConnectionFactory;
import db.UserDBConnection;

/**
 * Servlet implementation class Register
 */
@WebServlet("/user")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     * 
     *      PUT method is used to update an existing plan
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDBConnection connection = UserDBConnectionFactory.getConnection();

        try {
            JSONObject obj = RpcHelper.readJSONObject(request);
            entity.User user=entity.User.fromJSONObject(obj);
            if (connection.update(user)) {
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDBConnection connection = UserDBConnectionFactory.getConnection();
        try {
            int id =Integer.parseInt(request.getParameter("id"));
            if (connection.delete(id)) {
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
