package com.lms.request;


import com.lms.models.Author;
import com.lms.models.Book;
import com.lms.models.Genre;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCreateRequest {

    @NotBlank //use this when you dont want null as well as blank "" empty string
    private String name;
    @Positive
    private int cost;
    @NotNull   //only for not null
    private Author author;
    @NotNull
    private Genre genre;


    public Book to(){
        return Book.builder()
                .cost(this.cost)
                .genre(this.genre)
                .name(this.name)
                .author(this.author)
                .build();
    }

}
