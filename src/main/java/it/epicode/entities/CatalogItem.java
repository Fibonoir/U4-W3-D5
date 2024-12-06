package it.epicode.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "catalog_item")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CatalogItem {

    @Id
    @Column(name = "isbn", nullable = false, unique = true)
    private  String isbnCode;

    private String title;

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "number_of_pages")
    private int numberOfPages;
}
