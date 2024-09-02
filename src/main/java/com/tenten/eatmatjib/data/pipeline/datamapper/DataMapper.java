package com.tenten.eatmatjib.data.pipeline.datamapper;

import com.tenten.eatmatjib.data.pipeline.dto.Data;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    com.tenten.eatmatjib.data.pipeline.Data findDataByMgtno(String mgtno);
    void batchInsertData(List<com.tenten.eatmatjib.data.pipeline.Data> dataList);
    void batchUpdateData(List<com.tenten.eatmatjib.data.pipeline.Data> dataList);

    List<Data> selectRawData();

    void changedColumns();
}
