package com.cloud.web;

import javax.servlet.http.HttpServletRequest;

public interface LoadFileProperties {

	public FileProperties getProps(long eid, HttpServletRequest request);
	
	
}
