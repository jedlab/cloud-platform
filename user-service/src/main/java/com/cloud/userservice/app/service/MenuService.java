package com.cloud.userservice.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.config.acl.SecureAclService;
import com.cloud.userservice.app.controller.MenuController.MenuVO;
import com.cloud.userservice.app.dao.MenuDao;
import com.cloud.userservice.app.domain.MenuEntity;
import com.cloud.web.security.ExtendedBasePermission;
import com.jedlab.framework.spring.security.AuthenticationUtil;
import com.jedlab.framework.spring.service.AbstractCrudService;

@Service
public class MenuService extends AbstractCrudService<MenuEntity> {

	MenuDao menuDao;

	@PersistenceContext
	EntityManager em;

	SecureAclService aclService;

	public MenuService(MenuDao repository, PlatformTransactionManager ptm, SecureAclService aclService) {
		super(repository, ptm);
		this.menuDao = repository;
		this.aclService = aclService;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Transactional
	public void createMenu(MenuVO vo) {
		MenuEntity parentMenu = null;
		if(vo.getParentId() != null)
			parentMenu = menuDao.findById(vo.getParentId()).orElse(null);
		MenuEntity me = new MenuEntity();
		me.setTitle(vo.getTitle());
		me.setSearchKey(vo.getSearchKey());
		me.setParent(parentMenu);
		menuDao.save(me);
		vo.getPermissions().forEach(perm -> {
			aclService.removeAcl(me.getId(), MenuEntity.class);
				aclService.createAcl(me.getId(), MenuEntity.class, new SimpleGrantedAuthority(vo.getRoleName()), perm.getExtendedPermission());
		});

	}

	
	public List<MenuEntity> filter(List<MenuEntity> menuList) {
		List<MenuEntity> resultList = new ArrayList<MenuEntity>();
		for (MenuEntity menuEntity : menuList) {
			boolean hasPermission = aclService.hasPermission(AuthenticationUtil.getAuthentication(), menuEntity, Arrays.asList(ExtendedBasePermission.READ));
			if(hasPermission)
				resultList.add(menuEntity);
		}
		return resultList;
	}

}
