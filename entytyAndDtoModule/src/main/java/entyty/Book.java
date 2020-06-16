package entyty;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Book {
    private String author;
    private String description;
    private float price;
    private String writingDate;
    private int numberOfPages;
    private String title;
    private int bookId;
    public Book() {
    }

    public Book(String author, String description, float price, String writingDate, int numberOfPages, String title,String oldTitle) {
        this.author = author;
        this.description = description;
        this.price = price;
        this.writingDate = writingDate;
        this.numberOfPages = numberOfPages;
        this.title = title;
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
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
        return title != null ? title.equals(book.title) : book.title == null;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (writingDate != null ? writingDate.hashCode() : 0);
        result = 31 * result + numberOfPages;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + bookId;
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
                '}';
    }
}
