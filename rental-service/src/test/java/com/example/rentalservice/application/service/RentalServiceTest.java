package com.example.rentalservice.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.rentalservice.application.port.out.RentalRepository;
import com.example.rentalservice.application.service.Feign.MemberFeignClient;
import com.example.rentalservice.application.service.Feign.MemberFeignResponse;
import com.example.rentalservice.domain.Rental;
import com.example.rentalservice.domain.RentedItem;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {
    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private MemberFeignClient memberFeignClient;

    @InjectMocks
    private RentalService rentalService;

    private MemberFeignResponse getMemberInfo(){
        return new MemberFeignResponse(1L,1L,"testId","ADMIN","testName",false);
    }
    private Rental getRental(Long memberId){
        return Rental.of(memberId);
    }
    private RentedItem getRentedItem(Long bookId){
        return RentedItem.of(bookId);
    }

    @Nested
    class 대출{
        @Test
        void 대출_성공(){
            //given
            MemberFeignResponse mockMember = getMemberInfo();
            Rental rental = getRental(1L);
            RentedItem rentedItem = getRentedItem(1L);
            ReflectionTestUtils.setField(rental,"id", 1L);
            when(memberFeignClient.getMemberInfo(1L)).thenReturn(mockMember);
            when(rentalRepository.save(any(Rental.class))).thenReturn(rental);
            
            //when
            rental.addRentedItem(rentedItem);
            Rental savedRental = rentalService.rentBook(1L,1L);
            
            //then
            assertNotNull(savedRental);
            verify(rentalRepository).save(any(Rental.class));

        }

    }
}
