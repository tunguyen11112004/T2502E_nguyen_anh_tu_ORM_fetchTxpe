package org.fp.bt_ql_muon_sach;

import org.fp.bt_ql_muon_sach.entity.Book;
import org.fp.bt_ql_muon_sach.repository.BookRepository;
import org.fp.bt_ql_muon_sach.service.BookCatalogService;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class BtQlMuonSachApplicationTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCatalogService catalogService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void contextLoads() {
        assertThat(bookRepository.count()).isEqualTo(3);
    }

    @Test
    void eagerAuthorStaysLoadedAfterSessionCloses() {
        Book book = loadDetachedBook();

        assertThat(Hibernate.isInitialized(book.getAuthor())).isTrue();
        assertThat(book.getAuthor().getName()).isNotBlank();
    }

    @Test
    void lazyDetailAndCategoriesAreNotTouchedOnTheCatalogQuery() {
        Book book = loadDetachedBook();

        assertThat(Hibernate.isInitialized(book.getDetail())).isFalse();
        assertThat(Hibernate.isInitialized(book.getCategories())).isFalse();
        assertThatThrownBy(() -> book.getCategories().size())
                .isInstanceOf(LazyInitializationException.class);
    }

    @Test
    void detailPageLoadsOneToOneAndManyToManyInsideTheTransaction() {
        Long id = loadDetachedBook().getId();

        var view = catalogService.getBook(id);

        assertThat(view.summary()).isNotBlank();
        assertThat(view.pageCount()).isPositive();
        assertThat(view.categories()).isNotEmpty();
        assertThat(view.authorName()).isNotBlank();
    }

    @Test
    void authorPageLoadsTheOneToManyCollectionByJoinFetch() {
        Long authorId = loadDetachedBook().getAuthor().getId();

        var view = catalogService.getAuthorBooks(authorId);

        assertThat(view.books()).isNotEmpty();
        assertThat(view.books()).allSatisfy(card -> assertThat(card.authorName()).isEqualTo(view.name()));
    }

    private Book loadDetachedBook() {
        return transactionTemplate.execute(status -> bookRepository.findAll().getFirst());
    }
}
