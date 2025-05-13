package com.example.bookservice.application.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import com.example.bookservice.application.port.in.BookUseCase;
import com.example.bookservice.application.port.in.Command.RegisterBookCommand;
import com.example.bookservice.application.port.in.Command.UpdateBookCommand;
import com.example.bookservice.application.port.out.BookMessagePort;
import com.example.bookservice.application.port.out.BookRepository;
import com.example.bookservice.application.service.feign.MemberFeignClient;
import com.example.bookservice.application.service.feign.MemberFeignResponse;
import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.BookCategory;
import com.example.bookservice.domain.BookStatus;
import com.example.bookservice.domain.ISBN;
import com.example.bookservice.exception.BookException;
import com.example.bookservice.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberFeignClient memberFeignClient;

    @Mock
    private BookMessagePort bookMessagePort;
    
    @InjectMocks
    private BookService bookService;

    private RegisterBookCommand getSampleBook(){
        return new RegisterBookCommand(
            1L,
                1L,
                "test title",
                "test author",
                "test publisher",
                ISBN.of("979-11-963936-5-6"),
                LocalDate.parse("2021-05-01"),
                BookCategory.of(Set.of("PROGRAMMING", "JAVA")),
                BookStatus.AVAILABLE, 
                "서울 도서관 2층 A열" 
            );
    }
    private MemberFeignResponse getMemberInfo(){
        return new MemberFeignResponse(1L,1L,"testId","ADMIN","testName",true);
    }

    @Nested
    class 도서_등록{
        @Test
        void 도서_등록_성공(){
            //given
            RegisterBookCommand bookCommand = getSampleBook();
            Book savedBook = Book.of(bookCommand);
            ReflectionTestUtils.setField(savedBook, "id", 1L);//수동으로 지정.
            MemberFeignResponse mockMember = getMemberInfo();

            when(memberFeignClient.getMemberInfo(1L)).thenReturn(mockMember);
            when(bookRepository.findByIsbn(bookCommand.isbn())).thenReturn(Optional.empty());

            when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
            
            //when
            Long result = bookService.register(bookCommand);
            
            //then
            assertNotNull(result);
            verify(bookRepository).save(any(Book.class));

        }
        @Test
        void 권한이_없으면_에러를_던진다(){
            //given
            RegisterBookCommand bookCommand = getSampleBook();
            MemberFeignResponse mockMember = new MemberFeignResponse(1L,1L,"testId","USER","testName",true);
            when(memberFeignClient.getMemberInfo(1L)).thenReturn(mockMember);
            
            //when&then
            BookException exception = assertThrows(BookException.class, () -> {
                bookService.register(bookCommand);
            });
            assertEquals(ErrorCode.BOOK_ROLE_UNAUTHORIZED.getMessage(), exception.getMessage());
        }
    }

    @Nested
    class 도서_정보_수정{
        @Test
        void 도서_위치_수정_성공(){
            //given
            RegisterBookCommand newBookCommand = getSampleBook();
            UpdateBookCommand updateBookInfoCommand = new UpdateBookCommand(1L, 1L, 1L, 
            BookStatus.AVAILABLE, Set.of("PROGRAMMING", "JAVA"), "서울 도서관 2층 B열" );
            MemberFeignResponse mockMember = getMemberInfo();

            Book savedBook = Book.of(newBookCommand);
            ReflectionTestUtils.setField(savedBook, "id", 1L);

            when(memberFeignClient.getMemberInfo(1L)).thenReturn(mockMember);
            when(bookRepository.findById(1L)).thenReturn(Optional.of(savedBook));

            //when
            bookService.updateBookLocation(updateBookInfoCommand);

            //then
            assertEquals(updateBookInfoCommand.location(), savedBook.getLocation());
            verify(bookRepository).save(savedBook);
        }
        @Test
        void 도서_수정_정보가_null이면_에러를_던진다(){
            //given
            UpdateBookCommand updateBookInfoCommand = new UpdateBookCommand(1L, 1L, 1L, 
            BookStatus.AVAILABLE, Set.of("PROGRAMMING", "JAVA"), null);
            //when&then
            BookException exception = assertThrows(BookException.class,() -> {
                bookService.updateBookLocation(updateBookInfoCommand);
            });
            assertEquals(ErrorCode.BOOK_UPDATE_INFO_NOT_CORRECT.getMessage(), exception.getMessage());
        }
        @Test
        void 도서_변경시시_소속_정보가_다르면_에러를_던진다(){
            //given
            MemberFeignResponse mockMember = getMemberInfo();
            UpdateBookCommand updateBookInfoCommand = new UpdateBookCommand(1L, 2L, 1L, 
            BookStatus.AVAILABLE, Set.of("PROGRAMMING", "JAVA"), null);
            when(memberFeignClient.getMemberInfo(1L)).thenReturn(mockMember);
            //when&then
            BookException exception = assertThrows(BookException.class,() -> {
                bookService.updateBookCategory(updateBookInfoCommand);
            });
            assertEquals(ErrorCode.BOOK_DIFFRENT_ORGANIZATION.getMessage(), exception.getMessage());
        }
    }

}
