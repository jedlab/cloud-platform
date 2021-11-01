package com.cloud.userservice.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jedlab.framework.spring.dao.PO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sec_menu")
@Getter
@Setter
public class MenuEntity extends PO implements Comparable<MenuEntity> {

	@Column(name = "url")
	private String url;

	@Column(name = "title")
	private String title;

	@Column(name = "icon")
	private String icon;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private MenuEntity parent;

	@Column(name = "priority")
	private Integer priority;

	private List<MenuEntity> children;

	private void addChild(MenuEntity menu) {

		if (children == null) {
			children = new ArrayList<MenuEntity>();
		}
		children.add(menu);
	}

	@Transient
	public List<MenuEntity> getChildren() {
		return children;
	}

	@Transient
	public boolean getHasChild() {
		if (children == null)
			return false;
		return children.size() > 0;
	}

	public void setParent(MenuEntity parent) {
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
	}

	@Override
	public int compareTo(MenuEntity o) {
		return getPriority().compareTo(o.getPriority());
	}

}
