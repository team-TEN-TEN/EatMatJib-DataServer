//package com.tenten.eatmatjib.data.pipeline;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.IOException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class DataPipelineSchedulerTest {
//    @Mock
//    private DataMapper dataMapper;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private DataPipelineScheduler dataPipelineScheduler;
//
//    @Value("OPEN_API_KEY")
//    private String openApiKey;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        restTemplate = new RestTemplate();
//    }
//
//    @Test
//    public void testUpdateData() throws IOException {
//        // 준비: Mocking the responses
//        String mockResponse = "{ \"LOCALDATA_072404\": { \"list_total_count\": 2, \"row\": [ { \"MGTNO\": \"1\", \"LASTMODTS\": \"2023-01-01\", \"UPDATEGBN\": \"I\" }, { \"MGTNO\": \"2\", \"LASTMODTS\": \"2023-01-02\", \"UPDATEGBN\": \"U\" } ] } }";
//
//        when(restTemplate.getForEntity(anyString(), eq(String.class)))
//            .thenReturn(ResponseEntity.ok(mockResponse));
//
//        JsonNode mockJsonNode = objectMapper.readTree(mockResponse);
//        when(objectMapper.readTree(anyString())).thenReturn(mockJsonNode);
//
////        when(dataMapper.findDataByMgtno("2")).thenReturn(new Data("2", "2023-01-01", "U"));
//
//        dataPipelineScheduler.updateData();
//
//        // then
//        verify(dataMapper, times(1)).batchInsertData(anyList());
//        verify(dataMapper, times(1)).batchUpdateData(anyList());
//    }
//}
