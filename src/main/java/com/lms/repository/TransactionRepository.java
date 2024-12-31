package com.lms.repository;

import com.lms.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "select * from transaction where book_id = :bookId and student_id = :studentId" +
            " and transaction_type = :type order by id desc limit 1" , nativeQuery = true)
    Transaction findTransactionsforReturn(int bookId, int studentId, String type);

}
