package be.project.utils;

import java.util.regex.Pattern;

public class Utils {
	public static boolean emailIsValid(String email) {
    	return Pattern.compile("^[A-Za-z0-9._+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    		      .matcher(email)
    		      .matches();
	}
	
	public static boolean allFieldsAreFilled(String param1, String param2) {
    	if(param1!=null && !param1.isEmpty() && !param1.equals("") && param2!=null && !param2.isEmpty() && !param2.equals("")) {
    		return true;
    	}
    	return false;
    }
	
	public static boolean allFieldsAreFilled(String param1, String param2, String param3, String param4) {
    	if(param1!=null && !param1.isEmpty() && !param1.equals("") && param2!=null && !param2.isEmpty() && !param2.equals("") &&
    			param3!=null && !param3.isEmpty() && !param3.equals("") && param4!=null && !param4.isEmpty() && !param4.equals("")) {
    		return true;
    	}
    	return false;
    }
	
	public static boolean pwdValid(String pwd) {
		if(pwd.length()>=5 && pwd.length()<=50) {
			return true;
		}
		return false;
	}
}
