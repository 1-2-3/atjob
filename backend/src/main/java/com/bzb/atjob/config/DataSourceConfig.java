package com.bzb.atjob.config;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 主数据源配置
 */
@Configuration
// 扫描 mapper 接口的包名列表。注意此处涵盖的 mapper 会使用主数据源，范围不能与使用其他数据源的 Mapper 交叉
@MapperScan(basePackages = { "com.bzb.atjob.app" })
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfig {
    private String url;

    private String username;
    private String password;
    private String driverClassName;

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName("dataSource");
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        // 配置初始化大小、最小、最大
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(60000);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        // 这里建议配置为TRUE，防止取到的连接不可用
        dataSource.setTestOnBorrow(false); // 白杰配的是false
        dataSource.setTestOnReturn(false);
        // 缓存游标
        dataSource.setPoolPreparedStatements(true);
        // 缓存游标大小
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        // 要监控的项目: 监控统计用的filter:stat, 防御sql注入的filter:wall, 日志用的filter:log4j
        try {
            dataSource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        dataSource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        // 这里配置提交方式，默认就是TRUE，可以不用配置
        // dataSource.setDefaultAutoCommit(true);
        // 验证连接有效与否的SQL，不同的数据配置不同
        dataSource.setValidationQuery("SELECT 1 from dual");

        return dataSource;
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "paisSqlSessionFactory")
    @Primary
    public SqlSessionFactory paisSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // mybatis 配置文件路径
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        // 指定 mybatis mapper xml 文件的扫描规则，要配合 pom 里面 build/resources 里面的配置
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:**/repository/*.xml"));
        return sessionFactory.getObject();
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}