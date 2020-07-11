package com.epam.dao.impl;

import com.epam.dao.BookDAO;
import com.epam.entity.Book;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BookDAOImpl implements BookDAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public Long createNewBook(String author, String description, float price, String writingDate, int numberOfPages,
                              String title) {
        Book book = new Book(author, description, price, writingDate, numberOfPages, title);
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        return book.getBookId();
    }

    @Override
    public void removeBook(long bookId) {
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, bookId);
        if (book == null) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
        entityManager.remove(book);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<List<Book>> getAllBooks(int limit, int offset) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        try {
            Optional<List<Book>> books = Optional.of(entityManager.createQuery(criteria).setFirstResult(offset).
                    setMaxResults(limit).getResultList());
            entityManager.getTransaction().commit();
            return books;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public Book updateBook(String title, String author, String writingDate, String description, int numberOfPages,
                           float price, long bookId) {
        Book book = new Book(author, description, price, writingDate, numberOfPages, title, bookId);
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
        return book;
    }

    @Override
    public Book getBookById(long bookId) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("bookId"), bookId));
        try {
            Book book = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return book;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public Book changeBookPrice(float price, long bookId) {
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, bookId);
        book.setPrice(price);
        entityManager.merge(book);
        entityManager.getTransaction().commit();
        return book;
    }

    @Override
    public Book getBookByIdWithoutException(long bookId) {
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, bookId);
        entityManager.getTransaction().commit();
        return book;
    }

    @Override
    public Optional<List<Book>> searchByPartialCoincidence(String title, int limit, int offset) {
        title = "%" + title + "%";
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        criteria.where(builder.like(root.get("title"), title));
        try {
            Optional<List<Book>> books = Optional.of(entityManager.createQuery(criteria).setFirstResult(offset).
                    setMaxResults(limit).getResultList());
            entityManager.getTransaction().commit();
            return books;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Book>> searchByFullCoincidence(String title, int limit, int offset) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("title"), title));
        try {
            Optional<List<Book>> books = Optional.of(entityManager.createQuery(criteria).setFirstResult(offset).
                    setMaxResults(limit).getResultList());
            entityManager.getTransaction().commit();
            return books;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

}

