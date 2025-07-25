/**
 * 
 * Created-By: Sytecso
 * Date:       02/01/2019
 */
package com.sytecso.component.utility;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
public class UtileriaValidaPatrones {
	public static boolean esNulo(String sDato) {
		return sDato==null;
	}
	public static boolean vaciOnulo(String sDato) {
		return esNulo(sDato)||sDato.length()<1;
	}
	public static boolean tamanio(String sDato, int largo) {
		return sDato.length()<=largo&&sDato.length()>=0;
	}
	public static boolean tamanio(String sDato, int largo, boolean nulo) {
		if(vaciOnulo(sDato))return !nulo;
		else return tamanio(sDato, largo);
	}
	public static boolean isNumeric(String sDato){
		try {
			Integer.parseInt(sDato);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	public static boolean isSegmentoCliente(String sDato){
		boolean valido = false;
		try {
			
			if(Integer.parseInt(sDato)==10||Integer.parseInt(sDato)==11) {
				Integer.parseInt(sDato);
				valido= true;	
			}
			return valido;
		} catch (NumberFormatException nfe){
			return valido;
		}
	}
	public static boolean formatoEntero(String sDato) {
		Pattern pEntero=Pattern.compile("\\d+");
		return pEntero.matcher(sDato).matches();
	}
	public static boolean formatoEntero(String sDato, boolean nulo) {
		if(vaciOnulo(sDato))return !nulo;
		else return formatoEntero(sDato);
	}
	public static boolean formatoFecha422(String sDato) {
		Pattern pFecha=Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		return pFecha.matcher(sDato).matches()&&esFechaValida(sDato,"yyyy-MM-dd");
	}
	public static boolean formatoFecha422(String sDato, boolean nulo) {
		if(vaciOnulo(sDato))return !nulo;
		else return formatoFecha422(sDato);
	}
	public static boolean formatoFecha224(String sDato) {
		Pattern pFecha=Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
		return pFecha.matcher(sDato).matches()&&esFechaValida(sDato,"dd-MM-yyyy");
	}
	public static boolean formatoFecha224(String sDato, boolean nulo) {
		if(vaciOnulo(sDato))return !nulo;
		else return formatoFecha224(sDato);
	}
	public static boolean formatoNumDecimal(String sDato) {
		Pattern pFlotante=Pattern.compile("^[-+]?[0-9]*\\.[0-9]+$");
		return formatoEntero(sDato)||pFlotante.matcher(sDato).matches();
	}
	public static boolean formatoNumDecimal(String sDato, boolean nulo) {
		if(vaciOnulo(sDato))return !nulo;
		else return formatoNumDecimal(sDato);
	}
	public static boolean esFechaValida(String vFecha,String sFormato) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat(sFormato);
		dateFormat.setLenient(false);
		try{
		    dateFormat.parse(vFecha.trim());
		}catch(ParseException pe){
		    return false;
		}
		    return true;
	}
	public static boolean isEmpty( String string ){
		if( string == null || string.trim().length() == 0 )
			return true;
		return false;
	}
	
	public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	public static String formatoA5Decimales(String sFormatea){
		return ""+new DecimalFormat("#,###.#####").format(Double.parseDouble(sFormatea));
	}
	public static String formatoANDecimales(String sPattern,String sFormatea){
		return ""+new DecimalFormat(sPattern).format(Double.parseDouble(sFormatea));
	}
	
	public  static String formatTimestampJava8(Timestamp timestamp) {
	        if (timestamp == null) {
	            return null; // or throw an exception, depending on your requirements
	        }

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        return formatter.format(timestamp.toLocalDateTime());
	 }
	
}//   END   public class UtileriaValidaPatrones
