package com.epam.dao.impl;

import com.epam.dao.GenreDAO;
import com.epam.entity.Genre;
import com.epam.exception.DuplicatedException;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class GenreDAOImpl implements GenreDAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public Long createGenreAndReturnId(String genreName) {
        Genre genre = new Genre();
        try {
            entityManager.getTransaction().begin();
            genre.setGenreName(genreName);
            entityManager.persist(genre);
            entityManager.getTransaction().commit();
            return genre.getGenreId();
        } catch (PersistenceException e) {
            entityManager.getTransaction().commit();
            throw new DuplicatedException("Genre with this name is already exist");
        }
    }


    @Override
    public void removeGenre(long genreId) {
        entityManager.getTransaction().begin();
        Genre genre = entityManager.find(Genre.class, genreId);
        if (genre == null) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
        entityManager.remove(genre);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<List<Genre>> getGenreList(int limit, int offset) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);
        criteria.select(root);
        try {
            Optional<List<Genre>> genres = Optional.of(entityManager.createQuery(criteria).setFirstResult(offset).
                    setMaxResults(limit).getResultList());
            entityManager.getTransaction().commit();
            return genres;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public Genre getGenreById(long genreId) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("genreId"), genreId));
        try {
            Genre genre = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return genre;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }


    @Override
    @Transactional
    public Genre createGenre(String genreName) {
        Genre genre = new Genre();
        try {
            entityManager.getTransaction().begin();
            genre.setGenreName(genreName);
            entityManager.persist(genre);
            entityManager.getTransaction().commit();
            return genre;
        } catch (PersistenceException e) {
            entityManager.getTransaction().commit();
            throw new DuplicatedException("Genre with this name is already exist");
        }
    }

    @Override
    public Genre getGenreByNameWithoutException(String genreName) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("genreName"), genreName));
        try {
            Genre genre = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return genre;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            return new Genre();
        }
    }

    @Override
    public Genre updateGenre(String genreName, long genreId) {
        Genre genre = new Genre(genreName, genreId);
        entityManager.getTransaction().begin();
        entityManager.merge(genre);
        entityManager.getTransaction().commit();
        return genre;
    }

    @Override
    public Genre getGenreByIdWithoutException(long genreId) {
        entityManager.getTransaction().begin();
        Genre genre = entityManager.find(Genre.class, genreId);
        entityManager.getTransaction().commit();
        return genre;
    }
}
