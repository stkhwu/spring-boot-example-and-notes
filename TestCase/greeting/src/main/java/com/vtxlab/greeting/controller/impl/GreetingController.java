package com.vtxlab.greeting.controller.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vtxlab.greeting.controller.GreetingOperation;
import com.vtxlab.greeting.entity.Book;
import com.vtxlab.greeting.service.GreetingService;
import lombok.AllArgsConstructor;


@RestController // create bean to spring context during start up
// @Component // @Controller or @Service or @Repository or @Configuration
@RequestMapping("/api/v1")
@AllArgsConstructor
public class GreetingController implements GreetingOperation {

  @Autowired
  GreetingService greetingService;

  @Override
  public String greeting() {
    return greetingService.greeting() + "d"; // mock
  }

  @Override
  public List<Book> findAllBooks() {
    return greetingService.findAllBook();
  }
}
