package org.fp.bt_ql_muon_sach.web;

import org.fp.bt_ql_muon_sach.service.BookCatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {

    private final BookCatalogService catalogService;

    public BookController(BookCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String list(Model model) {
        model.addAttribute("books", catalogService.listBooks());
        return "books/list";
    }

    @GetMapping("/books/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("book", catalogService.getBook(id));
        return "books/detail";
    }

    @GetMapping("/authors/{id}")
    public String author(@PathVariable Long id, Model model) {
        model.addAttribute("author", catalogService.getAuthorBooks(id));
        return "authors/books";
    }
}
