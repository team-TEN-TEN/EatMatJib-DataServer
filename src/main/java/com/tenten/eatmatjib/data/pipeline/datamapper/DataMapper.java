package com.tenten.eatmatjib.data.pipeline.datamapper;

import com.tenten.eatmatjib.data.pipeline.dto.Data;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    public void initData(Data data);

    List<Data> selectRawData();

    void changedColumns();
}
