# Architecture map

```
Browser
    |
    v
BookController (Thymeleaf)
    |
    v
BookCatalogService  — map DTO trong @Transactional
    |
    v
BookRepository / AuthorRepository / CategoryRepository
    |
    v
MySQL bt_ql_muon_sach
    authors 1—N books N—1
    books 1—1 book_details (books.detail_id)
    books N—N categories qua book_category
```

Entry point: `BtQlMuonSachApplication`, `ServletInitializer`.
