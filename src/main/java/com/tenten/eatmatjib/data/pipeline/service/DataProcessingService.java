package com.tenten.eatmatjib.data.pipeline.service;

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
        List<Data> dataList = dataMapper.selectRawData();
        List<RestaurantData> restaurantDataList = new ArrayList<>();
        for (Data data : dataList) {
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
            restaurantData.setUpdatedAt(LocalDateTime.now());

            restaurantDataList.add(restaurantData);
        }
        dataSave.dataSave(restaurantDataList);
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
