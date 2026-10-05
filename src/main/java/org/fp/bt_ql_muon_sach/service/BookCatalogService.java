package org.fp.bt_ql_muon_sach.service;

import org.fp.bt_ql_muon_sach.dto.AuthorBooksView;
import org.fp.bt_ql_muon_sach.dto.BookCard;
import org.fp.bt_ql_muon_sach.dto.BookDetailView;
import org.fp.bt_ql_muon_sach.entity.Book;
import org.fp.bt_ql_muon_sach.repository.AuthorRepository;
import org.fp.bt_ql_muon_sach.repository.BookRepository;
import org.fp.bt_ql_muon_sach.web.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookCatalogService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookCatalogService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Danh mục chỉ đọc title, giá, tác giả.
     * author là EAGER nên không đụng detail (LAZY) và categories (LAZY).
     * DTO cắt quan hệ trước khi session đóng — view không còn lazy-load được.
     */
    public List<BookCard> listBooks() {
        return bookRepository.findAll().stream()
                .map(BookCatalogService::toCard)
                .sorted(Comparator.comparing(BookCard::title))
                .toList();
    }

    /**
     * Trang chi tiết cần cả 1-1 và N-N.
     * Hai query: detail không đi chung categories, tránh cartesian product.
     * Gọi getDetail() / getCategories() ngay trong transaction này.
     */
    public BookDetailView getBook(Long id) {
        Book book = bookRepository.findWithAuthorAndDetail(id)
                .orElseThrow(() -> new NotFoundException("Không có sách id=" + id));
        Book categorized = bookRepository.findWithCategories(id)
                .orElseThrow(() -> new NotFoundException("Không có sách id=" + id));

        List<String> categoryNames = categorized.getCategories().stream()
                .map(category -> category.getName())
                .sorted()
                .toList();

        return new BookDetailView(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPrice(),
                book.getAuthor().getName(),
                book.getAuthor().getId(),
                book.getDetail().getSummary(),
                book.getDetail().getPageCount(),
                book.getDetail().getLanguage(),
                categoryNames
        );
    }

    /**
     * 1-N LAZY được xử lý bằng findByIdWithBooks, không gọi author.getBooks()
     * sau khi hết transaction.
     */
    public AuthorBooksView getAuthorBooks(Long authorId) {
        var author = authorRepository.findByIdWithBooks(authorId)
                .orElseThrow(() -> new NotFoundException("Không có tác giả id=" + authorId));
        List<BookCard> cards = author.getBooks().stream()
                .map(BookCatalogService::toCard)
                .sorted(Comparator.comparing(BookCard::title))
                .toList();
        return new AuthorBooksView(author.getId(), author.getName(), author.getEmail(), cards);
    }

    private static BookCard toCard(Book book) {
        return new BookCard(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPrice(),
                book.getAuthor().getName(),
                book.getAuthor().getId()
        );
    }
}
