package com.ghada.library.libraryModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ghada.library.libraryService.BorrowService;
import com.ghada.library.libraryService.ReportService;

@Component
public class BorrowScheduler {
    @Autowired
    private ReportService BorrowReports;
    @Autowired
    private BorrowService borrowService;

    
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateLateBorrows() {
        List<Borrow> allBorrows = BorrowReports.getAllBorrows();
        for (Borrow borrow : allBorrows) {
            borrowService.checkLateBorrow(borrow);
        }
    }

}
