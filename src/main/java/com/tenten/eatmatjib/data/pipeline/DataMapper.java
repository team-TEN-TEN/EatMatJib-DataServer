package com.tenten.eatmatjib.data.pipeline;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    public void initData(Data data);
}
