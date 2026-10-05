package org.fp.bt_ql_muon_sach.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    /**
     * N-1: nhiều sách thuộc một tác giả.
     * EAGER: thẻ sách và trang chi tiết luôn hiện tên tác giả.
     * Đây là quan hệ đơn (một dòng), không nhân bản kết quả như collection.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    /**
     * 1-1, phía giữ FK detail_id. LAZY: kệ sách không cần tóm tắt và số trang.
     * optional = false vì mỗi cuốn luôn có một hồ sơ — Hibernate mới tạo được proxy
     * mà không cần bytecode enhancement. Phía không có FK (mappedBy) mà ghi LAZY
     * vẫn bị tải ngay; đó là tình huống 1-1 phải xử lý bằng cách đặt LAZY ở đây.
     * Trang chi tiết vẫn join fetch, không trông chờ view chạm getter.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "detail_id", nullable = false, unique = true)
    private BookDetail detail;

    /**
     * N-N. LAZY: thể loại chỉ cần ở trang chi tiết.
     * EAGER trên collection này sẽ nhân dòng khi join với collection khác
     * (cartesian product) và dễ gặp MultipleBagFetchException.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new LinkedHashSet<>();

    protected Book() {
    }

    public Book(String title, String isbn, BigDecimal price) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
    }

    public void setDetail(BookDetail detail) {
        this.detail = detail;
        if (detail != null) {
            detail.setBook(this);
        }
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.getBooks().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookDetail getDetail() {
        return detail;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Book book)) {
            return false;
        }
        return id != null && id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
