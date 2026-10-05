package org.fp.bt_ql_muon_sach.dto;

import java.math.BigDecimal;

public record BookCard(Long id, String title, String isbn, BigDecimal price, String authorName, Long authorId) {
}
