package com.ghada.library.libraryModel;

import java.time.LocalDate;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




@Document("borrows")
public class Borrow {
     @Id
    private String id;

    private String bookId;
    private String bookName;
    private String userName;
    private String userId;

    private LocalDate requestDate;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate actualReturnDate;

    private RequestStatus requestStatus;
    private BorrowStatus borrowStatus;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getBorrowDate() {
        return this.borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getActualReturnDate() {
        return this.actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public BorrowStatus getBorrowStatus() {
        return this.borrowStatus;
    }

    public void setBorrowStatus(BorrowStatus borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Borrow(String id, String bookId, String bookName, String userId, String userName, LocalDate requestDate, LocalDate borrowDate, LocalDate dueDate, LocalDate actualReturnDate, RequestStatus requestStatus, BorrowStatus borrowStatus) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.bookName = bookName;
        this.userName = userName;
        this.requestDate = requestDate;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.actualReturnDate = actualReturnDate;
        this.requestStatus = requestStatus;
        this.borrowStatus = borrowStatus;
    }
    public Borrow() {
    }

}
