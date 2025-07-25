package com.sytecso.security.profile.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sytecso.security.profile.ProfileAccess;

@Component
@Qualifier("accessView")
public class AcessViewImpl implements ProfileAccess {
	@Value("#{${default.access.solicitudes.versolicitudes}}")
	private Map<String, String> accessView;

	private Map<String, List<String>> access;

	@Override
	public Map<String, List<String>> getAccess() {
		return this.access;
	}

	@Override
	public void setAccess(Map<String, List<String>> access) {
		this.access = access;
	}

	/**
	 * @return the accessView
	 */
	public Map<String, String> getAccessView() {
		return accessView;
	}

	/**
	 * @param accessView the accessView to set
	 */
	public void setAccessView(Map<String, String> accessView) {
		this.accessView = accessView;
	}

	@Override
	public Map<String, Integer> getTipoAcceso() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTipoAcceso(Map<String, Integer> access) {
		// TODO Auto-generated method stub
		
	}

}
