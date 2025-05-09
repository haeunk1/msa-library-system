package com.example.bookqueryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookqueryservice.domain.BookCatalogRepository;

@SpringBootTest
class BookqueryserviceApplicationTests {
	@MockBean
    private BookCatalogRepository bookCatalogRepository;
	@Test
	void contextLoads() {
	}

}
