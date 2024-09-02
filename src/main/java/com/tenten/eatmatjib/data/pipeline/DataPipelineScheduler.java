package com.tenten.eatmatjib.data.pipeline;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class DataPipelineScheduler {

    @Value("${OPEN_API_KEY}")
    private String openApiKey;

    private final DataMapper dataMapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    @Scheduled(cron = "0 0 2 * * ?")
    public void updateData() {
        try {
            // 처음 페이지 요청하여 totalRecords 가져오기
            String initialUrl = buildUrl(1, 1);
            ResponseEntity<String> initialResponseEntity = restTemplate.getForEntity(initialUrl, String.class);
            checkResponse(initialResponseEntity);

            JsonNode initialRootNode = objectMapper.readTree(initialResponseEntity.getBody());

            // "list_total_count" 값을 추출
            int totalRecords = initialRootNode.path("LOCALDATA_072404").path("list_total_count").asInt();

            // 데이터를 배치로 저장하기 위한 리스트 준비
            List<Data> insertDataList = new ArrayList<>();
            List<Data> updateDataList = new ArrayList<>();

            // 데이터 페이지 단위로 가져오기
            int pageSize = 1000;
            for (int idx = Constants.START_IDX; idx <= totalRecords; idx += pageSize) {
                String requestUrl = buildUrl(idx, Math.min(idx + pageSize - 1, totalRecords));
                ResponseEntity<String> pageResponseEntity = restTemplate.getForEntity(requestUrl, String.class);
                checkResponse(pageResponseEntity);

                JsonNode pageRootNode = objectMapper.readTree(pageResponseEntity.getBody());
                JsonNode rowNode = pageRootNode.path("LOCALDATA_072404").path("row");

                processRows(rowNode, insertDataList, updateDataList);
            }
            // 배치 삽입 및 업데이트
            if (!insertDataList.isEmpty()) {
                dataMapper.batchInsertData(insertDataList);
            }

            if (!updateDataList.isEmpty()) {
                dataMapper.batchUpdateData(updateDataList);
            }
            System.out.println("데이터가 갱신되었습니다.");

        } catch (HttpClientErrorException e) {
            // HTTP 클라이언트 예외 처리
            throw new RuntimeException("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            // 일반적인 예외 처리
            throw new RuntimeException("An error occurred while processing the data", e);
        }
    }

    private void processRows(JsonNode rows, List<Data> insertDataList, List<Data> updateDataList) throws IOException {
        for (JsonNode node : rows) {
            Data data = objectMapper.treeToValue(node, Data.class);

            // 데이터베이스에서 해당 데이터가 존재하는지 확인
            Data existingData = dataMapper.findDataByMgtno(data.getMgtno());

            if (existingData == null) {
                // 데이터가 없고 새로 들어온 데이터면 삽입 목록에 추가
                if ("I".equals(data.getUpdategbn())) {
                    insertDataList.add(data);
                }
            } else {
                // 데이터가 있고, 업데이트 필요하면 업데이트 목록에 추가
                if ("U".equals(data.getUpdategbn()) && isOutdated(data, existingData)) {
                    data.setIsUpdated(1);
                    updateDataList.add(data);
                }
            }
        }
    }
    private boolean isOutdated(Data newData, Data existingData) {
        // 새 데이터의 최종 수정일자가 기존 데이터보다 나중이면 true 반환
        return newData.getLastmodts().compareTo(existingData.getLastmodts()) > 0;
    }

    private void checkResponse(ResponseEntity<String> responseEntity) {
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (!statusCode.is2xxSuccessful()) {
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
