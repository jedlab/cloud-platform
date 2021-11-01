package com.cloud.web;

import javax.servlet.http.HttpServletRequest;

public interface SecureContextLoader {

	public void loadContext(HttpServletRequest request, SecureContext sc);

}
