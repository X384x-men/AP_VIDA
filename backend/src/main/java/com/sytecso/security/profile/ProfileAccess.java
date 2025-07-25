package com.sytecso.security.profile;

import java.util.List;
import java.util.Map;

public interface ProfileAccess {
	abstract Map<String, List<String>> getAccess();

	abstract void setAccess(Map<String, List<String>> access);

	abstract Map<String, String> getAccessView();

	abstract void setAccessView(Map<String, String> accessView);

	public Map<String, Integer> getTipoAcceso();

	public void setTipoAcceso(Map<String, Integer> access);
}
