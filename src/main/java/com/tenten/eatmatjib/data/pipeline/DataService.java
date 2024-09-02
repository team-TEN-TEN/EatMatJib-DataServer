package com.tenten.eatmatjib.data.pipeline;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenten.eatmatjib.data.pipeline.datamapper.DataMapper;
import com.tenten.eatmatjib.data.pipeline.service.DataProcessingService;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class DataService {

    @Value("${OPEN_API_KEY}")
    private String openApiKey;

    private final DataMapper dataMapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final DataProcessingService dataProcessingService;

    @Transactional
    public void initData() {
        try {
            System.out.println("데이터 초기화 시작...");

            // 처음 페이지 요청하여 totalRecords 가져오기
            String initialUrl = buildUrl(1, 1);
            ResponseEntity<String> initialResponseEntity = restTemplate.getForEntity(initialUrl, String.class);
            checkResponse(initialResponseEntity);

            JsonNode initialRootNode = objectMapper.readTree(initialResponseEntity.getBody());

            // "list_total_count" 값을 추출
            int totalRecords = initialRootNode.path("LOCALDATA_072404").path("list_total_count").asInt();

            // 데이터를 배치로 저장하기 위한 리스트 준비
            List<Data> insertDataList = new ArrayList<>();

            System.out.println("초기화 준비 완료");

            // 페이지 단위로 데이터 가져오기
            int pageSize = 1000;
            for (int idx = Constants.START_IDX; idx <= totalRecords; idx += pageSize) {
                String requestUrl = buildUrl(idx, Math.min(idx + pageSize - 1, totalRecords));
                ResponseEntity<String> pageResponseEntity = restTemplate.getForEntity(requestUrl, String.class);
                checkResponse(pageResponseEntity);

                System.out.println("데이터 가져오는 중..." + String.format("%d", idx - Constants.START_IDX)
                    + "/" + String.format("%d", totalRecords - Constants.START_IDX));

                JsonNode pageRootNode = objectMapper.readTree(pageResponseEntity.getBody());
                JsonNode rowNode = pageRootNode.path("LOCALDATA_072404").path("row");

                for (JsonNode node : rowNode) {
                    Data data = objectMapper.treeToValue(node, Data.class);
                    insertDataList.add(data);
                }
            }
            // 배치 삽입 및 업데이트
            if (!insertDataList.isEmpty()) {
                dataMapper.batchInsertData(insertDataList);
            }
            System.out.println("데이터가 저장되었습니다.");
            dataProcessingService.processData();
        } catch (HttpClientErrorException e) {
            // HTTP 클라이언트 예외 처리
            throw new RuntimeException("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            // 일반적인 예외 처리
            throw new RuntimeException("An error occurred while processing the data", e);
        }
    }

    private void checkResponse(ResponseEntity<String> responseEntity) {
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            // 200-299 응답 코드 확인
            return;
        } else {
            throw new RuntimeException("Unexpected HTTP status code: " + statusCode);
        }
    }

    public String buildUrl(int startIdx, int endIdx) throws Exception {
        String urlBuilder =
            "http://openapi.seoul.go.kr:8088" + "/" + URLEncoder.encode(openApiKey, "UTF-8")
                + "/" + URLEncoder.encode("json", "UTF-8")
                + "/" + URLEncoder.encode("LOCALDATA_072404", "UTF-8")
                + "/" + URLEncoder.encode(String.format("%d", startIdx), "UTF-8")
                + "/" + URLEncoder.encode(String.format("%d", endIdx), "UTF-8");

        return urlBuilder;
    }
}
