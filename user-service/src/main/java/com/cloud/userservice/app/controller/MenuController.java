package com.cloud.userservice.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.userservice.app.domain.MenuEntity;
import com.cloud.userservice.app.service.MenuService;
import com.cloud.web.SecureContext;
import com.jedlab.framework.spring.service.JPARestriction;
import com.jedlab.framework.web.Pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	MenuService menuService;

	@GetMapping("")
	@SecureContext()
	public ResponseEntity<?> get(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, Sort sort, HttpServletRequest request) {
		Pagination p = new Pagination(page, pageSize);
		MenuRestriction restriction = new MenuRestriction();
		List<MenuEntity> menuList = menuService.load(p.getEvalPage(), p.getEvalPageSize(), null, null, MenuEntity.class,
				restriction);
		List<MenuEntity> filterList = menuService.filter(menuList);
		return null;
	}

	private class MenuRestriction implements JPARestriction {

		@Override
		public Specification countSpec(CriteriaBuilder builder, CriteriaQuery criteria, Root root) {
			return (rootEntity, query, criteriaBuilder) -> applyFilter(rootEntity, query, criteriaBuilder);
		}

		@Override
		public Specification listSpec(CriteriaBuilder builder, CriteriaQuery criteria, Root root) {
			return (rootEntity, query, criteriaBuilder) -> {
				return applyFilter(rootEntity, query, criteriaBuilder);
			};
		}

		private Predicate applyFilter(Root rootEntity, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
			var predicateList = new ArrayList<Predicate>();
			return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}

	}

	@GetMapping("/list")
	@SecureContext(roles = {"ROLE_ADMIN"})
	public ResponseEntity<?> list(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, Sort sort, HttpServletRequest request) {
		Pagination p = new Pagination(page, pageSize);
		MenuRestriction restriction = new MenuRestriction();
		List<MenuEntity> menuList = menuService.load(p.getEvalPage(), p.getEvalPageSize(), null, null, MenuEntity.class,
				restriction);
		return null;
	}

	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody MenuVO vo) {
		menuService.createMenu(vo);
		return null;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class MenuVO {
		private String title;
		private String searchKey;
		private Long parentId;
		private String roleName;
		List<PermissionRecord> permissions = new ArrayList<>();
	}

}
