package com.example.rentalservice.application.port.in;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.example.rentalservice.domain.Rental;

public interface RentalUseCase {
    // Rental save(Rental rental);
    // Page<Rental> findRentalAll(Pageable pageable);
    // Optional<Rental> findRentalOne(Long id);
    // void delete(Long id);

    /* 대출 */
    Rental rentBook(Long memberId, Long bookId);
    /* 반납 */
    Rental returnBook(Long memberId, Long bookId);
    
    /* 연체 도서 반납 */
    Rental returnOverdueBook(Long memberId, Long bookId);
    /* 연체 상태로 변경 */
    Long beOverdueBook(Long rentalId);
    /* 연체 상태 해제하기 */
    Rental releaseOverdue(Long memberId);
    /* Rental 조회 */
    Optional<Rental> findRentalByMember(Long memberId);
}
