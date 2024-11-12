package com.example.crawling.controller;

import com.example.crawling.Entity.Crawling;
import com.example.crawling.dto.CrawlingResponseDto;
import com.example.crawling.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;

    @GetMapping("/crawl")
    public void create() {
        crawlingService.crawl();
    }

    @GetMapping("/sendrecent")
    public CrawlingResponseDto sendRecentData() {
        Crawling crawling = crawlingService.getRecentData();
        return new CrawlingResponseDto(crawling);
    }

}
