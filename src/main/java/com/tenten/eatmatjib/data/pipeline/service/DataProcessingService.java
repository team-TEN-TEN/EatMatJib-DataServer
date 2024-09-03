package com.tenten.eatmatjib.data.pipeline.service;

import com.tenten.eatmatjib.data.pipeline.Constants;
import com.tenten.eatmatjib.data.pipeline.Cuisine;
import com.tenten.eatmatjib.data.pipeline.datamapper.DataMapper;
import com.tenten.eatmatjib.data.pipeline.dto.Data;
import com.tenten.eatmatjib.data.pipeline.dto.RestaurantData;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataProcessingService {

    private final DataMapper dataMapper;
    private final DataSave dataSave;

    public void processData() {
        System.out.println("데이터 전처리 시작...");

        List<Data> dataList = dataMapper.selectRawData();
        List<RestaurantData> restaurantDataList = new ArrayList<>();
        int dataSize = dataList.size();

        for (int idx = 0; idx < dataSize; idx++) {
            Data data = dataList.get(idx);

            if (data.getBplcnm() == null || data.getBplcnm().trim().isEmpty()) {
                continue;
            }

            String dtlstatenm = data.getDtlstatenm().trim();
            if ("폐업".equals(dtlstatenm)) {
                Data existingData = dataMapper.findDataByMgtno(data.getMgtno());

                if (existingData == null) {
                    continue;
                }
                // 폐업한 가게 정보 삭제하는 로직 작성 필요
            }

            RestaurantData restaurantData = new RestaurantData();

            try {
                BigDecimal x = new BigDecimal(data.getX());
                BigDecimal y = new BigDecimal(data.getY());
                restaurantData.setX(x);
                restaurantData.setY(y);
            } catch (NumberFormatException e) {
                continue; // 데이터 제외
            }

            restaurantData.setName(data.getBplcnm());

            if (data.getSitewhladdr() == null && data.getRdnwhladdr() == null) {
                continue;
            }

            if (data.getRdnpostno() != null && !data.getRdnpostno().trim().isEmpty()) {
                restaurantData.setZipCode(data.getRdnpostno());
                restaurantData.setAddress(data.getRdnwhladdr());
            } else if (data.getSitepostno() != null && !data.getSitepostno().trim().isEmpty()) {
                restaurantData.setZipCode(data.getSitepostno());
                restaurantData.setAddress(data.getSitewhladdr());
            }

            String uptaenm = data.getUptaenm();
            Cuisine cuisine = Cuisine.koreanName(uptaenm);
            restaurantData.setCuisine(cuisine.getName());

            if (data.getSitetel() == null || data.getSitetel().isEmpty() || data.getSitetel()
                .startsWith("050")) {
                restaurantData.setPhoneNumber("");
            } else {
                restaurantData.setPhoneNumber(normalizePhoneNumber(data.getSitetel()));
            }

            if (data.getHomepage() == null) {
                restaurantData.setHomepageUrl("");
            } else {
                restaurantData.setHomepageUrl(data.getHomepage());
            }
            restaurantData.setAvgScore(BigDecimal.valueOf(0));
            restaurantData.setViewCount(0);
            restaurantData.setUpdatedAt(LocalDateTime.now());

            restaurantDataList.add(restaurantData);

            if (idx % 1000 == 0) {
                System.out.println("데이터 처리 중..." + String.format("%d", idx)
                    + "/" + String.format("%d", dataSize));
            }
        }
        if (restaurantDataList.size() > 0) {
            dataSave.dataSave(restaurantDataList);
            System.out.println("데이터 전처리 및 저장을 완료했습니다. 저장된 데이터: " + restaurantDataList.size());
        } else {
            System.out.println("데이터 전처리 및 저장을 완료했습니다. 저장된 데이터: " + 0);
        }

    }

    private String normalizePhoneNumber(String phoneNumber) {
        String normalized = phoneNumber.replaceAll("\\s+", "");
        if (normalized.matches("^\\d{2,3}\\d{3,4}\\d{4}$")) {
            return normalized;
        } else {
            return "";
        }
    }

}
