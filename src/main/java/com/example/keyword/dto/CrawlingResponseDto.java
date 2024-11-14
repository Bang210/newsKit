package com.example.keyword.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder(toBuilder = true)
public class CrawlingResponseDto {

    //crawling 서버와의 통신을 위해 동일한 dto 생성
    private String rawData;
    private LocalDateTime createdTime;
    private long id;
}
