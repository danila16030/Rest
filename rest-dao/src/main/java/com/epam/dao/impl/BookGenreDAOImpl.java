package com.epam.dao.impl;

import com.epam.dao.BaseDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.entity.Book;
import com.epam.entity.BookGenre;
import com.epam.entity.BookGenre_;
import com.epam.entity.Genre;
import com.epam.exception.NoSuchElementException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository

public class BookGenreDAOImpl extends BaseDAO<BookGenre> implements BookGenreDAO {

    @Override
    public Optional<List<Book>> getAllBooksByGenre(long genreId, int limit, int offset) {
        limit = limiting(limit);
        Query query = entityManager.createNamedQuery(BookGenre.QueryNames.BOOKS_BY_GENRE, Book.class);
        query.setParameter("genreId", genreId);
        try {
            return Optional.of(query.setFirstResult(offset).
                    setMaxResults(limit).getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Genre>> getAllGenresOnBook(long bookId, int limit, int offset) {
        limit = limiting(limit);
        Query query = entityManager.createNamedQuery(BookGenre.QueryNames.GENRE_ON_BOOK, Genre.class);
        query.setParameter("bookId", bookId);
        try {
            return Optional.of(query.setFirstResult(offset).
                    setMaxResults(limit).getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Genre>> getAllGenresOnBook(long bookId) {
        Query query = entityManager.createNamedQuery(BookGenre.QueryNames.GENRE_ON_BOOK, Genre.class);
        query.setParameter("bookId", bookId);
        try {
            return Optional.of(query.getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void createConnection(BookGenre bookGenre) {
        create(bookGenre);
    }

    @Override
    public void removeConnection(BookGenre bookGenre) {
        entityManager.joinTransaction();
        Query query = entityManager.createNamedQuery(BookGenre.QueryNames.REMOVE, BookGenre.class);
        query.setParameter(1, bookGenre.getGenreId());
        query.setParameter(2, bookGenre.getBookId());
        query.executeUpdate();
    }

    @Override
    public boolean checkConnection(BookGenre bookGenre) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookGenre> criteria = builder.createQuery(BookGenre.class);
        Root<BookGenre> root = criteria.from(BookGenre.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(BookGenre_.BOOK_ID), bookGenre.getBookId()),
                builder.equal(root.get(BookGenre_.genreId), bookGenre.getGenreId()));
        try {
            BookGenre result = entityManager.createQuery(criteria).getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
