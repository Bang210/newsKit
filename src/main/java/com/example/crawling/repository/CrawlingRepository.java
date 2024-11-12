package com.example.crawling.repository;

import com.example.crawling.Entity.Crawling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CrawlingRepository extends JpaRepository<Crawling, Long> {

    @Query(value = "SELECT c FROM Crawling c ORDER BY c.createdTime ASC")
    List<Crawling> findOldestCrawling(@Param("num") long num);
}