package com.lms.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lms.BookSearchResponse;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int cost;
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToOne
    @JoinColumn    //creates a foreign key
    private Student student;

    @CreationTimestamp
    private Date createdOn;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("bookList")
    private Author author;

    @UpdateTimestamp
    private Date updatedOn;

    @OneToMany(mappedBy = "book")
    private List<Transaction> transactionList;

    public BookSearchResponse to(){
        return BookSearchResponse.builder()
                .author(author)
                .cost(cost).genre(genre).id(id).author(author).name(name).build();
    }
}
