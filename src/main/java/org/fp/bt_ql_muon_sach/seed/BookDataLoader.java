package org.fp.bt_ql_muon_sach.seed;

import org.fp.bt_ql_muon_sach.entity.Author;
import org.fp.bt_ql_muon_sach.entity.Book;
import org.fp.bt_ql_muon_sach.entity.BookDetail;
import org.fp.bt_ql_muon_sach.entity.Category;
import org.fp.bt_ql_muon_sach.repository.AuthorRepository;
import org.fp.bt_ql_muon_sach.repository.BookRepository;
import org.fp.bt_ql_muon_sach.repository.CategoryRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BookDataLoader implements ApplicationRunner {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public BookDataLoader(
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            BookRepository bookRepository
    ) {
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (bookRepository.count() > 0) {
            return;
        }

        Category literature = categoryRepository.save(new Category("Văn học"));
        Category skill = categoryRepository.save(new Category("Kỹ năng"));
        Category programming = categoryRepository.save(new Category("Lập trình"));

        Author nhatAnh = new Author("Nguyễn Nhật Ánh", "nhatanh@example.com");
        nhatAnh.addBook(book(
                "Mắt biếc",
                "978-604-2-12201",
                new BigDecimal("86000"),
                "Chuyện tình thời đi học ở làng Đo Đo.",
                280,
                "Tiếng Việt",
                literature
        ));
        nhatAnh.addBook(book(
                "Cho tôi xin một vé đi tuổi thơ",
                "978-604-2-08111",
                new BigDecimal("72000"),
                "Hồi ức tuổi thơ qua lời kể của một cậu bé.",
                220,
                "Tiếng Việt",
                literature
        ));
        authorRepository.save(nhatAnh);

        Author martin = new Author("Robert C. Martin", "unclebob@example.com");
        Book cleanCode = book(
                "Clean Code",
                "978-0-13-235088",
                new BigDecimal("350000"),
                "Cách viết code dễ đọc và dễ sửa.",
                464,
                "English",
                programming, skill
        );
        martin.addBook(cleanCode);
        authorRepository.save(martin);
    }

    private static Book book(
            String title,
            String isbn,
            BigDecimal price,
            String summary,
            int pages,
            String language,
            Category... categories
    ) {
        Book book = new Book(title, isbn, price);
        book.setDetail(new BookDetail(summary, pages, language));
        for (Category category : categories) {
            book.addCategory(category);
        }
        return book;
    }
}
