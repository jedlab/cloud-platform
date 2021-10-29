package com.cloud.usermanagement;

import java.util.stream.Stream;

import com.jedlab.framework.exceptions.ServiceException;

public enum GrantType {
	PASSWORD, CLIENT_CREDENTIALS, REFRESH_TOKEN;
	
	
	public static GrantType findByValue(String value)
	{
		return Stream.of(GrantType.values()).filter(f->f.name().equalsIgnoreCase(value)).findFirst()
				.orElseThrow(()->new ServiceException("Invalid Grant type"));
	}
}