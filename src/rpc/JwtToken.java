package rpc;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.StyleConstants.CharacterConstants;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import db.UserDBConnection;
import entity.User;

public class JwtToken {
	private static final String KEY="team5-tp";
	private static final String ISSUER="team5";
	public static String createToken(User user) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create()
				.withIssuer(ISSUER)
				.withHeader(map)// header
				.withClaim("user_id", user.getId())// payload
				.sign(Algorithm.HMAC256(KEY));// º”√‹
		return token;
	}

	public static boolean verifyToken(String token) throws Exception {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(KEY)).build();
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> claims = jwt.getClaims();
		UserDBConnection connection = new db.mysql.UserDBConnection();
		try {
			User user = connection.getById(claims.get("user_id").asInt());
			if (user != null) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return false;
	}
	public static void main(String[] args) {
		UserDBConnection connection = new db.mysql.UserDBConnection();
		User user=connection.getById(1111);
		try {
		String token=createToken(user);
		System.out.print(verifyToken(token));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
}
