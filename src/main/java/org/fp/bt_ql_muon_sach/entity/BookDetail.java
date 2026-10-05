package org.fp.bt_ql_muon_sach.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_details")
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String summary;

    @Column(nullable = false)
    private Integer pageCount;

    @Column(nullable = false, length = 40)
    private String language;

    /**
     * 1-1 phía ngược. EAGER: mở hồ sơ chi tiết thì luôn cần cuốn sách tương ứng.
     * FK nằm bên Book (detail_id), không nằm ở bảng này.
     */
    @OneToOne(mappedBy = "detail", fetch = FetchType.EAGER)
    private Book book;

    protected BookDetail() {
    }

    public BookDetail(String summary, Integer pageCount, String language) {
        this.summary = summary;
        this.pageCount = pageCount;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
