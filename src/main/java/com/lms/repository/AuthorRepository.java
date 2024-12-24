package com.lms.repository;

import com.lms.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    /*
     Native Query: It is the SQL query and it will use the column names for query
     JPQL Query  : It is JPA query and it uses the Java Class names and attributes for query
     By default native query is set to false
     */

   // @Query(value = "select * from author where email = :auth_email", nativeQuery = true)
    public Author findAuthorByEmail(String auth_email);
}
