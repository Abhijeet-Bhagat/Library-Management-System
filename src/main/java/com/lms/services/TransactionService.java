package com.lms.services;

import com.lms.exception.TxnServiceException;
import com.lms.models.Book;
import com.lms.models.Student;
import com.lms.models.Transaction;
import com.lms.models.TransactionType;
import com.lms.repository.TransactionRepository;
import com.lms.request.BookFilterType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public class TransactionService {

    @Autowired
    StudentService studentService;  //good practice is to communicate through service

    @Autowired
    BookService bookService;

    @Autowired
    TransactionRepository transactionRepository;
    public void issueTxn(int studentId, int bookId) {
        //student exists or not
        //book is present and available
        //create a transaction if success
        //make the book unavailable

        Student student = studentService.findStudentByStudentId(studentId);

        if(student == null) {
            throw new TxnServiceException("Student doesn't exist");
        }

        List<Book> books = bookService.findBooks(BookFilterType.BOOK_ID, String.valueOf(bookId));

        if(books == null || books.size() == 0){
            throw new TxnServiceException("Book not present");
        }

        Book book = books.get(0);

        if(book.getStudent() != null){
            throw new TxnServiceException("Book is already assigned");
        }

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .book(books.get(0))
                .payment(books.get(0).getCost())
                .student(student)
                .build();
    }
}
