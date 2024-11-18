package com.example.keyword.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CrawlingResponseDto {

    //crawling 서버와의 통신을 위해 동일한 dto 생성
    private long id;
    private String rawData;
    private LocalDateTime createdTime;

}
