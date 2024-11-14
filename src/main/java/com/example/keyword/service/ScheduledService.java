package com.example.keyword.service;

import com.example.keyword.client.CrawlingClient;
import com.example.keyword.dto.CrawlingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final KeywordService keywordService;
    private final CrawlingClient crawlingClient;

    //30분마다 실행
    @Scheduled(cron = "0 0/30 6-23 * * *")
    public void scheduledKeywordExtraction() {
        crawlingClient.crawl();

        wait(10);

        CrawlingResponseDto crawlingResponseDto = crawlingClient.receiveRecentData().getBody();

        wait(10);

        keywordService.extractKeyword(crawlingResponseDto);

        //실행 확인
        System.out.println("실행된 시간:");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        System.out.println(formattedDateTime);
    }

    public void wait(int seconds) {

        try {
            // 10초 대기
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
            e.printStackTrace();
        }
    }
}