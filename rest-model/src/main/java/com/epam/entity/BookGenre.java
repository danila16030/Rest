package com.epam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_genre")
public class BookGenre {
    @Column(name = "book_id", nullable = false)
    private long bookId;
    @Id
    @Column(name = "genre_id", nullable = false)
    private long genreId;

    public BookGenre() {
    }

    public BookGenre(long bookId, long genreId) {
        this.bookId = bookId;
        this.genreId = genreId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookGenre bookGenre = (BookGenre) o;

        if (bookId != bookGenre.bookId) return false;
        return genreId == bookGenre.genreId;
    }

    @Override
    public int hashCode() {
        int result = (int) (bookId ^ (bookId >>> 32));
        result = 31 * result + (int) (genreId ^ (genreId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BookGenre{" +
                "bookId=" + bookId +
                ", genreId=" + genreId +
                '}';
    }
}
