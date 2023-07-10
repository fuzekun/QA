package com.fuzekun.demo1.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

/**
 * @author: Zekun Fu
 * @date: 2022/9/18 19:19
 * @Description: 配置牛客社区的数据源
 *
 *
 */
//@Configuration
//@MapperScan(basePackages = {"com.fuzekun.demo1.mapper.community"}, sqlSessionFactoryRef = "CSF")
@Configuration
@MapperScan(basePackages = {"com.fuzekun.demo1.mapper.community"}, sqlSessionFactoryRef = "communitySqlSessionFactory")
public class CommunityDataSourceConf {

//    @Bean(name = "communityDataSource")
//    @ConfigurationProperties(prefix = "spring.community-datasource")
//    public DataSource cmmntyDS() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "communitySqlSessionFactory")
//    public SqlSessionFactory secondFactory(@Qualifier("communityDataSource") DataSource source) throws Exception{
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(source);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/community/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "communityTransactionManager")
//    public DataSourceTransactionManager senconManager(@Qualifier("communityDataSource") DataSource source) {
//        return new DataSourceTransactionManager(source);
//    }
//
//    @Bean(name = "communitySqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("communitySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
    @Bean(name = "communityDataSource")
    @ConfigurationProperties(prefix = "spring.community-datasource")
    public DataSource secondDataSource() {
    return DataSourceBuilder.create().build();
}

    @Bean(name = "communitySqlSessionFactory")
    public SqlSessionFactory secondFactory(@Qualifier("communityDataSource") DataSource source) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        // 从这里配置mybatis的信息, 需要先进行setDataSource
        bean.setDataSource(source);
        // 更换了idea之后，就报错了，不知道为什么
//        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/community/*.xml"));
        bean.setTypeAliasesPackage("com.fuzekun.demo1.entity.community");
        return bean.getObject();
    }

    @Bean(name = "communityTransactionManager")
    public DataSourceTransactionManager senconManager(@Qualifier("communityDataSource") DataSource source) {
        return new DataSourceTransactionManager(source);
    }

    @Bean(name = "CSST")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("communitySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
