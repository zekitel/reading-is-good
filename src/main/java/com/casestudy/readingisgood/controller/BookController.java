package com.casestudy.readingisgood.controller;

import com.casestudy.readingisgood.dto.BookDto;
import com.casestudy.readingisgood.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/book/")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;


    @SneakyThrows
    @PostMapping("save")
    public ResponseEntity<BookDto> createNewBook(@RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.ok(bookService.createNewBook(bookDto));
    }

    @SneakyThrows
    @PutMapping("updateBookStock")
    public ResponseEntity<BookDto> updateBookStock(@RequestParam @Positive Long bookId, @NotNull @PositiveOrZero @RequestParam Long bookStock) {
        return ResponseEntity.ok(bookService.updateStock(bookId, bookStock));
    }

}

