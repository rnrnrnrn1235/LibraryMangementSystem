package com.ghada.library.libraryRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ghada.library.libraryModel.Borrow;
import com.ghada.library.libraryModel.BorrowStatus;
import com.ghada.library.libraryModel.RequestStatus;

public interface BorrowRepository extends MongoRepository<Borrow, String> {

    // Put Status Counts here
    List<Borrow> findByRequestStatus(RequestStatus r_status);

    List<Borrow> findByBorrowStatus(BorrowStatus status);

   long countByRequestStatus(RequestStatus r_status);

   List<Borrow> findByUserId(String userId);

    long countByUserId(String userId);

    long countByBorrowStatus(BorrowStatus status);

    long countByBorrowStatusIn(List<BorrowStatus> b_status);

    long countByBorrowDate(LocalDate date);

    long countByActualReturnDate(LocalDate date);

    List<Borrow> findByBorrowStatusIn(List<BorrowStatus> statuses);

    long countByUserIdAndBorrowStatus(String userId, BorrowStatus status);

    long countByUserIdAndBorrowStatusIn(String userId, List<BorrowStatus> statuses);

    @Query(value = "{ borrowStatus: { $in: ?0 } }", fields = "{ userId : 1 }")
    List<Borrow> findDistinctBorrowsByBorrowStatusIn(List<BorrowStatus> status);

    // put userSpecific counts (in borrow collection) here

    // Distinct active borrowers here

}
