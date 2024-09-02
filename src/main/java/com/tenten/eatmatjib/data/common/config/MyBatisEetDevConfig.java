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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.tenten.eatmatjib.data.pipeline.devmapper", sqlSessionFactoryRef = "EetDevSqlSessionFactory")
public class MyBatisEetDevConfig {

    @Bean(name = "EetDevSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("eatDevDataSource") DataSource dataSource,
        ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/dev/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("EetDevSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("EetDevSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
