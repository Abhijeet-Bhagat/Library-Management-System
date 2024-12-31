package com.lms.controller;

import com.lms.BookSearchResponse;
import com.lms.request.BookCreateRequest;
import com.lms.request.BookFilterType;
import com.lms.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    @Autowired
    BookService bookService;


    @PostMapping("/book")
    public void createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest){ //@Valid annotation is required for the validations put in BookCreateRequest to work
        bookService.createOrUpdateBook(bookCreateRequest);
        System.out.println("Book created successfully");
    }

    @GetMapping("/book/search")
    public List<BookSearchResponse> findBooks(@RequestParam("filter") String bookFilterType,
                                              @RequestParam("value") String value){
        BookFilterType filter = null;
        try {
            filter = filter.valueOf(bookFilterType.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid filter type. Valid values are: " + Arrays.toString(BookFilterType.values()));
        }

        return bookService.findBooks(filter, value).stream()
                .map(book -> book.to())
                .collect(Collectors.toList());
    }
}
