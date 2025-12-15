package com.ghada.library.libraryDTOs;



public class MemberDashboardDTO {
    
    private long activeBorrowCount;
    private long overdueCount;
    private long historyCount;

    public MemberDashboardDTO(long activeBorrowCount, long overdueCount, long historyCount) {
        this.activeBorrowCount = activeBorrowCount;
        this.overdueCount = overdueCount;
        this.historyCount = historyCount;
    }


    public long getActiveBorrowCount() {
        return this.activeBorrowCount;
    }

    public void setActiveBorrowCount(long activeBorrowCount) {
        this.activeBorrowCount = activeBorrowCount;
    }

    public long getOverdueCount() {
        return this.overdueCount;
    }

    public void setOverdueCount(long overdueCount) {
        this.overdueCount = overdueCount;
    }

    public long getHistoryCount() {
        return this.historyCount;
    }

    public void setHistoryCount(long historyCount) {
        this.historyCount = historyCount;
    }
    
}