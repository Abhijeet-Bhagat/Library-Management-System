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
    public String issueTxn(@RequestParam("studentId") int studentId,
                         @RequestParam("bookId") int bookId){

       return transactionService.issueTxn(studentId, bookId);
    }

    @PostMapping("/transaction/return")
    public String returnTxn(@RequestParam("studentId") int studentId,
                           @RequestParam("bookId") int bookId){

        return transactionService.returnTxn(studentId, bookId);
    }

}
