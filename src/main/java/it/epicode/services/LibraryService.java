package it.epicode.services;

import it.epicode.entities.Book;
import it.epicode.entities.CatalogItem;
import it.epicode.entities.Loan;
import it.epicode.entities.User;
import it.epicode.utilities.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {

    public void addCatalogItem(CatalogItem item) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(item);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void removeCatalogItemByISBN(String isbn) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CatalogItem item = em.find(CatalogItem.class, isbn);
            if (item != null) {
                TypedQuery<Loan> query = em.createQuery(
                        "SELECT l FROM Loan l WHERE l.catalogItem.isbnCode = :isbn", Loan.class);
                query.setParameter("isbn", isbn);
                List<Loan> loans = query.getResultList();
                for (Loan loan : loans) {
                    em.remove(loan);
                }
                em.remove(item);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }


    public CatalogItem searchByISBN(String isbn) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(CatalogItem.class, isbn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<CatalogItem> searchByPublicationYear(int year) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM CatalogItem c WHERE c.publicationYear = :year", CatalogItem.class)
                    .setParameter("year", year)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Book> searchByAuthor(String author) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT b FROM Book b WHERE b.author = :author", Book.class)
                    .setParameter("author", author)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<CatalogItem> searchByTitle(String titlePart) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM CatalogItem c WHERE c.title LIKE :titlePart", CatalogItem.class)
                    .setParameter("titlePart", "%" + titlePart + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Loan> searchLoansByUserCardNumber(Long cardNumber) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Loan l WHERE l.user.cardNumber = :cardNumber AND l.actualReturnDate IS NULL", Loan.class)
                    .setParameter("cardNumber", cardNumber)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Loan> searchOverdueLoans() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Loan l WHERE l.expectedReturnDate < :today AND l.actualReturnDate IS NULL", Loan.class)
                    .setParameter("today", LocalDate.now())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public void addUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } finally {
            em.close();
        }
    }


    public void createLoan(Loan loan) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(loan);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void returnLoan(Long loanId, LocalDate returnDate) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Loan loan = em.find(Loan.class, loanId);
            if (loan != null) {
                loan.setActualReturnDate(returnDate);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }
}
