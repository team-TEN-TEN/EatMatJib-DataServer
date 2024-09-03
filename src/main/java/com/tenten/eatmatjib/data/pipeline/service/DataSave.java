package com.tenten.eatmatjib.data.pipeline.service;

import com.tenten.eatmatjib.data.pipeline.datamapper.DataMapper;
import com.tenten.eatmatjib.data.pipeline.devmapper.EatDevMapper;
import com.tenten.eatmatjib.data.pipeline.dto.RestaurantData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataSave {

    private final EatDevMapper eatDevMapper;
    private final DataMapper dataMapper;
    public void dataSave(List<RestaurantData> restaurantDataList) {
        eatDevMapper.dataSave(restaurantDataList);
        dataMapper.changedColumns();
    }
}
