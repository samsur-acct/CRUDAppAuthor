package com.example.CRUDApplication.service;

import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.repo.BookRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {

  private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

  @Autowired
  private BookRepo bookRepo;

  @Override
  public List<Book> getAllBooks() {
    logger.info("Fetching all books");
    List<Book> books = bookRepo.findAll();
    logger.debug("Number of books fetched: {}", books.size());
    return books;
  }

  @Override
  public Book getBookById(Long id) {
    logger.debug("Fetching book with ID: {}", id);
    if (id == 2L) {
      logger.warn("Attempt to access restricted book with ID: {}", id);
      throw new IllegalArgumentException("Book with ID 2 is restricted and cannot be accessed.");
    }
    return bookRepo.findById(id)
            .orElseThrow(() -> {
              logger.error("Book not found with ID: {}", id);
              return new NoSuchElementException("Book not found with ID: " + id);
            });
  }

  @Override
  public Book addBook(Book book) {
    logger.info("Adding a new book with title: {}", book.getTitle());
    Book savedBook = bookRepo.save(book);
    logger.debug("Book saved with ID: {}", savedBook.getId());
    return savedBook;
  }

  @Override
  public Book updateBook(Long id, Book newBookData) {
    logger.info("Updating book with ID: {}", id);
    Book existingBook = bookRepo.findById(id)
            .orElseThrow(() -> {
              logger.error("Book not found for update with ID: {}", id);
              return new NoSuchElementException("Book not found with ID: " + id);
            });

    existingBook.setTitle(newBookData.getTitle());
    existingBook.setAuthor(newBookData.getAuthor());
    Book updatedBook = bookRepo.save(existingBook);
    logger.debug("Book updated with ID: {}", updatedBook.getId());
    return updatedBook;
  }

  @Override
  public void deleteBook(Long id) {
    logger.info("Deleting book with ID: {}", id);
    if (!bookRepo.existsById(id)) {
      logger.error("Book not found for delete with ID: {}", id);
      throw new NoSuchElementException("Book not found with ID: " + id);
    }
    bookRepo.deleteById(id);
    logger.info("Book deleted with ID: {}", id);
  }
}
