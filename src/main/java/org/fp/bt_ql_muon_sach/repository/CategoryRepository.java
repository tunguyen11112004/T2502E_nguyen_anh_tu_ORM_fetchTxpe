package org.fp.bt_ql_muon_sach.repository;

import org.fp.bt_ql_muon_sach.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
