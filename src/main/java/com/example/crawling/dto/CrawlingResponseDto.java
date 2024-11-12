package com.example.crawling.dto;

import com.example.crawling.Entity.Crawling;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CrawlingResponseDto {

    private long id;
    private String rawData;
    private LocalDateTime createdTime;

    public CrawlingResponseDto (Crawling crawling) {
        this.id = crawling.getId();
        this. rawData = crawling.getRawData();
        this.createdTime = crawling.getCreatedTime();
    }
}
