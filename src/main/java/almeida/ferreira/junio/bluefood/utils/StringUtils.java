package almeida.ferreira.junio.bluefood.utils;

import java.util.Collection;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StringUtils {

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}

		return str.trim().length() == 0;
	}

	public static String encrypt(String rawString) {
		PasswordEncoder enconder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return enconder.encode(rawString);
	}

	public static String concatenate(Collection<String> strings, String delimiter) {

		boolean first = true;

		StringBuilder sb = new StringBuilder();

		for (String str : strings) {
			
			if(!first) {
				sb.append(delimiter);
			}
			
			sb.append(str);
			first = false;
		}
		
		return sb.toString();

	}

}
