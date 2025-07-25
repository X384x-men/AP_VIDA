package com.sytecso.config.menu;

import java.io.Serializable;
import java.util.List;


public class MenuDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4938952165342989586L;
	private List<ModuloDTO> modulos;
	
	
	public List<ModuloDTO> getModulos() {
		return modulos;
	}
	public void setModulos(List<ModuloDTO> modulos) {
		this.modulos = modulos;
	}

	

}
