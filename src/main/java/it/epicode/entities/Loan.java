package it.epicode.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_card_number")
    private User user;

    @ManyToOne
    @JoinColumn(name = "catalog_item_isbn")
    private CatalogItem catalogItem;

    @Column(name = "loan_start_date")
    private LocalDate loanStartDate;

    @Column(name = "expected_return_date")
    private LocalDate expectedReturnDate;

    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;

    public Loan(User user, CatalogItem catalogItem, LocalDate loanStartDate) {
        this.user = user;
        this.catalogItem = catalogItem;
        this.loanStartDate = loanStartDate;
        this.expectedReturnDate = loanStartDate.plusDays(30);
    }

}
