package com.sytecso.component.exceptions;

import java.lang.reflect.Method;

import com.sytecso.config.logger.SytecsoLogger;

public class SytecsoExceptions {
	public static void logClassAndMethodWithException(Exception e){
		Integer indice =0;
		for (StackTraceElement ste : e.getStackTrace()){
			String basePackage =ste.getClassName();
			if(basePackage.contains("com.sytecsosmartbilling")) {
				break;
			}	
			indice+=1;
		}
		
		StackTraceElement ste = e.getStackTrace()[indice];
		Class<?> c = null;
		try {
			 c = Class.forName(ste.getClassName());
		} catch (Exception e2) {
			SytecsoLogger.error("An exception ocurred in SmartBillingException type", e2);
		}
		
		String mname = ste.getMethodName();


		if ("<init>".equals(mname)) {
		    c.getConstructors(); 
		} else if ("<cinit>".equals(mname)) {
			 SytecsoLogger.error("An exception ocurred in a satic block: ", e);	
		} else {
		    for (Method m : c.getMethods()) {
		        if (m.getName().equals(mname)) {
		        	if(m.getDeclaringClass().getName().contains("Impl")) {
						StringBuilder buffer = new StringBuilder();
						Integer index1=m.getDeclaringClass().getName().indexOf("imp");
						Integer index2 =m.getDeclaringClass().getName().indexOf("Impl");
						buffer.append(m.getDeclaringClass().getName().substring(0, index1));
						buffer.append(m.getDeclaringClass().getName().substring(index1+5,index2));
			            SytecsoLogger.error("ERROR: An exception ocurred in method: "+ m.getName() +" in class: "+ buffer+ " exception type: ", e);
			            System.err.println("ERROR: An exception ocurred in method: " + m.getName() + " in class "+buffer+e);
					}else {
			            SytecsoLogger.error("ERROR: An exception ocurred in method: " + m.getName() +" in class: "+ m.getDeclaringClass() +" type exception ", e);
			            System.err.println("ERROR: An exception ocurred in method: " + m.getName() + " in class: "+m.getDeclaringClass()+" type exception: "+ e);
					}
		        	break;
		        }else {
		        	SytecsoLogger.error("ERROR: An exception ocurred in method: "+ m.getName() +" And sub method: "+mname+" in class: "+ m.getDeclaringClass()+ " exception type: ", e);
		        	System.err.println("ERROR: An exception ocurred in method: "+ m.getName() +" And sub method: "+mname+" in class: "+ m.getDeclaringClass()+ " exception type: "+ e);
		        	break;
		        }
		    }
		}

	}

}
