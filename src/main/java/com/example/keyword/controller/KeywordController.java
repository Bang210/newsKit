package com.example.keyword.controller;

import com.example.global.response.GlobalResponse;
import com.example.keyword.client.CrawlingClient;
import com.example.keyword.dto.CrawlingResponseDto;
import com.example.keyword.dto.KeywordResponseDto;
import com.example.keyword.entity.ChildKeyword;
import com.example.keyword.entity.Keyword;
import com.example.keyword.service.KeywordService;
import com.example.keyword.service.ScheduledService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final CrawlingClient crawlingClient;
    private final KeywordService keywordService;
    private final ScheduledService scheduledService;

    //부모 키워드 생성
    @GetMapping("/requestRecent")
    public void receiveRecentData() {
        CrawlingResponseDto crawlingResponseDto = crawlingClient.receiveRecentData().getBody();
        keywordService.extractKeyword(crawlingResponseDto);
    }

    @GetMapping("/showRecent")
    public List<String> showRecent() {
        return keywordService.showRecentTopKeywords();
    }

    //클라이언트 앱으로 부모키워드 전송
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

    //클라이언트 앱으로 자식키워드 전송
    @PostMapping("/dataRequest/{parentKeyword}")
    public GlobalResponse responseFromKeyword(@PathVariable String parentKeyword) {
        ChildKeyword childKeyword = keywordService.getRecentChildKeywords(parentKeyword);
        if (childKeyword != null) {
            KeywordResponseDto keywordResponseDto = new KeywordResponseDto().toBuilder()
                    .keywordList(childKeyword.getChildKeywordList())
                    .createdTime(childKeyword.getCreatedTime())
                    .build();
            return GlobalResponse.of("200", "response success", keywordResponseDto);
        } else {
            return GlobalResponse.of("404", "data not found");
        }
    }

    @GetMapping("/manual")
    public void manual() {
        scheduledService.scheduledKeywordExtraction();
    }
}