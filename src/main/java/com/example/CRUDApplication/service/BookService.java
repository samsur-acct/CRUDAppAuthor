package com.example.CRUDApplication.service;

import com.example.CRUDApplication.model.Book;
import java.util.List;

public interface BookService {
  List<Book> getAllBooks();

  Book getBookById(Long id);

  Book addBook(Book book);

  Book updateBook(Long id, Book newBookData);

  void deleteBook(Long id);
}
