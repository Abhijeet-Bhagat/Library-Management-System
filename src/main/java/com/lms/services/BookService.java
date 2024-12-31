package com.lms.services;

import com.lms.models.Author;
import com.lms.models.Book;
import com.lms.repository.AuthorRepository;
import com.lms.repository.BookRepository;
import com.lms.request.BookCreateRequest;
import com.lms.request.BookFilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;
    public void createOrUpdateBook(BookCreateRequest bookCreateRequest){

        Book book = bookCreateRequest.to();

        createOrUpdateBook(book);
    }

    public void createOrUpdateBook(Book book){
        Author author = book.getAuthor();

        //first check if the author exists in DB or not, if not then only save it
        Author authemail = authorRepository.findAuthorByEmail(author.getEmail());
        System.out.println("Author Email: " + (authemail != null ? authemail.getEmail() : "null"));
        Author authsaved;
        if(authemail == null){
            authsaved = authorRepository.save(author);
        }else{
            book.setAuthor(authemail);
        }
        bookRepository.save(book);
    }
    public List<Book> findBooks(BookFilterType bookFilterType, String value){

        switch (bookFilterType){
            case NAME :
                return bookRepository.findByName(value);
            case AUTHOR:
                return bookRepository.findByAuthor_Name(value);
            case GENRE:
                return bookRepository.findByGenre(value);
            case BOOK_ID:
                return bookRepository.findAllById(Collections.singletonList(Integer.parseInt(value)));
            default:
                return new ArrayList<>((Collection) Book.builder().name("Nothing").build());
        }

    }
}
