package com.cloud.web.security;

import javax.servlet.http.HttpServletRequest;

public interface SecureContextLoader {

	public void loadContext(HttpServletRequest request, SecureContext sc);

}
