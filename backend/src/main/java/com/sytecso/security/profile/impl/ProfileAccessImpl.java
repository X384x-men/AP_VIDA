package com.sytecso.security.profile.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sytecso.security.profile.ProfileAccess;

@Component
@Qualifier("access")
public class ProfileAccessImpl implements ProfileAccess {
	@Value("#{${default.access.list}}")
	private Map<String, List<String>> access;
	@Value("#{${user.access.tipo.value}}")
	private Map<String, Integer> views;

	@Override
	public Map<String, List<String>> getAccess() {
		return this.access;
	}

	@Override
	public void setAccess(Map<String, List<String>> access) {
		this.access = access;
	}

	@Override
	public Map<String, String> getAccessView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAccessView(Map<String, String> accessView) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Integer> getTipoAcceso() {
		return this.views;
	}

	@Override
	public void setTipoAcceso(Map<String, Integer> access) {
		this.views = access;
	}

}
