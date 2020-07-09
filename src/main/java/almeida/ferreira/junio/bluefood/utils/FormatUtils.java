package almeida.ferreira.junio.bluefood.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
	
	private static final Locale LOCALE = new Locale("pt", "BR");
	
	public static NumberFormat getCurrencyFormater() {
		NumberFormat nf = NumberFormat.getNumberInstance(LOCALE);
		
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);
		
		return nf;
	}
	
	public static String formatCurrency(BigDecimal source) {
		
		if(source == null) {
			return null;
		}
		
		return getCurrencyFormater().format(source);
	}
}
