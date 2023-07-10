package com.fuzekun.demo1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 13:32
 * @Description:
 *  配置双数据源
 */
//@Configuration
public class JdbcDataSourceConf {

//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource firstDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "secondDataSource")
//    @ConfigurationProperties(prefix = "spring.second-datasource")
//    public DataSource secondDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @Primary
//    public JdbcTemplate primaryTemplate(DataSource source) {
//        return new JdbcTemplate(source);
//    }
//
//    @Bean(name = "seondTemplate")
//    public JdbcTemplate secondTemplate(@Qualifier("secondDataSource") DataSource source) {
//        return new JdbcTemplate(source);
//    }


}
