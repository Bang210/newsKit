package com.example.keyword.client;

import com.example.keyword.dto.CrawlingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "crawling-server")
public interface CrawlingClient {
    //crawling-server와의 통신을 위한 클라이언트

    //crawling Server의 "/sendrecent"에 get요청 전송
    @GetMapping("/sendrecent")
    ResponseEntity<CrawlingResponseDto> receiveRecentData();

    //crawling server의 "/crawl"에 get요청 전송
    @GetMapping("/crawl")
    ResponseEntity<Void> crawl();

    @PostMapping("/crawl/{keyword}")
    ResponseEntity<Void> crawlWithKeyword(@PathVariable("keyword") String keyword);

    @GetMapping("/sendrecent/{keyword}")
    ResponseEntity<CrawlingResponseDto> receiveChildData(@PathVariable("keyword") String keyword);
}
