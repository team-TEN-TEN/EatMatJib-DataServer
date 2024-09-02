package com.tenten.eatmatjib.data.pipeline.datamapper;

import com.tenten.eatmatjib.data.pipeline.dto.Data;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    Data findDataByMgtno(String mgtno);
    void batchInsertData(List<Data> dataList);
    void batchUpdateData(List<Data> dataList);

    List<Data> selectRawData();

    void changedColumns();
}
