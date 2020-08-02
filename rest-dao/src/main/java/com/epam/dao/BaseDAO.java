package com.epam.dao;

import com.epam.exception.DuplicatedException;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T> {
    @Autowired
    protected EntityManager entityManager;

    public T create(T entity) {
        entityManager.joinTransaction();
        try {
            entityManager.persist(entity);
            return entity;
        } catch (PersistenceException e) {
            throw new DuplicatedException("Object with this name is already exist");
        }
    }

    public T update(T entity) {
        entityManager.joinTransaction();
        try {
            entityManager.merge(entity);
            return entity;
        } catch (PersistenceException e) {
            throw new DuplicatedException("Object with this name is already exist");
        }
    }

    public void remove(long id, Class<T> aClass) {
        entityManager.joinTransaction();
        T entity = entityManager.find(aClass, id);
        if (entity == null) {
            throw new NoSuchElementException();
        }
        entityManager.remove(entity);
    }

    public Optional<List<T>> getAll(int limit, int offset, Class<T> aClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(aClass);
        Root<T> root = criteria.from(aClass);
        criteria.select(root);
        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).
                    getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }
}
