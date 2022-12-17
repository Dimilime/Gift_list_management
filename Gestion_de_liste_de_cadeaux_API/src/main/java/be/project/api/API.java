package be.project.api;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class API{
	
	public API() {
		// TODO Auto-generated constructor stub
	}
	protected static String getApiKey() {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			return (String) env.lookup("apiKey");
		} catch (NamingException e) {
			return "";
		}
	}
	
	protected static String getBaseUri() {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			return (String) env.lookup("baseURI");
		} catch (NamingException e) {
			return "";
		}
	}

}
