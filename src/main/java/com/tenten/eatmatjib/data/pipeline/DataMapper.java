package com.tenten.eatmatjib.data.pipeline;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    Data findDataByMgtno(String mgtno);
    void insertData(Data data);
    void batchUpdateData(List<Data> dataList);
}
