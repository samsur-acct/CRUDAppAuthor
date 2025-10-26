package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

  @Autowired private BookService bookService;

  @GetMapping("/getallbooks")
  public ResponseEntity<List<Book>> getAllBooks() {
    List<Book> books = bookService.getAllBooks();
    if (books.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @GetMapping("/getbookbyid/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    Book book = bookService.getBookById(id);
    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  @PostMapping("/addbook")
  public ResponseEntity<Book> addBook(@RequestBody Book book) {
    Book savedBook = bookService.addBook(book);
    return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
  }

  @PutMapping("/updatebook/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
    Book updatedBook = bookService.updateBook(id, book);
    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
  }

  @DeleteMapping("/deletebook/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
