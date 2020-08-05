package com.epam.dao.impl;

import com.epam.dao.BaseDAO;
import com.epam.dao.BookDAO;
import com.epam.entity.Book;
import com.epam.entity.Book_;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Repository
public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Book createNewBook(Book book) {
        return create(book);
    }

    @Override
    public void removeBook(long bookId) {
        remove(bookId, Book.class);
    }

    @Override
    public Optional<List<Book>> getAllBooks(int limit, int offset) {
        limit = limiting(limit);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        criteria.orderBy(builder.asc(root.get(Book_.TITLE)));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).
                    getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Book updateBook(Book book) {
        return update(book);
    }

    @Override
    public Book getBookById(long bookId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Book_.BOOK_ID), bookId));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }


    @Override
    public Book getBookByIdWithoutException(long bookId) {
        return entityManager.find(Book.class, bookId);
    }

    @Override
    public Optional<List<Book>> getBookSortedByAuthor(int limit, int offset) {
        limit = limiting(limit);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        criteria.orderBy(builder.asc(root.get(Book_.TITLE)));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).
                    getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Book>> searchByPartialCoincidence(String title, int limit, int offset) {
        limit = limiting(limit);
        title = "%" + title + "%";
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        criteria.where(builder.like(root.get(Book_.TITLE), title));
        try {
            return Optional.of(entityManager.createQuery(criteria).setFirstResult(offset).
                    setMaxResults(limit).getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Book>> searchByFullCoincidence(String title, int limit, int offset) {
        limit = limiting(limit);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Book_.TITLE), title));
        try {
            return Optional.of(entityManager.createQuery(criteria).setFirstResult(offset).
                    setMaxResults(limit).getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }
}

