package com.example.keyword.client;

import com.example.keyword.dto.CrawlingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "crawling-server")
public interface CrawlingClient {
    //crawling-server와의 통신을 위한 클라이언트

    //crawling Server의 "/sendrecent"에 get요청 전송
    @GetMapping("/sendrecent")
    ResponseEntity<CrawlingResponseDto> receiveRecentData();

    //crawling server의 "/crawl"에 get요청 전송
    @GetMapping("/crawl")
    ResponseEntity<Void> crawl();
}
