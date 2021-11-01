package com.cloud.userservice.app.service;

import java.util.List;

import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecureAclService {

	protected ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();

	protected SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();
	protected final AclService aclService;

	public SecureAclService(AclService aclService) {
		this.aclService = aclService;
	}

	protected boolean hasPermission(Authentication authentication, Object domainObject,
			List<Permission> requirePermission) {
		// Obtain the OID applicable to the domain object
		ObjectIdentity objectIdentity = this.objectIdentityRetrievalStrategy.getObjectIdentity(domainObject);

		// Obtain the SIDs applicable to the principal
		List<Sid> sids = this.sidRetrievalStrategy.getSids(authentication);

		try {
			// Lookup only ACLs for SIDs we're interested in
			Acl acl = this.aclService.readAclById(objectIdentity, sids);
			return acl.isGranted(requirePermission, sids, false);
		} catch (NotFoundException ex) {
			return false;
		}
	}

}
