package com.example.crawling.controller;

import com.example.crawling.Entity.Crawling;
import com.example.crawling.dto.CrawlingResponseDto;
import com.example.crawling.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/crawl/{keyword}")
    public void createWithKeyword(@PathVariable String keyword) {
        crawlingService.crawlWithKeyword(keyword);
        System.out.println(keyword);
    }

    @GetMapping("/sendrecent/{keyword}")
    public CrawlingResponseDto sendRecentDataWithKeyword(@PathVariable String keyword) {
        Crawling crawling = crawlingService.getRecentDataWithKeyword(keyword);
        return new CrawlingResponseDto(crawling);
    }

}
