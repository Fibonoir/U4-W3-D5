package it.epicode.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "magazines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class Magazine extends CatalogItem{

    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    public Magazine(String isbnCode, String title, int publicationYear, int numberOfPages, Periodicity periodicity) {
        super(isbnCode, title, publicationYear, numberOfPages);
        this.periodicity = periodicity;
    }
}
