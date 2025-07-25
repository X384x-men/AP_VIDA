package com.sytecso.security.profile;

import java.util.List;

public interface Profile {
	public void setUserName(String userName);

	public String getUserName();

	public void setPassword(String password);

	public String getPassword();

	public void setRole(String role);

	public String getRole();

	public void setAcceso(String acceso);

	public String getAcceso();

	public void setDescripcion(String descripcion);

	public String getDescripcion();

	public List<String> getViews();

	public void setAcceso(List<String> view);
}
