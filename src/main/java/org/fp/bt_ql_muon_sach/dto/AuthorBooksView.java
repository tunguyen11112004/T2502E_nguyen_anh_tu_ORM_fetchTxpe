package org.fp.bt_ql_muon_sach.dto;

import java.util.List;

public record AuthorBooksView(Long id, String name, String email, List<BookCard> books) {
}
