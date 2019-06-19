package com.hottop.core.service;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.request.argument.flag.FlagPageSizeRequest;
import com.hottop.core.utils.CommonUtil;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public abstract class EntityBaseService<T extends EntityBase, ID extends Serializable> {
    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional(readOnly = true)
    public T findOne(Class<T> clz, ID id) {
        return getEntityManager().find(clz, id);
    }

    public abstract EntityBaseRepository<T, ID> repository();

    public Page<T> filter(Class<T> clazz, IFilterResolver<T> filterResolver) {
        if (filterResolver.functionResolver(clazz).flagPageable() == null) {
            return repository().findAll(filterResolver.orSpecification(),
                    FlagPageSizeRequest.DEFAULT);
        } else {
            return repository().findAll(filterResolver.orSpecification(),
                    filterResolver.functionResolver(clazz).flagPageable());
        }
    }

    public ArrayList<Field> fields(Class<T> clazz) {
        return CommonUtil.fields(clazz);
    }

    @Transactional
    public T save(T entity) {
        entity.setCreateTime(new Date());
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    @Transactional
    public T update(T entity) {
        entity.setUpdateTime(new Date());
        getEntityManager().merge(entity);
        getEntityManager().flush();
        return entity;
    }

    @Transactional
    public void delete(T entity) {
        entity.setDeleteTime(new Date());
        update(entity);
    }

    @Transactional
    public void delete(Class<T> clz, ID id) {
        T entity = getEntityManager().find(clz, id);
        delete(entity);
    }
}
