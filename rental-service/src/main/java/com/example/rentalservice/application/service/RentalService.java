package com.example.rentalservice.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.rentalservice.application.port.in.RentalUseCase;
import com.example.rentalservice.application.port.out.RentalRepository;
import com.example.rentalservice.domain.Rental;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalService implements RentalUseCase{

    private final RentalRepository rentalRepository;

    /* 도서 대출하기기 */
    @Override
    public Rental rentBook(Long memberId, Long bookId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rentBook'");
    }

    @Override
    public Rental returnBook(Long memberId, Long bookId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }

    @Override
    public Rental returnOverdueBook(Long memberId, Long bookId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnOverdueBook'");
    }

    @Override
    public Long beOverdueBook(Long rentalId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beOverdueBook'");
    }

    @Override
    public Rental releaseOverdue(Long memberId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'releaseOverdue'");
    }

    @Override
    public Optional<Rental> findRentalByMember(Long memberId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRentalByMember'");
    }
    
}
