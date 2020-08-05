package com.epam.dao;

import com.epam.exception.DuplicatedException;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public abstract class BaseDAO<T> {
    private static final int MAXLIMIT = 50;

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

    protected int limiting(int limit) {
        if (limit > MAXLIMIT) {
            return MAXLIMIT;
        }
        return limit;
    }
}
