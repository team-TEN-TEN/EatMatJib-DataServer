package com.tenten.eatmatjib.data.pipeline.devmapper;

import com.tenten.eatmatjib.data.pipeline.dto.RestaurantData;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EatDevMapper {

    int dataSaveUpdate(List<RestaurantData> data);

    void dataSave(List<RestaurantData> data);
}
