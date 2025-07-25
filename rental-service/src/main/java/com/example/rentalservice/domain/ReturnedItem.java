package com.example.rentalservice.domain;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "rented_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
public class ReturnedItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "returned_date")
    private LocalDate returnedDate;

    @ManyToOne
    @JsonIgnoreProperties("returnedItems")
    private Rental rental;

    protected ReturnedItem(){}

    public ReturnedItem(Long bookId, LocalDate returnedDate){
        this.bookId = bookId;
        this.returnedDate = returnedDate;
    }

    public static ReturnedItem of(Long bookId, LocalDate returnedDate) {
        return new ReturnedItem(bookId,returnedDate);
    }
}
