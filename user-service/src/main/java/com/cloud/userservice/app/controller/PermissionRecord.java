package com.cloud.userservice.app.controller;

import org.springframework.security.acls.model.Permission;

import com.cloud.web.security.ExtendedBasePermission;

public enum PermissionRecord {
	READ(ExtendedBasePermission.READ), CREATE(ExtendedBasePermission.CREATE), UPDATE(ExtendedBasePermission.UPDATE);

	Permission extendedPermission;

	private PermissionRecord(Permission extendedPermission) {
		this.extendedPermission = extendedPermission;
	}

	public Permission getExtendedPermission() {
		return extendedPermission;
	}

}