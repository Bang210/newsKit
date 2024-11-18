package com.example.keyword.service;

import com.example.keyword.dto.CrawlingResponseDto;
import com.example.keyword.entity.Keyword;
import com.example.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.stereotype.Service;
import scala.collection.Seq;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public void extractKeyword(CrawlingResponseDto crawlingResponseDto) {

        String rawData = crawlingResponseDto.getRawData();
        long crawlingId = crawlingResponseDto.getId();

        //rawData에서 어구 추출
        CharSequence normalizedData = OpenKoreanTextProcessorJava.normalize(rawData);

        //추출된 어구 토큰화
        Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalizedData);

        //명사만 추출하기 위한 세팅
        List<String> nouns = new ArrayList<>();
        scala.collection.Iterator<KoreanTokenizer.KoreanToken> iterator = tokens.iterator();
        while(iterator.hasNext()) {
            KoreanTokenizer.KoreanToken token = iterator.next();
            if(token.pos().toString().equals("Noun")) {
                nouns.add(token.text());
            }
        }

        //의미없는 키워드 제외를 위한 목록
        //추후 업데이트
        Set<String> meaninglessWords = new HashSet<>(Arrays.asList(
                "뉴스", "확성기", "뉴스스탠드", "정부", "한국", "오늘", "단독", "공개", "방송", "대응", "명령",
                "논란", "재개", "영역", "집단", "신고", "사건", "경제", "서울", "최고", "중앙",
                "이번", "추가", "사망", "세계", "상임", "이유", "검토", "위반", "경찰", "효과",
                "우리", "가능성", "투자","올해","불법","영상","이상","국민", "대표", "선출", "의혹",
                "구성", "전환", "글로벌", "고소", "검찰", "사실", "현장", "혐의", "속보", "연장", "내년",
                "언론","판결","입장","발표","직접", "대통령", "충격", "수사", "가족", "향년", "발견", "폭행",
                "수사", "벌금", "통과", "국회", "선고", "확정", "여사", "조사", "추천", "금융", "회장",
                "의원", "작년", "사진", "구속", "조카", "부인", "거부", "처리", "책임", "문제", "유죄", "귀국",
                "여성", "남성", "포토", "살해", "법원", "종합", "해결", "이슈"));

        Pattern hangulPattern = Pattern.compile("^[가-힣]+$");

        //한 글자이거나 의미없는 단어 필터링, 명사만 추출
        List<String> filteredNouns = nouns.stream()
                .filter(word -> (word.length() > 1 || !hangulPattern.matcher(word).matches()) && !meaninglessWords.contains(word))
                .toList();

        //언급 빈도 계산
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String noun: filteredNouns) {
            frequencyMap.put(noun, frequencyMap.getOrDefault(noun, 0) + 1);
        }

        //빈도수 상위 20개 키워드 추출
        List<String> topKeywords = frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(20)
                .map(Map.Entry::getKey)
                .toList();

        //추출된 키워드 확인
        System.out.println("키워드 확인");
        topKeywords.forEach(System.out::println);

        create(crawlingId, topKeywords);
    }

    public void create(long crawlingId, List<String> topKeywords) {

        Keyword keyword = new Keyword().toBuilder()
                .crawlingId(crawlingId)
                .keywordList(topKeywords)
                .createdTime(LocalDateTime.now())
                .build();

        keywordRepository.save(keyword);
    }

    public List<String> showRecentTopKeywords() {
        Keyword keyword = keywordRepository.findFirstByOrderByCreatedTimeDesc();
        List<String> recentTopKeywords = keyword.getKeywordList();
        return recentTopKeywords;
    }

    public Keyword getRecentData() {
        try {
            return keywordRepository.findFirstByOrderByCreatedTimeDesc();
        } catch (Exception e) {
            return null;
        }
    }
}
