package com.sytecso.component.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sytecso.component.exceptions.SytecsoController;

public class UtileriaFechas {

	private static Pattern datePattern = Pattern
			.compile("(0?[1-9]|[12][0-9]|3[01])[/|-](0?[1-9]|1[012])[/|-]((19|20)\\d\\d)");

	private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	private static final String DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy";

	private UtileriaFechas() {
		throw new IllegalStateException("UtileriaFechas");
	}

	/**
	 * Este metodo valida el formato de la fecha, asi como, que la fecha este en un
	 * valido
	 * 
	 * @param date
	 * @return false si la fecha es invalida o true su la fecha es valida
	 * 
	 */
	public static boolean validateDateFormat(String date) {
		Matcher mtch = datePattern.matcher(date);
		if (mtch.matches()) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY);
			simpleDateFormat.setLenient(false);
			try {
				simpleDateFormat.parse(date.replace("/", "-"));
				return true;
			} catch (Exception e) {
				throw new DateTimeException("Fecha invalida");
			}
		}
		return false;
	}

	/**
	 * Este metodo valida el formato de la fecha, asi como, que la fecha este en un
	 * valido
	 * 
	 * @param date
	 * @return false si la fecha es invalida o true su la fecha es valida
	 * 
	 */
	public static boolean validateDateFormat(String date, Pattern pattern, String strFormat) {
		Matcher mtch = pattern.matcher(date);
		if (mtch.matches()) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
			simpleDateFormat.setLenient(false);
			try {
				if (date.contains("/"))
					simpleDateFormat.parse(date.replace("/", "-"));
				else
					simpleDateFormat.parse(date);
				return true;
			} catch (Exception e) {
				throw new DateTimeException("Fecha invalida");
			}
		}
		return false;
	}

	/**
	 * Este metodo compara dos fechas, en la cual la fecha de fin no puede ser menor
	 * que la de inicio
	 * 
	 * @param dateIni
	 * @param dateFn
	 * @return false si la fecha de fin es menor que la de inicio o true si la fecha
	 *         de fin es mayor que la de inicio
	 */
	public static boolean compareDates(String dateIni, String dateFn) {
		Integer[] diff = calculateLocalDateBettwenDateDifferenceInMonths(dateIni, dateFn);
		if (diff[0] == 0 && diff[1] < 0)
			return false;
		return diff[0] >= 0;
	}

	/**
	 * Este metodo comprueba la longitud de una fecha, si esta es igual a 6, se
	 * agrega 01 al inicio, si no es asi, se regresa la fecha sin cambios
	 * 
	 * @param fecha
	 * @return fecha formato dd-mm-yyyy
	 */
	private static String agregaCeroAFecha(String fecha) {
		if (fecha.length() == 7)
			return "01-" + fecha;
		return fecha;
	}

	/**
	 * Este metodo convierte una fecha en formato dd-mm-yyyy o dd-yyyy a yyyy-dd-mm
	 * Si el formato es yyyy-dd-mm retorna la fecha sin hacer ninguna modificacion
	 * Si la fecha es un formato mm-yyyy se retornada una fecha con formato
	 * yyyy-mm-dd con dia 01 ejemplo 2018-12-01
	 * 
	 * @param fecha
	 * @return Feha en formato yyyy-mm-dd
	 */
	public static String convertDateFormat(String fecha) {
		String newDate = agregaCeroAFecha(fecha);
		if (fecha.contains("/"))
			newDate = fecha.replace("/", "-");
		int position = newDate.indexOf('-');
		if (position == 2) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY);
				SimpleDateFormat formatter2 = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
				Date date = formatter.parse(newDate);
				return formatter2.format(date);
			} catch (ParseException e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return newDate;
	}

	/**
	 * Este metodo convierte una fecha en formato yyyy-mm-dd a yyyy-dd-mm Si el
	 * formato es yyyy-dd-mm retorna la fecha sin hacer ninguna modificacion Si la
	 * fecha es un formato mm-yyyy se retornada una fecha con formato yyyy-mm-dd con
	 * dia 01 ejemplo 2018-12-01
	 * 
	 * @param fecha
	 * @return Feha en formato dd-mm-yyyy
	 */
	public static String convertDateFormatYearMonthDay(String fecha) {
		String newDate = agregaCeroAFecha(fecha);
		if (fecha.contains("/"))
			newDate = fecha.replace("/", "-");
		int position = newDate.indexOf('-');
		if (position == 4) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
				SimpleDateFormat formatter2 = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY);
				Date date = formatter.parse(newDate);
				return formatter2.format(date);
			} catch (ParseException e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return newDate;
	}

	/**
	 * Este metodo convierte una fecha en formato yyyy-mm-dd a yyyy-dd-mm Si el
	 * formato es yyyy-dd-mm retorna la fecha sin hacer ninguna modificacion Si la
	 * fecha es un formato mm-yyyy se retornada una fecha con formato yyyy-mm-dd con
	 * dia 01 ejemplo 2018-12-01
	 * 
	 * @param fecha
	 * @return Feha en formato dd-mm-yyyy
	 */
	public static String convertDateFormatYearMonthDayTT(String fecha, String actDateFormat, String newDateFormat) {
		String newDate = agregaCeroAFecha(fecha);
		if (fecha.contains("/"))
			newDate = fecha.replace("/", "-");
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(actDateFormat);
			SimpleDateFormat formatter2 = new SimpleDateFormat(newDateFormat);
			Date date = formatter.parse(newDate);
			return formatter2.format(date);
		} catch (ParseException e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return newDate;
	}

	/**
	 * Este metodo convierte una fecha en String a un LocalDate
	 * 
	 * @param date
	 * @return LocalDate en formato yyyy-mm-dd
	 */
	private static LocalDate convertDateToLocalDate(String date) {
		Integer year = Integer.parseInt(date.substring(0, 4));
		Integer month = Integer.parseInt(date.substring(5, 7));
		Integer day = Integer.parseInt(date.substring(8, 10));
		return LocalDate.of(year, month, day);
	}

	/**
	 * Este metodo calcula la diferencia en meses entre una fecha y la fecha actual
	 * 
	 * @param fechaIni
	 * @param fechaFn
	 * @return Integer[] el indice 0 contiene la diferencia en meses y el indice 1
	 *         contiene los dias que hay de diferencia, el indice 2 contiene los
	 *         anios que existen entre las dos fechas
	 */
	public static Integer[] calculateLocalDateBettwenDateDifferenceInMonths(String fechaIni, String fechaFn) {
		LocalDate bDay = convertDateToLocalDate(convertDateFormat(fechaIni));
		LocalDate bDay2 = convertDateToLocalDate(convertDateFormat(fechaFn));
		return calculateMonthsDiffrence(bDay2, bDay);
	}

	/**
	 * Este metodo calcula la diferencia en meses, dias y anios que hay entre dos
	 * fechasfechas
	 * 
	 * @param fechaFin
	 * @param fechaIni
	 * @return Integer[] el indice 0 contiene la diferencia en meses y el indice 1
	 *         contiene los dias que hay de diferencia el indice 2 contiene los
	 *         anios que hay entre esas fechas
	 */
	private static Integer[] calculateMonthsDiffrence(LocalDate fechaFin, LocalDate fechaIni) {
		Period age = Period.between(fechaIni, fechaFin);
		Integer[] data = new Integer[3];
		int years = age.getYears();
		int months = age.getMonths();
		int days = age.getDays();
		data[0] = (months + (years * 12));
		data[1] = days;
		data[2] = years;
		return data;
	}

	/**
	 * Este metodo calcula la diferencia en meses entre una fecha y la fecha actual
	 * 
	 * @param date
	 * @return Integer[] diferencia en meses, fecha actual y fecha anterior, indice
	 *         0 meses indice 1 dias
	 */
	public static Integer[] calculateLocalDateBettwenDateDifferenceInMonths(String date) {
		LocalDate endDate = convertDateToLocalDate(date);
		LocalDate initDate = LocalDate.now();
		return calculateMonthsDiffrence(endDate, initDate);
	}

	/**
	 * Esta metodo obtiene la difrencia en meses, entre una fecha de inicio y una
	 * fecha fin en formato yyyy-mm-dd
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return diferencia en meses
	 */
	public static Integer differenceBettwenDates1(String fechaInicio, String fechaFin) {
		Integer differenceFechaInicioAndActualDate = calculateLocalDateBettwenDateDifferenceInMonths(fechaInicio)[0];
		Integer differenceFechaInicioAndFechaFinal = calculateLocalDateBettwenDateDifferenceInMonths(fechaInicio,
				fechaFin)[0];
		return differenceFechaInicioAndActualDate <= differenceFechaInicioAndFechaFinal
				? differenceFechaInicioAndActualDate
				: differenceFechaInicioAndFechaFinal;
	}

	/**
	 * Este metodo calcula el tiempo que tarda en ejecutarse un metodo
	 * 
	 * @param startDate
	 * @param endDate
	 * @param method
	 * @return String
	 */
	public static String durationMethod(Date startDate, Date endDate, String method) {
		long diff = endDate.getTime() - startDate.getTime();
		long millis = TimeUnit.MILLISECONDS.toMillis(diff);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
		return "La duracion del metodo " + method + " fue:  Minutos: " + minutes + " Segundos: " + seconds
				+ " Millisegundos " + millis;
	}

	/**
	 * Este metodo calcula los dias restantes que tienen un mes o meses
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return dias que exiten entre un mes o meses
	 */
	public static String calcularMesesAFecha(Date fechaInicio, Date fechaFin) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(fechaInicio);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(fechaFin);
		int startMes = (startCalendar.get(Calendar.YEAR) * 12) + startCalendar.get(Calendar.MONTH);
		int endMes = (endCalendar.get(Calendar.YEAR) * 12) + endCalendar.get(Calendar.MONTH);
		int dias = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);
		int diffMonth = endMes - startMes;
		return diffMonth + "," + dias + "," + (startCalendar.get(Calendar.MONTH) + 1) + ","
				+ (endCalendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * Este metodo convierte una fecha de tipo String en formato yyyy-dd-mm a un
	 * objeto tipo Date
	 * 
	 * @param fecha
	 * @return Date
	 */
	public static Date convertDateToGregorian(String fecha) {
		int anio = Integer.parseInt(fecha.substring(0, 4));
		int mes = Integer.parseInt(fecha.substring(5, 7));
		int dia = Integer.parseInt(fecha.substring(8, 10));
		return new GregorianCalendar(anio, (mes - 1), dia).getTime();

	}

	/**
	 * Este metodo obtiene fecha en un formato dado, puede ser yyyy-dd-mm o
	 * dd-mm-yyyy
	 * 
	 * @param dateFormat
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat generateDateFormat(String dateFormat) {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		simpleDateFormat.setLenient(false);
		simpleDateFormat.format(date);
		return simpleDateFormat;
	}

	/**
	 * Este metodo convierte una fecha en formato yyyy-mm-dd a calendar
	 * 
	 * @param date
	 * @return regresa una fecha en Calendar, con el ani,mes y dia pasados
	 * 
	 */
	public static Calendar convertStringDateToCalendar(String date) {
		SimpleDateFormat ss = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		try {
			ss.getCalendar().setLenient(false);
			ss.parse(date);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return ss.getCalendar();
	}

	/**
	 * Esta metodo genera una fecha en un formato dado
	 * 
	 * @param formato ejemplo: yyyy-dd-mm
	 * @return String fecha en el formato dato
	 */
	public static String generateDate(String dateFormat) {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(date);
	}

	/**
	 * Este metodo comprueba si una fecha tiene el formato yyyy-dd-mm HH:MM:SS y la
	 * retorna en formato yyyy-mm-dd
	 * 
	 * @param date
	 * @return String en formato yyyy-mm-dd
	 */
	public static String cmpDateHaveTimeMillies(String date) {
		return date.length() > 10 ? date.substring(0, 10) : date;
	}

	/**
	 * Este metodo calcula la fecha anterior, dado el mes de diferencia pasado y
	 * regresa la una fecha en formato yyy-MM-dd con el ultimo dia del mes de esa
	 * fecha
	 */
	public static String getDateBeforeCurrentDate(Integer month) {
		SimpleDateFormat simpleDateFormat = generateDateFormat(DATE_FORMAT_YYYY_MM_DD);
		Calendar calendar = simpleDateFormat.getCalendar();
		calendar.add(Calendar.MONTH, (-month));
		return addCeroToDateAndLastDay(calendar, DATE_FORMAT_YYYY_MM_DD);

	}

	/**
	 * Este metodo genera una fecha apartir de el numero de meses pasados, formato
	 * de fecha yyyy-mm-dd
	 * 
	 * @param month
	 * @return fecha en formato yyyy-mm-dd
	 */
	public static String getDateAfterCurrentDate(Integer month) {
		SimpleDateFormat simpleDateFormat = generateDateFormat(DATE_FORMAT_YYYY_MM_DD);
		Calendar calendar = simpleDateFormat.getCalendar();
		calendar.add(Calendar.MONTH, month);
		return addCeroToDateAndLastDay(calendar, DATE_FORMAT_YYYY_MM_DD);

	}

	/**
	 * Este metodo agrega ceros restantes a una fecha para tener un formato
	 * yyyy-mm-dd
	 * 
	 * @param Calendar fecha
	 * @param String   dateFormat
	 * @return fecha en formato yyyy-mm-dd
	 */
	public static String addCeroToDateAndLastDay(Calendar fecha, String dateFormat) {
		StringBuilder builder = new StringBuilder();
		int lastDayOfMonth = fecha.getActualMaximum(Calendar.DAY_OF_MONTH);
		int month = fecha.get(Calendar.MONTH);
		String agregaDayCero = lastDayOfMonth >= 10 ? "" + lastDayOfMonth : "0" + lastDayOfMonth;
		String agregaMesCero = month >= 9 ? "" + (month + 1) : "0" + (month + 1);
		if (dateFormat.equals(DATE_FORMAT_YYYY_MM_DD))
			return builder.append(fecha.get(Calendar.YEAR)).append("-").append(agregaMesCero).append("-")
					.append(agregaDayCero).toString();
		return builder.append(agregaDayCero).append("-").append(agregaMesCero).append("-")
				.append(fecha.get(Calendar.YEAR)).toString();
	}

	/**
	 * ESte metodo calcula la fecha de inicio y de fin, apartir de una fecha dada y
	 * un mes dado, en formato yyyy-mm-dd
	 * 
	 * @param date
	 * @param months
	 * @return arreglo String indice 0 = fecha inicio, indice 1 = fecha fin
	 */
	public static String[] addMonthsToDateAndGetInitialDayAndLastDay(String date, Integer months) {
		Calendar calendar = convertStringDateToCalendar(date);
		calendar.add(Calendar.MONTH, months);
		String fechaFin = addCeroToDateAndLastDay(calendar, DATE_FORMAT_YYYY_MM_DD);
		String fechaInicio = fechaFin.substring(0, 8).concat("01");
		return new String[] { fechaInicio, fechaFin };
	}

	/**
	 * Esta funcion agrega ceros al mes o dia, si es que es necesario, para obtener
	 * una fecha en formato yyy-mm-dd
	 * 
	 * @param fecha      Instancia de un calendar
	 * @param dateFormat fecha a calcular
	 * @return fecha en formato yyyy-mm-dd
	 */
	public static String addCeroToDate(Calendar fecha, String dateFormat) {
		StringBuilder builder = new StringBuilder();
		String agregaDayCero = fecha.get(Calendar.DAY_OF_MONTH) >= 10 ? "" + fecha.get(Calendar.DAY_OF_MONTH)
				: "0" + fecha.get(Calendar.DAY_OF_MONTH);
		String agregaMesCero = (fecha.get(Calendar.MONTH) + 1) >= 10 ? "" + (fecha.get(Calendar.MONTH) + 1)
				: "0" + (fecha.get(Calendar.MONTH) + 1);
		if (dateFormat.equals(DATE_FORMAT_YYYY_MM_DD))
			return builder.append(fecha.get(Calendar.YEAR)).append("-").append(agregaMesCero).append("-")
					.append(agregaDayCero).toString();
		return builder.append(agregaDayCero).append("-").append(agregaMesCero).append("-")
				.append(fecha.get(Calendar.YEAR)).toString();
	}

	/**
	 * Este metodo concatena un mes dado a la fecha actual y con el primer dia de
	 * esa fecha en formato yyyy-mm-dd
	 * 
	 * @param month mes
	 * @return fecha en formato yyyy-mm-dd
	 **/
	public static String appendMonthToDate(String month) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return String.valueOf(year).concat("-").concat(month).concat("-01");
	}

	/**
	 * **/
	public static String getMonthName(Integer month) {
		String[] months = { "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic" };
		return month < 0 ? "" : months[month - 1];
	}

	/***
	 * Este metodo regresa el mes actual
	 */
	public static String getCurrentMonth() {
		YearMonth yearMonth = YearMonth.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
		return yearMonth.format(formatter);
	}

	/**
	 * Este metodo regresa una fecha en segundos dado un formato 
	 * @param date
	 * @param dateFormat
	 * @return fecha en segundos
	 * */
	public static long getDateTimeSecondsFromStringDate(String date,String dateFormat) {
		try {
			return new SimpleDateFormat(dateFormat).parse(date).getTime()/1000;
		} catch (ParseException e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return 0;
	}

	
}
