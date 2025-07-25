package com.sytecso.security.profile.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sytecso.security.profile.Profile;

@Component
@Qualifier("admin")
public class AdminImpl implements Profile {

	@Value("${profile.admin.name}")
	private String userName;
	@Value("${profile.admin.password}")
	private String password;
	@Value("${profile.admin.role}")
	private String role;
	@Value("${profile.admin.tipo.acceso}")
	private String acceso;
	@Value("${profile.admin.descripcion}")
	private String descripcion;
	@Value("#{'${profile.admin.view}'.split(',')}")
	private List<String> views;

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	@Override
	public String getAcceso() {
		return this.acceso;
	}

	@Override
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String getDescripcion() {
		return this.descripcion;
	}

	@Override
	public List<String> getViews() {
		return this.views;
	}

	@Override
	public void setAcceso(List<String> view) {
		this.views = view;
	}

}
