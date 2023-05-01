package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.BookDTO;
import com.casestudy.readingisgood.entity.Book;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.ResourceAlreadyExistsException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import jakarta.transaction.Transactional;

public interface BookService {

    @Transactional
    BookDTO persist(BookDTO bookDto) throws ResourceAlreadyExistsException;

    @Transactional
    BookDTO updateStock(Long id, Long stock) throws DbNotFoundException, StockValueChangedException;

    Book findById(Long bookId) throws DbNotFoundException;
}
