package com.vtxlab.greeting;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.greeting.entity.Book;
import com.vtxlab.greeting.repository.GreetingRepository;
import com.vtxlab.greeting.service.GreetingService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@WebMvcTest // @Controller, @Configuration // have bean no service
// Start spring context, but with Controller related bean ONLY
public class GreetingControllerIntegrationTest {


    @MockBean // Create a new bean to spring context
    GreetingService greetingService;

    @MockBean // Create a new bean to spring context
    GreetingRepository greetingRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    // Due to @WebMvcTest, the mockMvc Bean has been loaded to context
    MockMvc mockMvc; // just like postman for testing

    @Test
    void testWebMvc() throws Exception {
        // Given
        Mockito.when(greetingService.greeting()).thenReturn("Hello World");
        // When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/greeting");
        // Execute the call
        ResultActions response = mockMvc.perform(builder);
        // check if the http response is 200
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ResultMatcher statusOk = status.isOk(); // 200
        response.andExpect(statusOk);


        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher contentHelloworld = content.string("Hello Worldd");
        response.andExpect(contentHelloworld);
    }

    @Test
        // with static import
    void testWebMvc2() throws Exception {
        when(greetingService.greeting()).thenReturn("Hello World");
        mockMvc.perform(get("/api/v1/greeting")) //
                .andExpect(status().isOk()) //
                .andExpect(content().string("Hello Worldd"));
    }

    @Test
    void testFindAllBooks() throws Exception {
        Book book = new Book(1L, "Tommy's Book", LocalDate.of(2022, Month.JUNE, 22));
        List<Book> books = new ArrayList<>();
        books.add(book);

        // when(greetingRepository.findAll()).thenReturn(books);
        when(greetingService.findAllBook()).thenReturn(books);
        MvcResult result = mockMvc.perform(get("/api/v1/books"))
                .andDo(print()) //
                .andExpect(status().isOk()) //
                .andReturn();
        // .andExpect(jsonPath("$.length()").value(1));
        String string = result.getResponse().getContentAsString();
        log.info(string);

        // ObjectMapper objectMapper = new ObjectMapper();

        // Deserialization
        List<Book> bookList = objectMapper.readValue(string, List.class);
        log.info("bookList.size()={}", bookList.size());
    }

}
