package com.epam.services.comparator;

import com.epam.entytys.entyty.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class BookDateComparator implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy");
            Date date1 = sdformat.parse(o1.getWritingDate());
            Date date2 = sdformat.parse(o2.getWritingDate());
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
