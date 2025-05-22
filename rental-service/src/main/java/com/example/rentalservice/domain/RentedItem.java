package com.example.rentalservice.domain;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "rented_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
public class RentedItem implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "rented_date")
    private LocalDate rentedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne
    @JsonIgnoreProperties("rentedItems")
    private Rental rental;

    protected RentedItem (){}

    public RentedItem(Long bookId, String bookTitle, LocalDate rentedDate, LocalDate dueDate){
        this.bookId = bookId;
        this.rentedDate = rentedDate;
        this.dueDate = dueDate;
        this.bookTitle = bookTitle;
    }

    public static RentedItem of(Long bookId, String bookTitle, LocalDate rentedDate) {
        return new RentedItem(bookId,bookTitle,rentedDate,rentedDate.plusWeeks(2));
    }

}