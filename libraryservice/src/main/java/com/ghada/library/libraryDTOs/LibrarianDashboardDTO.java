package com.ghada.library.libraryDTOs;



public class LibrarianDashboardDTO {
    
    private long issuedToday;
    private long returnedToday;
    private long activeBorrowers;
    private long pendingReturns;
    private long currentlyBorrowedBooks;

    public LibrarianDashboardDTO(long issuedToday, long returnedToday, long activeBorrowers, long pendingReturns, long currentlyBorrowedBooks) {
        this.issuedToday = issuedToday;
        this.returnedToday = returnedToday;
        this.activeBorrowers = activeBorrowers;
        this.pendingReturns = pendingReturns;
        this.currentlyBorrowedBooks = currentlyBorrowedBooks;
    }

    public long getCurrentlyBorrowedBooks() {
        return this.currentlyBorrowedBooks;
    }

    public void setCurrentlyBorrowedBooks(long currentlyBorrowedBooks) {
        this.currentlyBorrowedBooks = currentlyBorrowedBooks;
    }

    public long getIssuedToday() {
        return this.issuedToday;
    }

    public void setIssuedToday(long issuedToday) {
        this.issuedToday = issuedToday;
    }

    public long getReturnedToday() {
        return this.returnedToday;
    }

    public void setReturnedToday(long returnedToday) {
        this.returnedToday = returnedToday;
    }

    public long getActiveBorrowers() {
        return this.activeBorrowers;
    }

    public void setActiveBorrowers(long activeBorrowers) {
        this.activeBorrowers = activeBorrowers;
    }

    public long getPendingReturns() {
        return this.pendingReturns;
    }

    public void setPendingReturns(long pendingReturns) {
        this.pendingReturns = pendingReturns;
    }
}
