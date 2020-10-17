package com.epam.dao.impl;

import com.epam.dao.BaseDAO;
import com.epam.dao.GenreDAO;
import com.epam.entity.Genre;
import com.epam.entity.Genre_;
import com.epam.exception.NoSuchElementException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class GenreDAOImpl extends BaseDAO<Genre> implements GenreDAO {

    @Override
    public Long createGenreAndReturnId(Genre genre) {
        return create(genre).getGenreId();
    }

    @Override
    public void removeGenre(long genreId) {
        remove(genreId, Genre.class);
    }

    @Override
    public Optional<List<Genre>> getGenreList(int limit, int offset) {
        limit = limiting(limit);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);
        criteria.select(root);
        criteria.orderBy(builder.asc(root.get(Genre_.GENRE_NAME)));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).
                    getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Genre getGenreById(long genreId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Genre_.GENRE_ID), genreId));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }


    @Override
    public Genre createGenre(Genre genre) {
        return create(genre);
    }

    @Override
    public Genre getGenreByNameWithoutException(String genreName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Genre_.GENRE_NAME), genreName));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return new Genre();
        }
    }


    @Override
    public Genre getGenreByName(String genreName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Genre_.GENRE_NAME), genreName));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Genre updateGenre(Genre genre) {
        return update(genre);
    }

    @Override
    public Genre getGenreByIdWithoutException(long genreId) {
        return entityManager.find(Genre.class, genreId);
    }


}
