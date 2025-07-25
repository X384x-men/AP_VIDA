package com.sytecso.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomContext {

		@Value("${context.app_name}")
	    private String app_Name;
	    @Value("${context.server}")
	    private String server;
	    @Value("${context.properties}")
	    private String properties;
	    @Value("${context.http}")
	    private String http;
	    
		public String getApp_Name() {
			return app_Name;
		}
		public void setApp_Name(String app_Name) {
			this.app_Name = app_Name;
		}
		public String getServer() {
			return server;
		}
		public void setServer(String server) {
			this.server = server;
		}
		public String getProperties() {
			return properties;
		}
		public void setProperties(String properties) {
			this.properties = properties;
		}
		public String getHttp() {
			return http;
		}
		public void setHttp(String http) {
			this.http = http;
		}
	    
}
