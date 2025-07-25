package com.sytecso.security.profile.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sytecso.security.profile.ProfileProperties;

@Component
@Qualifier("profile")
public class ProfilePropertyImpl implements ProfileProperties {

	@Value("#{'${profile.profile.types}'.split(',')}")
	private List<String> types;

	@Override
	public List<String> getType() {
		return this.types;
	}

	@Override
	public void setType(List<String> types) {
		this.types = types;
	}

}
