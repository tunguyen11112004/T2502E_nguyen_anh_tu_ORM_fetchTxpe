# BA — BT_QL_muon_sach

## 1. Mục tiêu

Nhân viên nhà sách quản lý đầu sách, xem kệ và mở hồ sơ một cuốn. Hệ thống phải hiện đúng tác giả, tóm tắt và thể loại mà không kéo thừa dữ liệu mỗi lần mở danh sách.

## 2. Tác nhân

| Tác nhân | Việc làm |
| --- | --- |
| Thủ thư | Xem kệ sách, mở chi tiết, xem các sách của một tác giả |
| Hệ thống ORM | Ánh xạ bảng và chọn thời điểm tải quan hệ |

Phạm vi bài này là đọc dữ liệu mẫu. Chưa có thêm, sửa, xóa trên giao diện.

## 3. Use case

### UC01 — Xem kệ sách

- Thủ thư mở trang chủ.
- Hệ thống liệt kê tên sách, ISBN, giá, tên tác giả.
- Không hiện tóm tắt, số trang, thể loại.

### UC02 — Xem chi tiết một cuốn

- Thủ thư chọn một cuốn trên kệ.
- Hệ thống hiện tóm tắt, số trang, ngôn ngữ và các thể loại.
- Có đường sang hồ sơ tác giả.

### UC03 — Xem sách của một tác giả

- Thủ thư bấm tên tác giả.
- Hệ thống hiện email và mọi đầu sách của người đó.

## 4. Quy tắc nghiệp vụ

- Mỗi cuốn có đúng một tác giả và đúng một hồ sơ chi tiết.
- Một tác giả có thể có nhiều cuốn.
- Một cuốn thuộc nhiều thể loại; một thể loại có nhiều cuốn.
- ISBN là duy nhất. Giá và số trang là số dương. Tên sách, tên tác giả, tên thể loại là bắt buộc.

## 5. Dữ liệu

| Thực thể | Việc nghiệp vụ | Bảng |
| --- | --- | --- |
| Author | Người viết | `authors` |
| Book | Đầu sách trên kệ | `books` (`author_id`) |
| BookDetail | Hồ sơ dài của cuốn sách | `book_details`; `books.detail_id` unique |
| Category | Thể loại | `categories`, bảng nối `book_category` |

Quan hệ:

- Author **1-N** Book, Book **N-1** Author
- Book **1-1** BookDetail (khóa ngoại `books.detail_id`, để LAZY trên kệ sách có hiệu lực)
- Book **N-N** Category

Dữ liệu mẫu: Nguyễn Nhật Ánh có hai cuốn văn học; Robert C. Martin có Clean Code thuộc Lập trình và Kỹ năng.

## 6. Màn hình quyết định FetchType

| Màn hình | Cần | Không cần | Cách tải |
| --- | --- | --- | --- |
| Kệ sách | Book, Author | BookDetail, Category | `Book.author` = EAGER. Detail và categories = LAZY, không đụng tới |
| Chi tiết sách | Book, Author, BookDetail, Category | Sách khác của tác giả | Join fetch detail; query riêng cho categories |
| Sách của tác giả | Author, các Book | BookDetail, Category | `Author.books` = LAZY, join fetch khi vào đúng trang này |

`open-in-view` tắt. Giao diện chỉ nhận DTO đã map xong trong transaction, nên đóng session không làm vỡ trang.

## 7. Ngoài phạm vi

Thêm sách, sửa giá, xóa, đăng nhập, thanh toán.
