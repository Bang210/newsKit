package com.example.keyword.controller;

import com.example.global.response.GlobalResponse;
import com.example.keyword.client.CrawlingClient;
import com.example.keyword.dto.CrawlingResponseDto;
import com.example.keyword.dto.KeywordResponseDto;
import com.example.keyword.entity.Keyword;
import com.example.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final CrawlingClient crawlingClient;
    private final KeywordService keywordService;

    @GetMapping("/requestRecent")
    public void receiveRecentData() {
        CrawlingResponseDto crawlingResponseDto = crawlingClient.receiveRecentData().getBody();
        keywordService.extractKeyword(crawlingResponseDto);
    }

    @GetMapping("/showRecent")
    public List<String> showRecent() {
        return keywordService.showRecentTopKeywords();
    }

    @PostMapping("/dataRequest")
    public GlobalResponse response() {
        Keyword keyword = keywordService.getRecentData();
        if (keyword != null) {
            KeywordResponseDto keywordResponseDto = new KeywordResponseDto().toBuilder()
                    .keywordList(keyword.getKeywordList())
                    .createdTime(keyword.getCreatedTime())
                    .build();
            System.out.println("data sent");
            return GlobalResponse.of("200", "response success", keywordResponseDto);
        }
        else {
            System.out.println("failed");
            return GlobalResponse.of("404", "data not found");
        }
    }
}