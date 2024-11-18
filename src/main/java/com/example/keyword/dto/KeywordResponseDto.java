package com.example.keyword.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class KeywordResponseDto {

    private List<String> keywordList;
    private LocalDateTime createdTime;
}
