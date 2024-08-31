package com.tenten.eatmatjib.data.pipeline;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
public class DataPipelineSchedulerTest {

    @Autowired
    private DataPipelineScheduler scheduler;

    @Test
    public void testGetRawData() throws Exception {
        scheduler.getRawData();
    }
}
