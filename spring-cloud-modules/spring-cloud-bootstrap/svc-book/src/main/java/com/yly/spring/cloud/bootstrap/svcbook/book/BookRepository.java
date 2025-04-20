package com.yly.spring.cloud.bootstrap.svcbook.book;

import org.springframework.data.jpa.repository.JpaRepository;

interface BookRepository extends JpaRepository<Book, Long>{
}
