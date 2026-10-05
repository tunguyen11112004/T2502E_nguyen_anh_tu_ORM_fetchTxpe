package org.fp.bt_ql_muon_sach.repository;

import org.fp.bt_ql_muon_sach.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Xử lý LAZY 1-N: một câu lấy tác giả kèm sách, tránh N+1
     * (mỗi tác giả một select books).
     */
    @Query("select distinct a from Author a left join fetch a.books where a.id = :id")
    Optional<Author> findByIdWithBooks(@Param("id") Long id);
}
