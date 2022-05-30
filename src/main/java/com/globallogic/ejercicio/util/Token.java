package com.globallogic.ejercicio.util;

import javax.servlet.http.HttpServletRequest;

import com.globallogic.ejercicio.entity.User;
import com.nimbusds.jose.JOSEException;

public class Token {
	
	public static String createToken(HttpServletRequest requestServelt,User request) throws JOSEException {
		return AuthUtils.createToken(
				requestServelt.getScheme() + "//" + requestServelt.getServerName() + ":"
						+ requestServelt.getServerPort() + requestServelt.getContextPath(),
				new User(request.getEmail()), null);
	}

}
