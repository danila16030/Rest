package com.epam.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book",schema = "public")
public class Book {
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "writing_date", nullable = false)
    private String writingDate;
    @Column(name = "page_number", nullable = false)
    private int numberOfPages;
    @Column(name = "title", nullable = false)
    private String title;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private long bookId;
    @Transient
    private List<Genre> genres;

    public Book() {
    }

    public Book(String author, String description, float price, String writingDate, int numberOfPages, String title) {
        this.author = author;
        this.description = description;
        this.price = price;
        this.writingDate = writingDate;
        this.numberOfPages = numberOfPages;
        this.title = title;
    }

    public Book(String author, String description, float price, String writingDate, int numberOfPages, String title,
                long bookId) {
        this.author = author;
        this.description = description;
        this.price = price;
        this.writingDate = writingDate;
        this.numberOfPages = numberOfPages;
        this.title = title;
        this.bookId = bookId;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

    public float getPrice() {
        return this.price;
    }

    public String getWritingDate() {
        return this.writingDate;
    }

    public int getNumberOfPages() {
        return this.numberOfPages;
    }

    public String getTitle() {
        return this.title;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setWritingDate(String writingDate) {
        this.writingDate = writingDate;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (Float.compare(book.price, price) != 0) return false;
        if (numberOfPages != book.numberOfPages) return false;
        if (bookId != book.bookId) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (description != null ? !description.equals(book.description) : book.description != null) return false;
        if (writingDate != null ? !writingDate.equals(book.writingDate) : book.writingDate != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        return genres != null ? genres.equals(book.genres) : book.genres == null;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (writingDate != null ? writingDate.hashCode() : 0);
        result = 31 * result + numberOfPages;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (int) (bookId ^ (bookId >>> 32));
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", writingDate='" + writingDate + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", title='" + title + '\'' +
                ", bookId=" + bookId +
                ", genres=" + genres +
                '}';
    }
}
