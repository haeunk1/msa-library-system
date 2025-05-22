package com.example.rentalservice.domain;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "overdue_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OverdueItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne
    @JsonIgnoreProperties("overdueItems")
    private Rental rental;

    protected OverdueItem(){}

    public OverdueItem(Long bookId, String bookTitle, LocalDate dueDate){
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.dueDate = dueDate;
    }

    public static OverdueItem of(Long bookId, String bookTitle, LocalDate dueDate){
        return new OverdueItem(bookId,bookTitle,dueDate);
    }
}
