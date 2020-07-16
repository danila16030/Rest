package com.epam.comparator;

import com.epam.entity.Book;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class BookTitleComparator implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
