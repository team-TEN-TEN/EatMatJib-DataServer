package com.tenten.eatmatjib.data.common.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.tenten.eatmatjib.data.pipeline.datamapper", sqlSessionFactoryRef = "EetDataSqlSessionFactory")
public class MyBatisEetDataConfig {

    @Primary
    @Bean(name = "EetDataSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
        @Qualifier("eatDataDataSource") DataSource dataSource,
        ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/data/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "EetDataSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("EetDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
