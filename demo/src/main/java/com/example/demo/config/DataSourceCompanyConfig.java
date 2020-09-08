package com.example.demo.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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
 * <p>
 * 1.设置dataSouce
 * 2 设置事务管理
 * 3 设置会话管理
 *
 * MapperScan
 * mybatis-basePackages : 包扫描
 * mybatis-sqlSessionTemplateRef : 引用的sql会话模板
 */
@Configuration
@MapperScan(basePackages = { "com.example.demo.dao.sourceCompany" }, sqlSessionTemplateRef = "companySqlSessionTemplate")
public class DataSourceCompanyConfig {

    /**
     * 设置一个数据源
     *
     * @return 数据源
     */
    @Bean(name = "companyDataSource")
    @ConfigurationProperties(prefix = "jdbc.company")
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
    public DataSourceTransactionManager transactionManager(@Qualifier("companyDataSource") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        // 默认值:false, 不允许开启嵌套事务
        dataSourceTransactionManager.setNestedTransactionAllowed(false);
        // 默认值:false, 不允许在commit失败的时候回滚事务(正常情况下commit不会失败,失败一般发生在批量操作时网络阻塞)
        dataSourceTransactionManager.setRollbackOnCommitFailure(false);
        return dataSourceTransactionManager;
    }

    @Bean(name = "companyTransactionInterceptor")
    public TransactionInterceptor txAdvice(@Qualifier("companyTransactionManager") DataSourceTransactionManager manager) {
        /* 事务传播类型:
         * PROPAGATION_REQUIRED: 如果当前没有事务,就新建一个事务,如果已经存在一个事务中,加入到这个事务中.这是最常见的选择.
         * PROPAGATION_SUPPORTS: 支持当前事务，如果当前没有事务，就以非事务方式执行
         * PROPAGATION_MANDATORY: 使用当前的事务，如果当前没有事务，就抛出异常
         * PROPAGATION_REQUIRES_NEW: 新建事务，如果当前存在事务，把当前事务挂起
         * PROPAGATION_NOT_SUPPORTED: 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
         * PROPAGATION_NEVER: 以非事务方式执行，如果当前存在事务，则抛出异常
         * PROPAGATION_NESTED: 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与 PROPAGATION_REQUIRED 类似的操作
         *
         * 注意:同一个类之间的事务以初次进入的方法为准,如果入口方法为查询事务,这整个调用链都是查询事务.
         */

        // 只读事务类型
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        // 增删改事务类型
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        // 回滚规则: Exception报错回滚
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Throwable.class)));
        // 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 超时时间: 50s
        requiredTx.setTimeout(50000);

        RuleBasedTransactionAttribute independentTx = new RuleBasedTransactionAttribute();
        independentTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Throwable.class)));
        independentTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        independentTx.setTimeout(50000);

        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("qry*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("*", requiredTx);
        txMap.put("independent*", independentTx);

        NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
        nameMatchTransactionAttributeSource.setNameMap(txMap);
        return new TransactionInterceptor(manager, nameMatchTransactionAttributeSource);
    }

    @Bean(name = "companyTransactionAdvisor")
    public Advisor txAdviceAdvisor(@Qualifier("companyTransactionInterceptor") TransactionInterceptor transactionInterceptor) {
        /* 参见关于AspectJ语法详解
         * https://blog.csdn.net/sunlihuo/article/details/52701548
         */

        // 设置事务切入点为: BaseService基类(所有BaseService的子类都会被注入事务)
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("this(com.example.demo.service.impl.BaseService)");
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }

    /**
     * 会话管理
     *
     * @param dataSource 数据源
     * @return sql会话工厂
     * @throws Exception
     */
    @Bean(name = "companySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("companyDataSource") DataSource dataSource,
                                               ObjectProvider<Interceptor[]> interceptorsProvider) throws Exception {

        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // mybatis plus分页 插件
        // 防止全表更新与删除 插件
        Interceptor[] plugins = interceptorsProvider.getIfAvailable();
        factoryBean.setPlugins(plugins);

        // 设置ibatis的扫描包位置(类注解已经设置了,此处不再设置)
        //factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/demo/dao/sourceCompany"));
        return factoryBean.getObject();
    }

    @Bean(name = "companySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("companySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate tmp = new SqlSessionTemplate(sqlSessionFactory);
        // 开启自动驼峰命名转换
        tmp.getConfiguration().setMapUnderscoreToCamelCase(true);
        return tmp;
    }
}
