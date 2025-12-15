package com.ghada.library.libraryController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ghada.library.libraryDTOs.BorrowRequestDTO;
import com.ghada.library.libraryModel.Book;
import com.ghada.library.libraryModel.Borrow;
import com.ghada.library.libraryService.BorrowService;
import com.ghada.library.libraryService.ReportService;
import com.ghada.library.Security.JwtService;

@RestController
@RequestMapping("/library/borrow")
public class BorrowController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    @GetMapping("/getAllBorrows")
    public List<Borrow> getAllBorrows() {
        return reportService.getAllBorrows();
    }

    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    @GetMapping("/getAllBorrowedBooks")
    public List<Book> getAllBorrowedBooks() {
        return reportService.getAllBorrowedBooks();
    }

    // get borrow history per user
    @GetMapping("/history/{id}")
    public List<Borrow> getBorrowByUserId(@PathVariable String id) {
        return reportService.getBorrowsByUserId(id);
    }

    @PostMapping("/request")
    public Borrow borrowBook(@RequestBody BorrowRequestDTO req, @RequestHeader("Authorization") String token) {
        String userId = jwtService.extractId(token.substring(7));
        req.setUserId(userId);
        if (req.getBookId() == null || req.getBookId().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
        return borrowService.createBorrowRequest(req);
    }

    @PostMapping("/return/{borrowId}")
    public Borrow returnBorrowedBook(
            @PathVariable String borrowId,
            @RequestHeader("Authorization") String token) {

        String userId = jwtService.extractId(token.substring(7));
        return borrowService.returnBorrow(borrowId, userId);
    }

    @PostMapping("/cancel/{borrowId}")
    public Borrow cancelBorrowedBook(
            @PathVariable String borrowId,
            @RequestHeader("Authorization") String token) {

        String userId = jwtService.extractId(token.substring(7));
        return borrowService.cancelBorrow(borrowId, userId);
    }

    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    @PostMapping("/approve/{borrowId}")
    public Borrow approveBorrowedBook(@PathVariable String borrowId) {
        return borrowService.approveBorrow(borrowId);
    }

    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    @PostMapping("/reject/{borrowId}")
    public Borrow rejectBorrowedBook(@PathVariable String borrowId) {
        return borrowService.rejectBorrow(borrowId);
    }
    
}
