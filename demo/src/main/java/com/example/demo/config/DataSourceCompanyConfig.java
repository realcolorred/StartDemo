package com.example.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019/3/22.
 * 建立company-pc数据库的连接(学习用,配置中有大量设置默认值的操作)
 *
 * 1.设置dataSouce
 * 2 设置事务管理
 * 3 设置会话管理
 */
@Configuration
@MapperScan(basePackages = { "com.example.demo.dao.sourceCompany" },     // mybatis-basePackages : 包扫描
    sqlSessionTemplateRef = "companySqlSessionTemplate")             // mybatis-sqlSessionTemplateRef : 引用的sql会话模板
public class DataSourceCompanyConfig {

    /**
     * 设置一个数据源
     * @return 数据源
     */
    @Bean(name = "companyDataSource")
    @ConfigurationProperties(prefix = "jdbc.company")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 事务管理
     * @param dataSource 数据源
     * @return 事务
     */
    @Bean(name = "companyTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("companyDataSource") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        dataSourceTransactionManager.setNestedTransactionAllowed(false); // 默认值:false, 不允许开启嵌套事务
        dataSourceTransactionManager.setRollbackOnCommitFailure(false); // 默认值:false, 不允许在commit失败的时候回滚事务(正常情况下commit不会失败,失败一般发生在批量操作时网络阻塞)
        return dataSourceTransactionManager;
    }

    @Bean(name = "companyTransactionInterceptor")
    public TransactionInterceptor txAdvice(@Qualifier("companyTransactionManager") DataSourceTransactionManager manager) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(50000);

        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        source.setNameMap(txMap);
        TransactionInterceptor txAdvice = new TransactionInterceptor(manager, source);
        return txAdvice;
    }

    @Bean(name = "companyAdvisor")
    public Advisor txAdviceAdvisor(@Qualifier("companyTransactionInterceptor") TransactionInterceptor txAdvice) {
        // 切面的定义,pointcut及advice
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.arrow.shop.service.TestService.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }

    /**
     * 会话管理
     * @param dataSource 数据源
     * @return sql会话工厂
     * @throws Exception
     */
    @Bean(name = "companySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("companyDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/demo/dao/sourceCompany")); // 设置ibatis的扫描包位置(类头上注解已经设置了,此处不再设置)
        return bean.getObject();
    }

    @Bean(name = "companySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("companySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate tmp = new SqlSessionTemplate(sqlSessionFactory);
        tmp.getConfiguration().setMapUnderscoreToCamelCase(true); // 开启自动驼峰命名转换
        return tmp;
    }
}
