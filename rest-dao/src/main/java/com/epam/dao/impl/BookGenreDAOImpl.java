package com.epam.dao.impl;

import com.epam.dao.BookGenreDAO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.entity.Book;
import com.epam.entity.BookGenre;
import com.epam.entity.Genre;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookGenreDAOImpl implements BookGenreDAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public Optional<List<Book>> getAllBooksByGenre(long genreId, int limit, int offset) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT c FROM BookGenre sc INNER JOIN Book c ON " +
                "c.bookId=sc.bookId WHERE sc.genreId=" + genreId, Book.class);
        try {
            Optional<List<Book>> books = Optional.of(query.setFirstResult(offset).
                    setMaxResults(limit).getResultList());
            entityManager.getTransaction().commit();
            return books;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Genre>> getAllGenresOnBook(long bookId, int limit, int offset) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT c FROM BookGenre sc INNER JOIN Genre c ON " +
                "c.genreId=sc.genreId WHERE sc.bookId=" + bookId, Genre.class);
        try {
            Optional<List<Genre>> genres = Optional.of(query.setFirstResult(offset).
                    setMaxResults(limit).getResultList());
            entityManager.getTransaction().commit();
            return genres;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Genre>> getAllGenresOnBook(long bookId) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT c FROM BookGenre sc INNER JOIN Genre c ON " +
                "c.genreId=sc.genreId WHERE sc.bookId=" + bookId, Genre.class);
        try {
            Optional<List<Genre>> genres = Optional.of(query.getResultList());
            entityManager.getTransaction().commit();
            return genres;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public void createConnection(long bookId, long genreId) {
        BookGenre bookGenre = new BookGenre(bookId, genreId);
        entityManager.getTransaction().begin();
        entityManager.persist(bookGenre);
        entityManager.getTransaction().commit();
    }

    @Override
    public boolean checkConnection(long bookId, long genreId) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookGenre> criteria = builder.createQuery(BookGenre.class);
        Root<BookGenre> root = criteria.from(BookGenre.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("bookId"), bookId)).where(builder.equal(root.get("genreId"), genreId));
        try {
            BookGenre result = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return true;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            return false;
        }
    }

    @Override
    public Book getBookByGenres(ParametersRequestDTO parametersRequestDTO) {
        try {
            //           return jdbcTemplate.queryForObject(getBookByGenres, new BookMapper());
            return null;
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

}
