package com.sytecso.component.utility;

import java.io.IOException;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.sytecso.config.ConfigFiles;
import com.sytecso.config.PropertiesFile;
import com.sytecso.config.logger.SytecsoLogger;

public class SessionEmail {

	public static Session sessionEmail() {
		Properties prop = null;
		try {
			 prop= new PropertiesFile().getPropValues(new ConfigFiles().getStringSO()+"email.properties");
		} catch (IOException e) {
			SytecsoLogger.error("ERROR AL TRATAR DE LEER EL  ARCHIVO DE CONFIGURACIÓN DE LA  EMAIL", e);
		} 
		String userName=prop.getProperty("mail.username");
		String password=prop.getProperty("mail.password");
		prop.put("mail.smtp.host", prop.getProperty("mail.host"));
        prop.put("mail.smtp.port", prop.getProperty("mail.port"));
        prop.put("mail.smtp.auth", prop.getProperty("mail.auth"));
        prop.put("mail.smtp.starttls.enable",prop.getProperty("mail.enable")); //TLS
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(userName, password);
                    }
                });
	      
	      return session;
	}

	public static String getFromProp() {
		Properties prop = null;
		try {
			 prop= new PropertiesFile().getPropValues(new ConfigFiles().getStringSO()+"email.properties");
		} catch (IOException e) {
			SytecsoLogger.error("ERROR AL TRATAR DE LEER EL  ARCHIVO DE CONFIGURACIÓN DE LA  EMAIL", e);
		} 
		String from=prop.getProperty("mail.from");
		
		return from;
	}

	public static String getUrlVectorProp() {
		Properties prop = null;
		try {
			 prop= new PropertiesFile().getPropValues(new ConfigFiles().getStringSO()+"email.properties");
		} catch (IOException e) {
			SytecsoLogger.error("ERROR AL TRATAR DE LEER EL  ARCHIVO DE CONFIGURACIÓN DE LA  EMAIL", e);
		} 
		String url=prop.getProperty("mail.urlVector");
		
		return url;
	}

}
