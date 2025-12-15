package com.ghada.library.libraryRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ghada.library.libraryModel.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByTitleContainingIgnoreCase(String titlePart);
    List<Book> findByAuthorContainingIgnoreCase(String keyword);
    // Available books only
    List<Book> findByCopiesGreaterThan(int copies);

    //Combined search by author and title
    @Query("{ '$or': [ { 'title': { $regex: ?0, $options: 'i' } }, { 'author': { $regex: ?0, $options: 'i' } } ] }")
    List<Book> searchBookByNameAndAuthor(String keyword);


}
