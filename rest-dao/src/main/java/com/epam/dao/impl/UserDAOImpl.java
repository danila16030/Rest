package com.epam.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.epam.dao.BaseDAO;
import com.epam.dao.UserDAO;
import com.epam.entity.User;
import com.epam.entity.User_;
import com.epam.exception.NoSuchElementException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

    @Override
    public User getUser(long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(User_.USER_ID), userId));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public User getUser(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(User_.USERNAME), username));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public User getUserByNameWithoutException(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(User_.USERNAME), username));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserByIdWithoutException(long userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public Optional<List<User>> getAll(int limit, int offset) {
        limit = limiting(limit);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.orderBy(builder.asc(root.get(User_.USERNAME)));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).
                    getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<User>> getAllByPartialName(String userName, int limit, int offset) {
        limit = limiting(limit);
        userName = "%" + userName + "%";
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.like(root.get(User_.USERNAME), userName));
        criteria.orderBy(builder.asc(root.get(User_.USERNAME)));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).
                    getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public User createUser(User user) {
        return create(user);
    }

    @Override
    public void removeUser(long userId) {
        remove(userId, User.class);
    }

    @Override
    public User updateUser(User user) {
        return update(user);
    }
}
