package com.globallogic.ejercicio.util;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import com.globallogic.ejercicio.entity.User;
import com.google.gson.Gson;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class AuthUtils {
	
	private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
	private static final String TOKEN_SECRET = "abc";
	public static final String AUTH_HEADER = "X-Auth-Token";
	
	public static String createToken(String host, User sub, HashMap<String, Object> customClaims) throws JOSEException {
        JWTClaimsSet claim = new JWTClaimsSet();
        claim.setSubject(new Gson().toJson(sub));
        claim.setIssuer(host);
        claim.setIssueTime(DateTime.now().toDate()); 
        claim.setExpirationTime(DateTime.now().plusMinutes(1000000000).toDate());
        if(customClaims != null) {
            claim.setCustomClaims(customClaims);
        }   
        JWSSigner signer = new MACSigner(TOKEN_SECRET);
        SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
        jwt.sign(signer);
        return jwt.serialize();
    }
	public static boolean validate(ReadOnlyJWTClaimsSet cs) throws ParseException, JOSEException {
		if (cs.getExpirationTime().before(new Date())) {
			return false;
		} else {
			return true;
		}
	}
	public static ReadOnlyJWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
		SignedJWT signedJWT = SignedJWT.parse(authHeader);
		if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
			return signedJWT.getJWTClaimsSet();
		} else {
			throw new JOSEException("Error al verificar la firma");
		}
	}
	public static boolean validate(String authToken) throws ParseException, JOSEException {
		ReadOnlyJWTClaimsSet cs = decodeToken(authToken);
		if (cs.getExpirationTime().before(new Date())) {
			return false;
		} else {
			return true;
		}
	}
	public static User getUser(HttpServletRequest request) throws Exception {
	    try {
	      ReadOnlyJWTClaimsSet cs = decodeToken(request.getHeader(AUTH_HEADER));
	      if (validate(cs)) {
	         return new Gson().fromJson(cs.getSubject(), User.class);
	      } else {
	         throw new LoginException();
	      }
	    } catch (ParseException e) {
	         e.printStackTrace();
	         return null;
	    } catch (JOSEException e) {
	         e.printStackTrace();
	         return null;
	    }
	 }
	public static User getUser(String authToken) {
		try {
			ReadOnlyJWTClaimsSet cs = decodeToken(authToken);
			User user = new User();
			user.setEmail(cs.getSubject());
			return user;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (JOSEException e) {
			e.printStackTrace();
			return null;
		}
	}
}
