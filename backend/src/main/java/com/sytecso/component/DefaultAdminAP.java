package com.sytecso.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdminAP {

		@Value("${defaultAdminAP.nombreRol}")
	    private String nombreRolAdminAP;
	    @Value("${defaultAdminAP.usuario}")
	    private String usuarioAdminAP;
	    @Value("${defaultAdminAP.password}")
	    private String passwordAdminAP;
	    @Value("${defaultAdminAP.descripcionRol}")
	    private String descripcionRol;
	    @Value("${defaultAdminAP.tipoAcceso}")
	    private String tipoAcceso;
	    
	    
		public String getTipoAcceso() {
			return tipoAcceso;
		}
		public void setTipoAcceso(String tipoAcceso) {
			this.tipoAcceso = tipoAcceso;
		}
		public String getNombreRolAdminAP() {
			return nombreRolAdminAP;
		}
		public void setNombreRolAdminAP(String nombreRolAdminAP) {
			this.nombreRolAdminAP = nombreRolAdminAP;
		}
		public String getUsuarioAdminAP() {
			return usuarioAdminAP;
		}
		public void setUsuarioAdminAP(String usuarioAdminAP) {
			this.usuarioAdminAP = usuarioAdminAP;
		}
		public String getPasswordAdminAP() {
			return passwordAdminAP;
		}
		public void setPasswordAdminAP(String passwordAdminAP) {
			this.passwordAdminAP = passwordAdminAP;
		}
		public String getDescripcionRol() {
			return descripcionRol;
		}
		public void setDescripcionRol(String descripcionRol) {
			this.descripcionRol = descripcionRol;
		}
	    
	    
		
}
