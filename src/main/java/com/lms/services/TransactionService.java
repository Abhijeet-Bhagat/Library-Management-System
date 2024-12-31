package com.lms.services;

import com.lms.exception.TxnServiceException;
import com.lms.models.Book;
import com.lms.models.Student;
import com.lms.models.Transaction;
import com.lms.models.TransactionType;
import com.lms.repository.TransactionRepository;
import com.lms.request.BookFilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    StudentService studentService;  //good practice is to communicate through service

    @Autowired
    BookService bookService;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${book.return.duedate}")
    int number_of_days;

    @Transactional
    public String issueTxn(int studentId, int bookId) {
        //student exists or not
        //book is present and available
        //create a transaction if success
        //make the book unavailable
        System.out.println("Inside transaction service");
        Student student = studentService.findStudentByStudentId(studentId);
        System.out.println("Inside transaction service - 2");

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

        transactionRepository.save(transaction);

        books.get(0).setStudent(student);
        bookService.createOrUpdateBook(books.get(0)); //if the id is already there then create will update the data

        return transaction.getExternalTxnId();
    }
    @Transactional
    public String returnTxn(int studentId, int bookId) {
        /*
        Student is valid or not
        If the book is issued to this particular student
        Calculate the fine
        Create a transaction -> save in transaction table
        Make the book available
         */
        Student student = studentService.findStudentByStudentId(studentId);

        if(student == null) {
            throw new TxnServiceException("Student doesn't exist");
        }

        List<Book> books = bookService.findBooks(BookFilterType.BOOK_ID, String.valueOf(bookId));

        if(books == null || books.size() == 0){
            throw new TxnServiceException("Book not present");
        }

        if(books.get(0).getStudent().getId() != studentId){
            throw new TxnServiceException("Book is not issued to this student");
        }

        //Finding
       Transaction issueTransaction  = transactionRepository.findTransactionsforReturn(books.get(0).getId(), studentId, "ISSUE");

        Transaction transaction = Transaction.builder()
                .student(student)
                .payment(calcFine(issueTransaction))
                .book(books.get(0))
                .transactionType(TransactionType.RETURN)
                .externalTxnId(UUID.randomUUID().toString())
                .build();
        transactionRepository.save(transaction);

        //make the book available
        books.get(0).setStudent(null);
        bookService.createOrUpdateBook(books.get(0));
        return transaction.getExternalTxnId();
    }

    private double calcFine(Transaction issueTransaction){
        long issueDate = issueTransaction.getTransactionDate().getTime();
        long returnDate = System.currentTimeMillis();
        double fine = 0.0;
        long diff = returnDate - issueDate;
        long daysPassed = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        if(daysPassed >= number_of_days)
            return (daysPassed - number_of_days) * 1.0;

        return 0.0;
    }
}
