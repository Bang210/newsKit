package com.example.crawling.service;

import com.example.crawling.Entity.Crawling;
import com.example.crawling.repository.CrawlingRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService {

    private final CrawlingRepository crawlingRepository;

    //window size
    private final long WINDOW_SIZE = 10;

    //네이버 뉴스스탠드 언론사 id
    List<String> pressIdList = List.of("081", "055", "018", "057", "032", "368", "028", "015", "029", "025",
            "016", "308", "056", "047", "277", "109", "422", "117", "052", "076",
            "214", "139", "314", "215", "366", "003", "030", "038", "044", "020",
            "006", "022", "293", "031", "014", "023", "008", "011", "327", "330",
            "326", "005", "241", "092", "073", "904", "002", "009", "021", "930",
            "079");

    public void crawl() {

        StringBuilder textData = new StringBuilder();

        //rawData 생성
        for (String id: pressIdList) {
            //언론사별 url 설정
            String pressUrl = "https://newsstand.naver.com/include/page/" + id + ".html";

            try {
                //데이터 크롤링
                Document document = Jsoup.connect(pressUrl).get();
                //텍스트 추출
                String text = document.text();
                //스트링빌더에 추가
                textData.append(text).append("\n");
            } catch (IOException e) {
                //예외처리
                e.printStackTrace();
            }
        }

        //Crawling 객체 생성
        Crawling crawling = Crawling.builder()
                .rawData(textData.toString())
                .createdTime(LocalDateTime.now())
                .build();

        //Repository에 저장
        crawlingRepository.save(crawling);

        //저장된 Crawling 데이터의 개수가 너무 많을 경우 오래된 것부터 삭제
        long dataCount = crawlingRepository.count();

        if (dataCount > WINDOW_SIZE) {
            //삭제할 데이터를 리스트로 불러옴
            long excessNum = dataCount - WINDOW_SIZE;
            List<Crawling> oldCrawlingList = crawlingRepository.findOldestCrawling(excessNum);

            //삭제
            crawlingRepository.deleteAll(oldCrawlingList);
        }
    }

    public Crawling getRecentData() {
        Crawling crawling = crawlingRepository.findFirstByOrderByCreatedTimeDesc();
        return crawling;
    }
}
