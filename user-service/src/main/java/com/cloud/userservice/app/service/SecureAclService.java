package com.cloud.userservice.app.service;

import java.util.List;

import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class SecureAclService {

	protected ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();

	protected SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();
	private final MutableAclService aclService;
	private final AclCache aclCache;

	public SecureAclService(MutableAclService aclService, AclCache aclCache) {
		this.aclService = aclService;
		this.aclCache = aclCache;
	}

	public boolean hasPermission(Authentication authentication, Object domainObject,
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

	public void evitFromCache(Long entityId) {
		aclCache.evictFromCache(entityId);
	}

	public void createAcl(Long id, Class<?> clz, GrantedAuthority roleName, Permission p) {
		ObjectIdentity oi = new ObjectIdentityImpl(clz, id);
		Sid sid = new GrantedAuthoritySid(roleName);
		// Create or update the relevant ACL
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(oi);
		} catch (NotFoundException nfe) {
			acl = aclService.createAcl(oi);
		}
		// Now grant some permissions via an access control entry (ACE)
		acl.insertAce(acl.getEntries().size(), p, sid, true);
		aclService.updateAcl(acl);
	}

	public void removeAcl(Long id, Class<?> clz) {
		ObjectIdentity oi = new ObjectIdentityImpl(clz, id);
		aclService.deleteAcl(oi, false);
	}

}
