package com.example.keyword.repository;

import com.example.keyword.entity.ChildKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildKeywordRepository extends JpaRepository<ChildKeyword, Long> {
}
