package com.epam.comparator;

import com.epam.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

@Component
public class BookDateComparator implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy.dd.MM");
            Date date1 = sdformat.parse(o1.getWritingDate());
            Date date2 = sdformat.parse(o2.getWritingDate());
            return date1.compareTo(date2);
        } catch (ParseException e) {
        }
        return 0;
    }
}
