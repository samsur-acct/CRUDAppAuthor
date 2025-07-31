package com.example.CRUDApplication.service;

import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.repo.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleBook = new Book(1L, "Sample Title", "Sample Author", "123-456");
    }

    @Test
    void testGetAllBooks_ReturnsList() {
        when(bookRepo.findAll()).thenReturn(List.of(sampleBook));

        List<Book> books = bookService.getAllBooks();

        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        verify(bookRepo).findAll();
    }

    @Test
    void testGetBookById_Found() {
        when(bookRepo.findById(1L)).thenReturn(Optional.of(sampleBook));

        Book book = bookService.getBookById(1L);

        assertNotNull(book);
        assertEquals("Sample Title", book.getTitle());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepo.findById(99L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.getBookById(99L);
        });

        assertEquals("Book not found with ID: 99", exception.getMessage());
    }

    @Test
    void testGetBookById_Restricted() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.getBookById(2L);
        });

        assertEquals("Book with ID 2 is restricted and cannot be accessed.", exception.getMessage());
    }

    @Test
    void testAddBook_Success() {
        when(bookRepo.save(sampleBook)).thenReturn(sampleBook);

        Book saved = bookService.addBook(sampleBook);

        assertNotNull(saved);
        verify(bookRepo).save(sampleBook);
    }

    @Test
    void testUpdateBook_Success() {
        Book updatedData = new Book(null, "Updated Title", "Updated Author", null);

        when(bookRepo.findById(1L)).thenReturn(Optional.of(sampleBook));
        when(bookRepo.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book updated = bookService.updateBook(1L, updatedData);

        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Author", updated.getAuthor());
    }

    @Test
    void testUpdateBook_NotFound() {
        when(bookRepo.findById(99L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.updateBook(99L, sampleBook);
        });

        assertEquals("Book not found with ID: 99", exception.getMessage());
    }

    @Test
    void testDeleteBook_Success() {
        when(bookRepo.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepo).deleteById(1L);

        assertDoesNotThrow(() -> bookService.deleteBook(1L));
        verify(bookRepo).deleteById(1L);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepo.existsById(99L)).thenReturn(false);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.deleteBook(99L);
        });

        assertEquals("Book not found with ID: 99", exception.getMessage());
    }
}
