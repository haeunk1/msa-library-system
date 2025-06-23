package com.example.rentalservice.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.example.rentalservice.exception.RentUnavailableException;

@Entity
@Table(name = "rental")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@EqualsAndHashCode
@ToString
public class Rental implements Serializable{

    private static final int MAX_RENT_LIMIT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status")
    private RentalStatus rentalStatus;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true) //고아 객체 제거 -> rental에서 컬렉션의 객체 삭제시, 해당 컬렉션의 entity삭제
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RentedItem> rentedItems = new HashSet<>();

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OverdueItem> overdueItems = new HashSet<>();

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReturnedItem> returnedItems = new HashSet<>();

    protected Rental(){}

    private Rental(Long memberId){
        this.memberId = memberId;
        this.rentalStatus = RentalStatus.RENT_AVAILABLE;
    }

    public static Rental of(Long memberId){
        return new Rental(memberId);
    }
    /* 대출하기 */
    public Rental rentBook(Long bookId){
        this.addRentedItem(RentedItem.of(bookId,LocalDate.now()));
        return this;
    }

    /* 반납하기 */
    public Rental returnBook(Long bookId){
        RentedItem rentedItem = this.rentedItems.stream()
        .filter(item -> item.getBookId().equals(bookId)).findFirst().get();
        this.addReturnedItem(ReturnedItem.of(rentedItem.getBookId(), LocalDate.now()));
        this.removeRentedItem(rentedItem);
        return this;
    }

    /* 연체처리 */
    public Rental overdueBook(Long bookId){
        RentedItem rentedItem = this.rentedItems.stream().filter(item -> item.getBookId().equals(bookId)).findFirst().get();
        this.addOverdueItem(OverdueItem.of(bookId, LocalDate.now()));
        this.removeRentedItem(rentedItem);
        return this;
    }

    /* 언체된 책 반납 */
    public Rental returnOverdueBook(Long bookId){
        OverdueItem overdueItem = this.overdueItems.stream().filter(item -> item.getBookId().equals(bookId)).findFirst().get();
        this.addReturnedItem(ReturnedItem.of(bookId,LocalDate.now()));
        this.removeOverdueItem(overdueItem);
        return this;
    }

    /* 대출 가능 처리 */
    public Rental makeRentalAbailable(){
        this.rentalStatus = RentalStatus.RENT_AVAILABLE;
        return this;
    }
    /* 대출 불가 처리 */
    public Rental makeRentUnavailable(){
        this.rentalStatus = RentalStatus.RENT_UNAVAILABLE;
        return this;
    }

    /* 대출 가능 여부 체크 */
    public void checkRentalAvailable() throws RentUnavailableException {
        if(this.rentalStatus.equals(RentalStatus.RENT_UNAVAILABLE)){
            throw new RentUnavailableException("연체 상태입니다. 도서를 대출하실 수 없습니다.");
        }
        if(this.getRentedItems().size() > MAX_RENT_LIMIT){
            throw new RentUnavailableException("대출 가능한 도서의 수는 "+( 5- this.getRentedItems().size())+"권 입니다.");
        }
    }

    /* 대출 항목 추가 */
    public Rental addRentedItem(RentedItem rentedItem){
        this.rentedItems.add(rentedItem);
        return this;
    }
    /* 대출 항목 삭제 */
    public Rental removeRentedItem(RentedItem rentedItem){
        this.rentedItems.remove(rentedItem);
        return this;
    }
    /* 연체 항목 추가 */
    public Rental addOverdueItem(OverdueItem overdueItem){
        this.overdueItems.add(overdueItem);
        return this;
    }
    /* 연체 항목 삭제 */
    public Rental removeOverdueItem(OverdueItem overdueItem){
        this.overdueItems.remove(overdueItem);
        return this;
    }

    /* 반납 항목 추가 */
    public Rental addReturnedItem(ReturnedItem returnedItem){
        this.returnedItems.add(returnedItem);
        return this;
    }
    /* 반납 항목 삭제 */
    public Rental removeReturnedItem(ReturnedItem returnedItem){
        this.returnedItems.remove(returnedItem);
        return this;
    }
    
}
