# Repo inventory

Cập nhật theo index CodeGraph sau `codegraph init` (6 file, 20 node, 15 edge).

| Path | Vai trò |
| --- | --- |
| `pom.xml` | Spring Boot 4.1.1, Java 21, WAR, Thymeleaf, Web MVC, MySQL, DevTools |
| `src/main/java/org/fp/bt_ql_muon_sach/BtQlMuonSachApplication.java` | Entry point |
| `src/main/java/org/fp/bt_ql_muon_sach/ServletInitializer.java` | Deploy WAR |
| `src/main/resources/application.properties` | MySQL `bt_ql_muon_sach`, `open-in-view=false` |
| `src/main/java/org/fp/bt_ql_muon_sach/entity/` | Author, Book, BookDetail, Category |
| `src/main/java/org/fp/bt_ql_muon_sach/web/BookController.java` | Kệ sách, chi tiết, sách của tác giả |
| `src/test/java/org/fp/bt_ql_muon_sach/BtQlMuonSachApplicationTests.java` | FetchType LAZY/EAGER |
| `HELP.md` | Link tài liệu Spring do Initializr sinh |
| `AGENTS.md`, `AI_RULES.md`, `.cursor/rules/` | Quy định vận hành agent |
| `.codegraph/` | Index local, không commit dữ liệu index |

Chưa có: Docker, CI.
