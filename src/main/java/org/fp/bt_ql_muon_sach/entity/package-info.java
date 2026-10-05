/**
 * Quan hệ của bài Book và fetch đi kèm.
 *
 * <ul>
 *   <li>Author 1 — N Book, Book N — 1 Author</li>
 *   <li>Book 1 — 1 BookDetail</li>
 *   <li>Book N — N Category</li>
 * </ul>
 *
 * Collection luôn LAZY. ManyToOne của Book sang Author là EAGER
 * vì mọi màn hình sách đều hiện tên tác giả.
 */
package org.fp.bt_ql_muon_sach.entity;
