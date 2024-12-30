package com.lms.controller;

import com.lms.models.Transaction;
import com.lms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/issue")
    public void issueTxn(@RequestParam("studentId") int studentId,
                         @RequestParam("bookId") int bookId){

        transactionService.issueTxn(studentId, bookId);
    }
}
