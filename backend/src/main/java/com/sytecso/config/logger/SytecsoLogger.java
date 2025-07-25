package com.sytecso.config.logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.sytecso.config.ConfigFiles;


public class SytecsoLogger {

    private static final Logger LOGGER;
	    static {
	        LOGGER = Logger.getLogger("APLogger");
	        try {
	            InputStream configFile =new FileInputStream(new ConfigFiles().getStringSO()+"logger.properties"); 
	            LogManager.getLogManager().readConfiguration(configFile);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
    }

    private SytecsoLogger(){
    }


    public static void debug(String message){
        LOGGER.log(Level.FINE, message);
    }

    public static void info(String message){
        LOGGER.log(Level.INFO, message);
    }

    public static void error(String message, Throwable throwable){
        LOGGER.log(Level.SEVERE, message, throwable);
    }
    public static final void logInfo(Class<?> clazz,String methodName) {
    	Class<?> c = null;
		try {
			c=Class.forName(clazz.getName());
		} catch (Exception e) {
			error("An exception ocurred in SytecsoLogger  method logInfo exception type: ", e);			
		}
		for(Method m : c.getMethods()) {

			if(m.getDeclaringClass().getName().contains("com.sytecso")&& m.getName().equals(methodName)) {}
				if(m.getDeclaringClass().getName().contains("Impl")) {
					StringBuilder buffer = new StringBuilder();
					Integer index1=m.getDeclaringClass().getName().indexOf("imp");
					Integer index2 =m.getDeclaringClass().getName().indexOf("Impl");
					buffer.append(m.getDeclaringClass().getName().substring(0, index1));
					buffer.append(m.getDeclaringClass().getName().substring(index1+5,index2));
					info("INFO: The method " + methodName + " in " +buffer + " was executed succefully ");
				}else {
					info("INFO: The method " + methodName + " in " +m.getDeclaringClass() + " was executed succefully ");
				}
				break;
			}
		}
    
}
        
    
    
    

