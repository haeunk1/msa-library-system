package com.example.rentalservice.domain;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "overdue_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
public class OverdueItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne
    @JsonIgnoreProperties("overdueItems")
    private Rental rental;

    protected OverdueItem(){}

    public OverdueItem(Long bookId, LocalDate dueDate){
        this.bookId = bookId;
        this.dueDate = dueDate;
    }

    public static OverdueItem of(Long bookId, LocalDate dueDate){
        return new OverdueItem(bookId,dueDate);
    }
}
