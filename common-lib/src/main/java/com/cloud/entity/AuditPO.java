package com.cloud.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.jedlab.framework.spring.dao.PO;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditPO extends PO {

	@Column(name = "created_by")
	@CreatedBy
	private String createdBy;

	@Column(name = "updated_by")
	@LastModifiedBy
	private String updatedBy;

}
