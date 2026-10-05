# Module: bootstrap

- Mục đích: khởi động `BT_QL_muon_sach` và phục vụ kệ sách.
- Màn hình: `/books`, `/books/{id}`, `/authors/{id}`.
- Entity: `Author`, `Book`, `BookDetail`, `Category` trong `org.fp.bt_ql_muon_sach.entity`.
- Class chính: `BtQlMuonSachApplication`, `ServletInitializer`, `BookController`, `BookCatalogService`.
- Phụ thuộc: Spring Boot Web MVC, Thymeleaf, Spring Data JPA, MySQL `bt_ql_muon_sach`.
- Test: `BtQlMuonSachApplicationTests` (H2 trong `src/test`).
