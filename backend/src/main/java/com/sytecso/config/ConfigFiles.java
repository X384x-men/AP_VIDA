package com.sytecso.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.sytecso.component.CustomContext;

public class ConfigFiles {
	
	@Autowired
	CustomContext context;
	
    public final String getStringSO() {
    	String soName="";
    	String soRoute="";
    	soName=System.getProperty("os.name").toLowerCase();
    	 if (soName.indexOf("win") >= 0) {
            soRoute="C:/properties/";
         }else {
        	 //soRoute="/home/admin/propertiesDemoAP/";
        	 soRoute="/home/admin/properties/";
         }
    	 System.out.println("RUTA: "+soRoute);
    	return soRoute;
    }
}
