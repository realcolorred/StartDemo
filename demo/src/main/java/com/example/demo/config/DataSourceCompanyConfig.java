package com.example.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by lenovo on 2019/3/22.
 * 建立company-pc数据库的连接
 */
@Configuration
@MapperScan(basePackages = { "com.example.demo.dao.sourceCompany" },     // mybatis-basePackages : 包扫描
    sqlSessionTemplateRef = "companySqlSessionTemplate")             // mybatis-sqlSessionTemplateRef : 引用的sql会话模板
public class DataSourceCompanyConfig {

    /**
     * 设置一个数据源
     *
     * @return 数据源
     */
    @Bean(name = "companyDataSource")
    @ConfigurationProperties(prefix = "jdbc.company")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 事务管理
     *
     * @param dataSource 数据源
     * @return 事务
     */
    @Bean(name = "companyTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("companyDataSource") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }

    /**
     * 设置ibatis-sql会话工厂
     *
     * @param dataSource 数据源
     * @return sql会话工厂
     * @throws Exception
     */
    @Bean(name = "companySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("companyDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 设置ibatis的扫描包位置 (注解已经设置了,此处不再设置)
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/demo/dao/sourceCompany"));
        return bean.getObject();
    }

    /**
     * 设置mybatis-sql会话模板
     *
     * @param sqlSessionFactory sql会话工厂
     * @return sql会话模板
     * @throws Exception
     */
    @Bean(name = "companySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("companySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate tmp = new SqlSessionTemplate(sqlSessionFactory);
        tmp.getConfiguration().setMapUnderscoreToCamelCase(true);
        return tmp;
    }

}
