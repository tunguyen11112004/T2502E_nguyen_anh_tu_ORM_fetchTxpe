package org.fp.bt_ql_muon_sach.repository;

import org.fp.bt_ql_muon_sach.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Trang chi tiết: join fetch 1-1 LAZY (detail) cùng N-1 (author).
     * Không kéo categories trong câu này.
     */
    @Query("""
            select b from Book b
            join fetch b.author
            left join fetch b.detail
            where b.id = :id
            """)
    Optional<Book> findWithAuthorAndDetail(@Param("id") Long id);

    /**
     * Query riêng cho N-N. Hai collection trong một join fetch
     * nhân dòng và có thể ném MultipleBagFetchException.
     */
    @Query("select b from Book b left join fetch b.categories where b.id = :id")
    Optional<Book> findWithCategories(@Param("id") Long id);
}
