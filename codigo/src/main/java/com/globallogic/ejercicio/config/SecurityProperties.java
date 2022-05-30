package com.globallogic.ejercicio.config;

public class SecurityProperties {

	public static  String TOKEN_SECRET; //= "918e8ba5089da22616a95b087daf97f08f63c66d39113ae816d5eb053b053c3b";

	public static String getTOKEN_SECRET() {
		return TOKEN_SECRET;
	}

	public static void setTOKEN_SECRET(String tOKEN_SECRET) {
		TOKEN_SECRET = tOKEN_SECRET;
	}
}
