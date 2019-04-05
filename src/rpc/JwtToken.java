package rpc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import db.UserDBConnection;
import entity.User;

public class JwtToken {
    private static final String KEY = "team5-tp";
    private static final String AUTHORIZATION="Authorization";
    private static final String BEARER = "Bearer ";

    public static String createToken(User user) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map)// header
                .withClaim("user_id", user.getId())// payload
                .sign(Algorithm.HMAC256(KEY));// ����
        return token;
    }

    public static boolean verifyToken(HttpServletRequest request)  throws Exception {
    	String token= request.getHeader(AUTHORIZATION);
        if (token == null || !token.startsWith(BEARER)) {
            return false;
        }
        token = token.replace(BEARER, "");
        UserDBConnection connection = new db.mysql.UserMySQLConnection();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(KEY)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

    public static int getUserId(HttpServletRequest request) throws Exception {
        String token = request.getHeader(AUTHORIZATION);
        if (token == null || !token.startsWith(BEARER)) {
            return -1;
        }
        token = token.replace(BEARER, "");
        
        UserDBConnection connection = new db.mysql.UserMySQLConnection();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(KEY)).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            User user = connection.getById(claims.get("user_id").asInt());
            return user.getId();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return -1;
    }

    public static void main(String[] arges) {
        UserDBConnection connection = new db.mysql.UserMySQLConnection();
        User user = connection.getById(1);
        try {
            String token = createToken(user);
            System.out.println(token);
            //System.out.println(verifyToken(token));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

}
