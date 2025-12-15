package com.ghada.library.libraryService;

import com.ghada.library.libraryModel.Book;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookServiceValidationDecorator implements BookService {
    
    private final BookService delegate;

    public BookServiceValidationDecorator(BookService bookService) {
        this.delegate = bookService;
    }

    private void validateId(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("Book ID cannot be null or empty.");
        }
    }

    private void validateBook(Book b) {
        if (b == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        if (!StringUtils.hasText(b.getTitle())) {
            throw new IllegalArgumentException("Book title cannot be empty.");
        }
        if (!StringUtils.hasText(b.getAuthor())) {
            throw new IllegalArgumentException("Book author cannot be empty.");
        }
    }
    @Override
    public List<Book> findAll() {
        return delegate.findAll();
    }

    @Override
    public Book findById(String id) {
        validateId(id);
        return delegate.findById(id);
    }

    @Override
    public Book create(Book b) {
        validateBook(b);
        return delegate.create(b);
    }

    @Override
    public Book update(String id, Book b) {
        validateId(id);
        validateBook(b);
        return delegate.update(id, b);
    }

    @Override
    public void delete(String id) {
        validateId(id);
        delegate.delete(id);
    }

}
