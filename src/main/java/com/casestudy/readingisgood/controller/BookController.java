package com.casestudy.readingisgood.controller;

import com.casestudy.readingisgood.dto.BookDTO;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.ResourceAlreadyExistsException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import com.casestudy.readingisgood.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/book/")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;



    @PostMapping("create")
    public ResponseEntity<BookDTO> create(@RequestBody @Valid BookDTO bookDto) throws ResourceAlreadyExistsException {
        return ResponseEntity.ok(bookService.persist(bookDto));
    }


    @PutMapping("update-stock/{id}")
    public ResponseEntity<BookDTO> updateStock(@PathVariable @Positive Long id, @NotNull @PositiveOrZero @RequestParam Long stock) throws DbNotFoundException, StockValueChangedException {
        return ResponseEntity.ok(bookService.updateStock(id, stock));
    }

}

