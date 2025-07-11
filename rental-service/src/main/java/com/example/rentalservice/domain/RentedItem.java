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

    @ManyToOne
    @JsonIgnoreProperties("rentedItems")
    private Rental rental;

    protected RentedItem (){}

    public RentedItem(Long bookId){
        this.bookId = bookId;
        this.rentedDate = LocalDate.now();
        this.dueDate = this.rentedDate.plusWeeks(2);
    }

    public static RentedItem of(Long bookId) {
        return new RentedItem(bookId);
    }

}