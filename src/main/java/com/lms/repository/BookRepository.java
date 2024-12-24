package com.lms.repository;

import com.lms.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    public List<Book> findByName(String name);

    @Query(value = "select * from book, author where author.name = :authorName and author.id = book.author_id", nativeQuery = true)
    List<Book> findByAuthor_Name(String authorName);

    List<Book> findByGenre(String genre);
}
