package com.example.rentalservice.application.service;

import java.lang.reflect.Member;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.rentalservice.application.port.in.RentalUseCase;
import com.example.rentalservice.application.port.out.RentalRepository;
import com.example.rentalservice.application.service.Feign.MemberFeignClient;
import com.example.rentalservice.application.service.Feign.MemberFeignResponse;
import com.example.rentalservice.domain.Rental;
import com.example.rentalservice.domain.RentedItem;
import com.example.rentalservice.exception.RentalException;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalService implements RentalUseCase{

    private final RentalRepository rentalRepository;
    private final MemberFeignClient memberFeignClient;

    /* 도서 대출하기기 */
    @Override
    public Rental rentBook(Long memberId, Long bookId) {
        
        try {
            MemberFeignResponse memberResponse = memberFeignClient.getMemberInfo(memberId);

            if (memberResponse.blocked()) {
                throw new RentalException("회원이 대출 정지 상태입니다.");
            }

            RentedItem rentedItem = RentedItem.of(bookId);
            Rental rental = Rental.of(memberId);
            rental.addRentedItem(rentedItem);
        
            return rentalRepository.save(rental);

        } catch (FeignException e) {
            if (e.status() == 400 && e.contentUTF8().contains("MEMBER0001")) {
                throw new RentalException("존재하지 않는 회원입니다.");
            }
            throw new RentalException("회원 조회 실패: " + e.getMessage());
        }

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
