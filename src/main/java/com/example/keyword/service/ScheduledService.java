package com.example.keyword.service;

import com.example.keyword.client.CrawlingClient;
import com.example.keyword.dto.CrawlingResponseDto;
import com.example.keyword.entity.Keyword;
import com.example.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final KeywordService keywordService;
    private final KeywordRepository keywordRepository;
    private final CrawlingClient crawlingClient;

    //30분마다 실행
    @Transactional
    @Scheduled(cron = "0 0/30 6-23 * * *")
    public void scheduledKeywordExtraction() {

        //부모키워드 생성
        crawlingClient.crawl();

        waitForSeconds(1);

        CrawlingResponseDto crawlingResponseDto = crawlingClient.receiveRecentData().getBody();


        keywordService.extractKeyword(crawlingResponseDto);


        //자식키워드 생성
        Keyword keyword = keywordService.getRecentData();

        List<String> keywordList = keyword.getKeywordList();

        for (String parentKeyword : keywordList) {
            crawlingClient.crawlWithKeyword(parentKeyword);

            waitForSeconds(2);

            CrawlingResponseDto childCrawlingDto = crawlingClient.receiveChildData(parentKeyword).getBody();
            keywordService.extractChildKeyword(childCrawlingDto, parentKeyword);
        }

        //실행 확인
        System.out.println("실행된 시간:");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        System.out.println(formattedDateTime);
    }

    public void waitForSeconds(int seconds) {

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
            e.printStackTrace();
        }
    }
}