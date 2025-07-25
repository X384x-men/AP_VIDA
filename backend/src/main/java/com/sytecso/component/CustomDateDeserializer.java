package com.sytecso.component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sytecso.component.exceptions.SytecsoController;

@Component
public class CustomDateDeserializer extends StdDeserializer<Date> {

	private static final long serialVersionUID = 238212607414667942L;
	private static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

	public CustomDateDeserializer() {
		this(null);
	}

	public CustomDateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
		String date = jsonparser.getText();
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
		try {
			LocalDate.parse(date, formatterDate);
			return formatter.parse(date);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			return null;
		}
	}
}