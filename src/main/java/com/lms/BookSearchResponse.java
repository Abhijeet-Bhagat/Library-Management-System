package com.lms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lms.models.Author;
import com.lms.models.Genre;
import com.lms.models.Student;
import com.lms.models.Transaction;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookSearchResponse {

    private int id;
    private int cost;
    private String name;
    private Genre genre;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("bookList")
    private Author author;

}
