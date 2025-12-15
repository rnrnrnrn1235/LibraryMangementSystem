package com.ghada.library.libraryDTOs;

public class BorrowRequestDTO {
    private String userId;
    private String bookId;

    public BorrowRequestDTO() {
    }

    public BorrowRequestDTO(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
