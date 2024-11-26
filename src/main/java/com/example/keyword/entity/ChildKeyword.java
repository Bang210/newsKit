package com.example.keyword.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder(toBuilder = true)
public class ChildKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String motherKeyword;

    @Column
    @ElementCollection
    private List<String> childKeywordList;

    @CreatedDate
    @Column
    private LocalDateTime createdTime;

}