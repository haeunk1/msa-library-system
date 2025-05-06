package com.example.bookservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.bookservice.exception.BookException;
public class BookTest {
    private Book getSampleBook(){
        return new Book(
                1L,
                "test title",
                "test author",
                "test publisher",
                "979-11-963936-5-6",
                LocalDate.parse("2021-05-01"),
                BookCategory.of(Set.of("PROGRAMMING", "JAVA")),
                BookStatus.AVAILABLE, 
                "서울 도서관 2층 A열" 
            );
    }
  
    @Test
    void 생성시_카테고리가_없으면_에러를_던진다() {
        assertThrows(BookException.class, ()-> new Book(
            1L,
            "test title",
            "test author",
            "test publisher",
            "979-11-963936-5-6",
            LocalDate.parse("2021-05-01"),
            null,
            BookStatus.AVAILABLE, 
            "서울 도서관 2층 A열" 
        ));
    }

    @Test 
    void ISBN_형식이_맞지_않으면_에러를_던진다(){
        assertThrows(BookException.class, ()-> new Book(
            1L,
            "test title",
            "test author",
            "test publisher",
            "9791196393656123",
            LocalDate.parse("2021-05-01"),
            BookCategory.of(Set.of("PROGRAMMING", "JAVA")),
            BookStatus.AVAILABLE, 
            "서울 도서관 2층 A열" 
        ));
    }

    @Test
    void 카테고리를_여러개_등록하면_해당_카테고리들로_저장된다() {
        Book book = getSampleBook();
        book.updateCategories(BookCategory.of(Set.of("Category1", "Category2")));

        assertTrue(book.getCategories().contains(new BookCategory("Category1")));
        assertTrue(book.getCategories().contains(new BookCategory("Category2")));
    }

    @Test
    void 도서_상태_변경_성공(){
        Book book = getSampleBook();
        book.updateBookStatus(BookStatus.DAMAGED);

        assertEquals(book.getBookStatus(),BookStatus.DAMAGED);
    }
    
}
