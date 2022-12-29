package com.vtxlab.greeting.service.impl;

import com.vtxlab.greeting.entity.Book;
import com.vtxlab.greeting.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vtxlab.greeting.service.GreetingService;

import java.util.List;

@Service // create the bean to spring context during start up
public class GreetingServiceHolder implements GreetingService {

  @Autowired
  GreetingRepository greetingRepository;

  @Override
  public String greeting() {
    return "Hello World!";
  }

  @Override
  public List<Book> findAllBook() {
    return greetingRepository.findAll();
  }


}
