# Schema summary

Database: `bt_ql_muon_sach` trên MySQL `localhost:3306`. Hibernate `ddl-auto=update` tạo bảng.

| Bảng | Khóa |
| --- | --- |
| `authors` | `id` |
| `books` | `id`, FK `author_id`, FK unique `detail_id` |
| `book_details` | `id` |
| `categories` | `id`, `name` unique |
| `book_category` | `book_id`, `category_id` |

Fetch: `Book.author` EAGER; `Book.detail`, `Author.books`, `Book.categories`, `Category.books` LAZY; `BookDetail.book` EAGER.
