package com.example.crawling.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class Crawling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(length = 50000)
    private String rawData;

    @CreatedDate
    @Column
    private LocalDateTime createdTime;
}
