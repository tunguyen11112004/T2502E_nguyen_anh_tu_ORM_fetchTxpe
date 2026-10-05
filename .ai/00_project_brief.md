# Project brief

- Tên: BT_QL_muon_sach
- Group: `org.fp`
- Package: `org.fp.bt_ql_muon_sach`
- Stack hiện có: Spring Boot 4.1.1, Java 21, Maven, packaging WAR, Thymeleaf, Spring Web MVC, MySQL connector, DevTools
- Mục tiêu tên dự án: quản lý mượn sách
- Trạng thái code: đã có entity Author, Book, BookDetail, Category; repository; `BookCatalogService`; `BookController`; Thymeleaf; datasource MySQL `bt_ql_muon_sach`.

## Open questions

- Actor, quy trình mượn/trả, hạn mức, phạt quá hạn chưa có trong code hoặc tài liệu nghiệp vụ.
- Chưa có `.env.example`, seed data, lệnh test ngoài test mặc định của Spring Boot.
