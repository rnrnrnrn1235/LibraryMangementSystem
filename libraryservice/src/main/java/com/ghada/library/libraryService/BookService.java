package com.ghada.library.libraryService;

import java.util.List;

import com.ghada.library.libraryModel.Book;


public interface BookService {

    List<Book> findAll();
    Book findById(String id);
    Book create(Book b);
    Book update(String id, Book b);
    void delete(String id);
   

}
