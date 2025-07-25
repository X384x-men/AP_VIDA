package com.sytecso.config.url;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("defaultAccess")
public class DefaultAccessImpl implements UrlProperties {
	
	@Value("#{'${deault.view.url}'.split(',')}")
	private List<String> url;

	@Override
	public void setUrls(List<String> urls) {
		this.url = urls;

	}

	@Override
	public List<String> getUrls() {
		return this.url;
	}

}
