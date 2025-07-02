package com.example.rentalservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.example.rentalservice.exception.RentUnavailableException;

public class RentalTest {
    public Rental getRental(){
        return Rental.of(1L);
    }
    public RentedItem getRentedItems(Long bookId){
        return RentedItem.of(bookId);
    }

    @Nested
    class 대출{
        @Test
        void 대출하기_성공(){
            // given
            Rental rental = getRental();
            RentedItem rentedItem = getRentedItems(1L);
            RentedItem rentedItem2 = getRentedItems(2L);


            // when
            rental.addRentedItem(rentedItem);
            rental.addRentedItem(rentedItem2);

            // then
            assertEquals(2, rental.getRentedItems().size());
        } 

        @Test
        void 대출권수_초과시_예외(){
            // given & when
            Rental rental = getRental();
            for(long i=0; i<6 ;i++){
                rental.addRentedItem(getRentedItems(i));
            }
            assertThrows(RentUnavailableException.class, () -> rental.checkRentalAvailable());
        }
    } 

    @Nested
    class 반납{
        @Test
        void 반납하기_성공(){
            //given
            Long bookId = 1L;
            Rental rental = getRental();
            RentedItem rentedItem = getRentedItems(bookId);
            
            //when
            rental.addRentedItem(rentedItem);
            rental.returnBook(bookId);
            
            //then
            assertTrue(rental.getReturnedItems().stream()
            .anyMatch(item -> item.getBookId().equals(bookId)));

            assertTrue(rental.getRentedItems().isEmpty());
        }
    }
}
