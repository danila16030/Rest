package com.epam.dao.impl;

import com.epam.dao.UserDAO;
import com.epam.entity.User;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    EntityManager entityManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getUser(long userId) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("userId"), userId));
        try {
            User user = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return user;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public User getUser(String username) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("username"), username));
        try {
            User user = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return user;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public User getUserByNameWithoutException(String username) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("username"), username));
        try {
            User user = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return user;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            return new User();
        }
    }

    @Override
    public User getUserByIdWithoutException(long userId) {
        entityManager.getTransaction().begin();
        User book = entityManager.find(User.class, userId);
        entityManager.getTransaction().commit();
        return book;
    }


    @Override
    public Optional<List<User>> getAll(int limit, int offset) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        try {
            Optional<List<User>> users = Optional.of(entityManager.createQuery(criteria).setFirstResult(offset).
                    setMaxResults(limit).getResultList());
            entityManager.getTransaction().commit();
            return users;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public void removeUser(long userId) {
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User updateUser(String username, long userId, String password) {
        User user = new User(username, userId, password);
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        return user;
    }
}
