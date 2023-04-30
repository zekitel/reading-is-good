package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.BookDto;
import com.casestudy.readingisgood.entity.Book;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import com.casestudy.readingisgood.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;


    @Autowired
    private BookRepository bookRepository;

    @Test
    void createNewBook() {


        List<Book> all = bookRepository.findAll();

        assertThat(all).hasSize(4);

        BookDto book1 = BookDto.builder()
                .title("test_book1")
                .author("test_author1")
                .isbn("12312334561")
                .price(10.0)
                .stock(4L)
                .build();

        BookDto newBook = bookService.createNewBook(book1);
        all = bookRepository.findAll();

        assertThat(newBook.getId()).isNotNull();

        assertThat(all).hasSize(5);
        assertThat(all.get(4).getIsbn()).isEqualTo(book1.getIsbn());
    }

    @Test
    void updateStock() throws DbNotFoundException, StockValueChangedException {

        List<Book> all = bookRepository.findAll();
        assertThat(all.get(0).getStock()).isEqualTo(4);
        assertThat(all.get(0).getId()).isEqualTo(1);

        Long stock = 190L;
        bookService.updateStock(all.get(0).getId(), stock);

        Book book = bookRepository.findById(all.get(0).getId()).orElseThrow();
        assertThat(book.getStock()).isEqualTo(stock);
    }

    @Test
    void findBookById() {
        List<Book> all = bookRepository.findAll();
        assertThat(all.get(0).getStock()).isEqualTo(4);
        assertThat(all.get(0).getId()).isEqualTo(1);


        Book bookById = bookService.findBookById(all.get(0).getId());

        assertThat(bookById.getStock()).isEqualTo(all.get(0).getStock());
        assertThat(bookById.getIsbn()).isEqualTo(all.get(0).getIsbn());


    }
}