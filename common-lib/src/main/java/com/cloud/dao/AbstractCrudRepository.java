package com.cloud.dao;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.cloud.entity.AuditPO;
import com.jedlab.framework.spring.dao.AbstractDAO;

@NoRepositoryBean
public interface AbstractCrudRepository<T extends AuditPO> extends AbstractDAO<T>
{

    //implements Custom behaviour
    @Modifying
    @Query("update #{#entityName} e set e.deletedDate = ?2, e.version= (e.version+1) where e.id= ?1")
    public void deleteSoft(Serializable id, Date date);
    
}
