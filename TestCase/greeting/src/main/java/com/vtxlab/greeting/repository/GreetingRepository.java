package com.vtxlab.greeting.repository;

import com.vtxlab.greeting.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Book,Long> {

}
