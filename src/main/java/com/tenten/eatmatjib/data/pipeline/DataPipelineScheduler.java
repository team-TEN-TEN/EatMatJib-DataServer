package com.tenten.eatmatjib.data.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataPipelineScheduler {

    @Value("OPEN_API_KEY")
    private String openApiKey;

    @Scheduled(cron = "0 0 2 * * ?")
    public void getRawData() throws IOException {
        StringBuffer urlBuilder = new StringBuffer("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode(openApiKey, "UTF-8"));
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("LOCALDATA_072404","UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("5","UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int resCode = con.getResponseCode();
        String resMessage = con.getResponseMessage();
        BufferedReader br;

        if (resCode >= 200 && resCode <= 300) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        con.disconnect();

        String resData = sb.toString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> returnMap = mapper.readValue(resData, Map.class);




    }

}
