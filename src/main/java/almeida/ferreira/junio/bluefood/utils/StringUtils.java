package almeida.ferreira.junio.bluefood.utils;

public class StringUtils {
	
	public static boolean isEmpty(String str) {
		if(str == null) {
			return true;
		}
		
		return str.trim().length() == 0;
	}
	
	public static String encypt(String rawString) {
		//PasswordEncoder enconder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//return enconder.encode(rawString);
		
		return rawString;
	}

}
