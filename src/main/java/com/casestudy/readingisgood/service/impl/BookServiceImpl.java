package com.casestudy.readingisgood.service.impl;

import com.casestudy.readingisgood.dto.BookDto;
import com.casestudy.readingisgood.entity.Book;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import com.casestudy.readingisgood.repository.BookRepository;
import com.casestudy.readingisgood.service.BookService;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookDto createNewBook(BookDto bookDto) {
        log.info("Book create started. reqeust data: . bookDto:{}", bookDto);
        Book book = modelMapper.map(bookDto, Book.class);
        Book result = bookRepository.save(book);

        bookDto = BookDto.builder()
                .title(result.getTitle())
                .isbn(result.getIsbn())
                .price(result.getPrice())
                .stock(result.getStock())
                .author(result.getAuthor())
                .id(result.getId())
                .build();
        log.info("Book create finished successfully. response data: . bookDto:{}", bookDto);
        return bookDto;
    }

    @Override
    @Transactional
    public BookDto updateStock(Long id, Long stock) throws DbNotFoundException, StockValueChangedException {
        log.info("Book update stock started. Coming data id: {}, stock: {} ", id, stock);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new DbNotFoundException("There is No Book Record on Database with id: " + id));
        book.setStock(stock);
        try {
            book = bookRepository.save(book);
        } catch (OptimisticLockException ex) {
            throw new StockValueChangedException("Book stock value is changed");
        }
        log.info("Book update stock finished. Response data of book: {} ", book);
        return modelMapper.map(book, BookDto.class);
    }

    @SneakyThrows
    @Override
    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new DbNotFoundException("The book is not found"));
    }
}
