package com.example.keyword.controller;

import com.example.keyword.client.CrawlingClient;
import com.example.keyword.dto.CrawlingResponseDto;
import com.example.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final CrawlingClient crawlingClient;
    private final KeywordService keywordService;

    @GetMapping("/receiverecent")
    public void receiveRecentData() {
        CrawlingResponseDto crawlingResponseDto = crawlingClient.receiveRecentData().getBody();
        keywordService.extractKeyword(crawlingResponseDto);
    }
}
