package com.cloud.web.security;

import org.springframework.security.acls.domain.AbstractPermission;
import org.springframework.security.acls.model.Permission;

public class ExtendedBasePermission extends AbstractPermission {

	public static final Permission READ = new ExtendedBasePermission(1 << 0, 'R'); // 1

	public static final Permission UPDATE = new ExtendedBasePermission(1 << 1, 'U'); // 2

	public static final Permission CREATE = new ExtendedBasePermission(1 << 2, 'C'); // 4

	public static final Permission DELETE = new ExtendedBasePermission(1 << 3, 'D'); // 8

	public static final Permission ADMINISTRATION = new ExtendedBasePermission(1 << 4, 'A'); // 16
	
	public static final Permission DOWNLOAD = new ExtendedBasePermission(1 << 5, 'G'); // 32
	
	public static final Permission SCAN = new ExtendedBasePermission(1 << 6, 'S'); // 64
	
	public static final Permission PRINT = new ExtendedBasePermission(1 << 7, 'P'); // 128

	protected ExtendedBasePermission(int mask) {
		super(mask);
	}

	protected ExtendedBasePermission(int mask, char code) {
		super(mask, code);
	}

}
