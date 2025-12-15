package com.ghada.library.libraryService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghada.library.libraryDTOs.BorrowRequestDTO;
import com.ghada.library.libraryModel.Book;
import com.ghada.library.libraryModel.Borrow;
import com.ghada.library.libraryModel.BorrowStatus;
import com.ghada.library.libraryModel.RequestStatus;
import com.ghada.library.libraryModel.User;
import com.ghada.library.libraryRepository.BookRepository;
import com.ghada.library.libraryRepository.BorrowRepository;
import com.ghada.library.libraryRepository.UserRepository;

@Service
public class BorrowService {
    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    BookRepository bookRepo;
    @Autowired
    UserRepository userRepo;

    // find book by id
    public Book findBookById(String bookId) {
        return bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // utility function along with is book available
    public Borrow getBorrowById(String borrowId) {

        return borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

    }

    // issuesBook
    // create borrow request
    public Borrow createBorrowRequest(BorrowRequestDTO req) {
        User user = getUserById(req.getUserId());
        if (user.isBlocked()) {
            throw new IllegalArgumentException("User is blocked from borrowing books.");
        }

        Book book = findBookById(req.getBookId());
        if (book.getCopies() <= 3) {
            throw new IllegalArgumentException("This book is currently not available for borrowing.");
        }

        Borrow borrow = new Borrow();
        borrow.setId(null);
        borrow.setUserId(req.getUserId());
        borrow.setBookId(req.getBookId());
        borrow.setBookName(bookRepo.findById(req.getBookId()).get().getTitle());
        borrow.setUserName(userRepo.findById(req.getUserId()).get().getUsername());
        borrow.setRequestDate(LocalDate.now());
        borrow.setRequestStatus(RequestStatus.REQUESTED);
        borrow.setBorrowStatus(null);
        borrow.setActualReturnDate(null);
        return borrowRepository.save(borrow);

    }

    // return book
    @Transactional
    public Borrow returnBorrow(String borrowId, String userId) {
        Borrow borrow = getBorrowById(borrowId);
        if (!borrow.getUserId().equals(userId)) {
            throw new RuntimeException("Access Denied: You cannot return a book you didn't borrow.");
        }
        if (borrow.getBorrowStatus() != BorrowStatus.BORROWED) {
            throw new IllegalArgumentException("This borrow cannot be returned because it is not currently borrowed.");
        }

        Book book = findBookById(borrow.getBookId());

        book.setCopies(book.getCopies() + 1);
        bookRepo.save(book);

        borrow = checkLateBorrow(borrow);
        borrow.setActualReturnDate(LocalDate.now());
        borrow.setBorrowStatus(BorrowStatus.RETURNED);

        return borrowRepository.save(borrow);
    }

    // get user by ID
    public User getUserById(String userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // check late borrow
    public Borrow checkLateBorrow(Borrow borrow) {
        if (!(borrow.getRequestStatus() == RequestStatus.APPROVED &&
                borrow.getBorrowStatus() == BorrowStatus.BORROWED &&
                borrow.getDueDate() != null)) {
            return borrow;
        }

        LocalDate today = LocalDate.now();
        long daysLate = ChronoUnit.DAYS.between(borrow.getDueDate(), today);

        if (daysLate > 30) {
            User user = getUserById(borrow.getUserId());
            user.setBlocked(true);
            userRepo.save(user);
            borrow.setBorrowStatus(BorrowStatus.VERY_LATE);
        } else if (daysLate > 0) {
            borrow.setBorrowStatus(BorrowStatus.LATE);
        }
        return borrowRepository.save(borrow);
    }

    // cancel borrow from user
    public Borrow cancelBorrow(String borrowId, String userId) {
        Borrow borrow = getBorrowById(borrowId);

        if (!borrow.getUserId().equals(userId)) {
            throw new RuntimeException("Access Denied: You cannot cancel a request you didn't make.");
        }

        if (borrow.getRequestStatus() != RequestStatus.REQUESTED) {
            throw new IllegalArgumentException("Only requested borrows can be cancelled.");
        }

        borrow.setRequestStatus(RequestStatus.CANCELLED);

        return borrowRepository.save(borrow);
    }

    // approve borrow from librarian
    @Transactional
    public Borrow approveBorrow(String borrowId) {
        Borrow borrow = getBorrowById(borrowId);
        if (borrow.getRequestStatus() != RequestStatus.REQUESTED) {
            throw new IllegalArgumentException("Only requested borrows can be approved.");
        }
        Book book = findBookById(borrow.getBookId());
        if (book.getCopies() <= 3) {
            throw new IllegalArgumentException("This book is currently not available for borrowing.");
        }
        book.setCopies(book.getCopies() - 1);
        bookRepo.save(book);

        borrow.setRequestStatus(RequestStatus.APPROVED);
        borrow.setBorrowStatus(BorrowStatus.BORROWED);
        LocalDate borrowDate = LocalDate.now();
        borrow.setBorrowDate(borrowDate);
        borrow.setDueDate(borrowDate.plusWeeks(1));
        return borrowRepository.save(borrow);
    }

    // reject borrow from librarian
    public Borrow rejectBorrow(String borrowId) {
        Borrow borrow = getBorrowById(borrowId);
        if (borrow.getRequestStatus() != RequestStatus.REQUESTED) {
            throw new IllegalArgumentException("Only requested borrows can be rejected.");
        }
        borrow.setRequestStatus(RequestStatus.REJECTED);
        return borrowRepository.save(borrow);
    }

}
