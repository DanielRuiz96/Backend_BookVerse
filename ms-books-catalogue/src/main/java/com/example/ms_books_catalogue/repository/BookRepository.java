package com.example.ms_books_catalogue.repository;

import com.example.ms_books_catalogue.entity.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository
        extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
