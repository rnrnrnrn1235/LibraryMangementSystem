package com.ghada.library.libraryDTOs;

public class AdminDashboardDTO {
    
    //Note: the books being in admin dashboard here only for JSON
    //Formatting and data isolation from the book, borrowed, and
    //user database. That also goes for other DTOs.
    
    private long totalUsers;
    private long totalBooks;
    private long totalBorrowedBooks;
    private long totalOverdueBooks;
    private long totalPendingReturns;

    public AdminDashboardDTO(long totalUsers, long totalBooks, long totalBorrowedBooks, long totalOverdueBooks, long totalPendingReturns) {
        this.totalUsers = totalUsers;
        this.totalBooks = totalBooks;
        this.totalBorrowedBooks = totalBorrowedBooks;
        this.totalOverdueBooks = totalOverdueBooks;
        this.totalPendingReturns = totalPendingReturns;
    }

    public long getTotalPendingReturns() {
        return this.totalPendingReturns;
    }

    public void setTotalPendingReturns(long totalPendingReturns) {
        this.totalPendingReturns = totalPendingReturns;
    }

    public long getTotalUsers() {
        return this.totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalBooks() {
        return this.totalBooks;
    }

    public void setTotalBooks(long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getTotalBorrowedBooks() {
        return this.totalBorrowedBooks;
    }

    public void setTotalBorrowedBooks(long totalBorrowedBooks) {
        this.totalBorrowedBooks = totalBorrowedBooks;
    }

    public long getTotalOverdueBooks() {
        return this.totalOverdueBooks;
    }

    public void setTotalOverdueBooks(long totalOverdueBooks) {
        this.totalOverdueBooks = totalOverdueBooks;
    }
}
