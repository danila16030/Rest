package com.epam.dto;


import javax.validation.constraints.*;
import java.util.List;

public class BookDTO {
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Z]+")
    @NotBlank
    @NotEmpty
    private String author;
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Z 0-9]+")
    @NotBlank
    @NotEmpty
    private String description;
    @NotNull
    @Min(1)
    private float price;
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[0-9.]+")
    @NotBlank
    @NotEmpty
    private String writingDate;
    @Min(1)
    private int numberOfPages;
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Z 0-9]+")
    @NotBlank
    @NotEmpty
    private String title;
    @Min(1)
    private long bookId;
    @NotNull
    private List<GenreDTO> genres;

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

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }
}
