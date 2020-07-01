package com.epam.dto.responce;

import java.util.List;

public class BookResponseDTO {
    private String author;
    private String description;
    private float price;
    private String writingDate;
    private int numberOfPages;
    private String title;
    private long bookId;
    private List<GenreResponseDTO> genres;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getWritingDate() {
        return writingDate;
    }

    public void setWritingDate(String writingDate) {
        this.writingDate = writingDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public List<GenreResponseDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreResponseDTO> genres) {
        this.genres = genres;
    }
}
