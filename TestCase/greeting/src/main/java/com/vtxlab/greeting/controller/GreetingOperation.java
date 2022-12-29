package com.vtxlab.greeting.controller;

import com.vtxlab.greeting.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/default")
public interface GreetingOperation {

  @GetMapping(value = "/greeting")
  String greeting();

  @GetMapping("/books")
  List<Book> findAllBooks();
}
