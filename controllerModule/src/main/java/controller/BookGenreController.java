package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.impl.BookGenreServiceImpl;

@Controller
@RequestMapping(value = "/book_genre")
public class BookGenreController {
    @Autowired
    private BookGenreServiceImpl bookGenreService;

    @GetMapping(value = "/getBookByGenre/{genreName}")
    public String getGenreByBook(Model model, @PathVariable String genreName) {
        model.addAttribute("books", bookGenreService.getBooksByGenre(genreName));
        return "jsonTemplate";
    }

    @GetMapping(value = "/getGenreByBook/{bookName}")
    public String getBookByGenre(Model model, @PathVariable String bookName) {
        model.addAttribute("genres", bookGenreService.getGenresByBook(bookName));
        return "jsonTemplate";
    }
}
