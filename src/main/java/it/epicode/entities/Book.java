package it.epicode.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class Book extends CatalogItem{

    private String author;
    private String genre;

    public Book(String isbnCode, String title, int publicationYear, int numberOfPages, String author, String genre) {
        super(isbnCode, title, publicationYear, numberOfPages);
        this.author = author;
        this.genre = genre;
    }
}
