package org.fp.bt_ql_muon_sach.dto;

import java.math.BigDecimal;
import java.util.List;

public record BookDetailView(
        Long id,
        String title,
        String isbn,
        BigDecimal price,
        String authorName,
        Long authorId,
        String summary,
        Integer pageCount,
        String language,
        List<String> categories
) {
}
