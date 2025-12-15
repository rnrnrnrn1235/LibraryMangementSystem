package com.ghada.library.libraryService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghada.library.libraryModel.Book;
import com.ghada.library.libraryModel.Borrow;
import com.ghada.library.libraryModel.User;
import com.ghada.library.libraryModel.BorrowStatus;
import com.ghada.library.libraryModel.RequestStatus;
import com.ghada.library.libraryRepository.BookRepository;
import com.ghada.library.libraryRepository.BorrowRepository;
import com.ghada.library.libraryRepository.UserRepository;

@Service
public class ReportService {

    @Autowired
    BorrowRepository borrowRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    BookRepository bookRepo;

    // Gets lists of request status
    public List<Borrow> getBorrowsByRequestStatus(RequestStatus r_status) {
        return borrowRepo.findByRequestStatus(r_status);
    }

    public long countRequestByStatus(RequestStatus r_status) {
        return borrowRepo.countByRequestStatus(r_status);
    }

    // Get borrow counts by status
    public long countBorrowsByStatus(BorrowStatus b_status) {
        return borrowRepo.countByBorrowStatus(b_status);
    }

    // list of borrowing
    public List<Borrow> getBorrowsByBorrowStatus(BorrowStatus b_status) {
        return borrowRepo.findByBorrowStatus(b_status);
    }

    public long countTotalBooks() {
        return bookRepo.count();
    }

    // count currently borrowed
    public long countCurrentlyBorrowedBooks() {
        return borrowRepo.countByBorrowStatusIn(
                List.of(BorrowStatus.BORROWED, BorrowStatus.LATE, BorrowStatus.VERY_LATE));
    }

    // Get all borrowed books
    public List<Book> getAllBorrowedBooks() {
        List<BorrowStatus> statuses = List.of(
                BorrowStatus.BORROWED,
                BorrowStatus.LATE,
                BorrowStatus.VERY_LATE);

        return borrowRepo.findByBorrowStatusIn(statuses).stream()
                .map(b -> bookRepo.findById(b.getBookId()).orElse(null))
                .filter(book -> book != null)
                .collect(Collectors.toList());
    } // might simplify logic

    // get all users who are borrowing
    public long countActiveBorrowers() {
        return borrowRepo.findByBorrowStatusIn(
                List.of(BorrowStatus.BORROWED, BorrowStatus.LATE, BorrowStatus.VERY_LATE))
                .stream()
                .map(Borrow::getUserId)
                .distinct()
                .count();
    }

    public long countPendingReturns() {
        return borrowRepo.countByBorrowStatusIn(
                List.of(BorrowStatus.BORROWED, BorrowStatus.LATE));
    }

    public long countBooksIssuedToday() {
        LocalDate today = LocalDate.now();
        return borrowRepo.countByBorrowDate(today);
    }

    public long countBooksReturnedToday() {
        LocalDate today = LocalDate.now();
        return borrowRepo.countByActualReturnDate(today);
    }

    // get everything in borrow repo
    public List<Borrow> getAllBorrows() {
        return borrowRepo.findAll();
    }

    // Get the borrows by userID (might remove if redundant in front)
    public List<Borrow> getBorrowsByUserId(String userId) {
        return borrowRepo.findByUserId(userId);
    }

    public List<Book> getAllBorrowedBooksByStatus(BorrowStatus status) {
        return borrowRepo.findByBorrowStatus(status).stream()
                .map(b -> bookRepo.findById(b.getBookId()).orElse(null))
                .filter(book -> book != null)
                .collect(Collectors.toList());
    }

    public List<User> getAllBorrowedUsersByStatus(BorrowStatus status) {
        return borrowRepo.findByBorrowStatus(status).stream()
                .map(b -> userRepo.findById(b.getUserId()).orElse(null))
                .filter(user -> user != null)
                .collect(Collectors.toList());
    }

    // For member dashboard
    public long countActiveBorrowForUser(String userId) {
        return borrowRepo.countByUserIdAndBorrowStatus(userId, BorrowStatus.BORROWED);
    }

    public long countOverdueForUser(String userId) {
        return borrowRepo.countByUserIdAndBorrowStatusIn(userId,
                List.of(BorrowStatus.LATE, BorrowStatus.VERY_LATE));
    }

    public long countHistoryForUser(String userId) {
        return borrowRepo.countByUserId(userId);
    }

}
