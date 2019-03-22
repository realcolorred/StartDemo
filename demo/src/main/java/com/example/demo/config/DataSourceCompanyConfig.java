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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by lenovo on 2019/3/22.
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.dao.sourceCompany", sqlSessionTemplateRef  = "companySqlSessionTemplate")
public class DataSourceCompanyConfig {

    @Bean(name = "companyDataSource")
    @ConfigurationProperties(prefix = "jdbc.company")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "companySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("companyDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/demo/dao/sourceCompany"));
        return bean.getObject();
    }

    @Bean(name = "companyTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("companyDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "companySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("companySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate tmp = new SqlSessionTemplate(sqlSessionFactory);
        tmp.getConfiguration().setMapUnderscoreToCamelCase(true);
        return tmp;
    }

}
