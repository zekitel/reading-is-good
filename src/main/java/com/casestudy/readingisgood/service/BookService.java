package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.BookDto;
import com.casestudy.readingisgood.entity.Book;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;

public interface BookService {

    @Transactional
    BookDto createNewBook(BookDto bookDto);

    @Transactional
    BookDto updateStock(Long id, Long stock) throws DbNotFoundException, StockValueChangedException;

    @SneakyThrows
    Book findBookById(Long bookId);
}
