package com.vtxlab.greeting.service;

import com.vtxlab.greeting.entity.Book;

import java.util.List;

public interface GreetingService {
  
  String greeting();

  List<Book> findAllBook();
}
