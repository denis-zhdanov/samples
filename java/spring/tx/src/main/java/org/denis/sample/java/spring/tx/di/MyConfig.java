package org.denis.sample.java.spring.tx.di;

import com.zaxxer.hikari.HikariDataSource;
import org.denis.sample.java.spring.tx.util.AdaptiveDataSource;
import org.denis.sample.java.spring.tx.util.AdaptivePlatformTransactionManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootConfiguration
@EnableTransactionManagement
public class MyConfig {

    @Bean(DiConstants.DATA_SOURCE_RW)
    public DataSource rwDataSource(@Value("${spring.datasource.url}") String url,
                                   @Value("${spring.datasource.username}") String user)
    {
        return createDataSource(url, user);
    }

    @Bean(DiConstants.DATA_SOURCE_RO)
    public DataSource roDataSource(@Value("${spring.datasource.url}") String url,
                                   @Value("${spring.datasource.username}") String user)
    {
        HikariDataSource result = createDataSource(url, user);
        result.setReadOnly(true);
        return result;
    }

    private static HikariDataSource createDataSource(String url, String user) {
        HikariDataSource result = new HikariDataSource();
        result.setJdbcUrl(url);
        result.setUsername(user);
        result.setReadOnly(true);
        return result;
    }

    @Bean(DiConstants.JDBC_TX_MANAGER_RW)
    public PlatformTransactionManager rwJdbcTransactionManager(
            @Qualifier(DiConstants.DATA_SOURCE_RW) DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(DiConstants.JDBC_TX_MANAGER_RO)
    public PlatformTransactionManager roJdbcTransactionManager(
            @Qualifier(DiConstants.DATA_SOURCE_RO) DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(DiConstants.JDBC_TEMPLATE_RO)
    public JdbcTemplate roTemplate(@Qualifier(DiConstants.DATA_SOURCE_RO) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(DiConstants.JDBC_TEMPLATE_RW)
    public JdbcTemplate rwTemplate(@Qualifier(DiConstants.DATA_SOURCE_RW) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(DiConstants.SESSION_FACTORY_RO)
    public LocalSessionFactoryBean roSessionFactory(@Qualifier(DiConstants.DATA_SOURCE_RO) DataSource dataSource) {
        LocalSessionFactoryBean result = new LocalSessionFactoryBean();
        result.setDataSource(dataSource);
        return result;
    }

    @Bean(DiConstants.SESSION_FACTORY_RW)
    public LocalSessionFactoryBean rwSessionFactory(@Qualifier(DiConstants.DATA_SOURCE_RW) DataSource dataSource) {
        LocalSessionFactoryBean result = new LocalSessionFactoryBean();
        result.setDataSource(dataSource);
        return result;
    }

    @Bean(DiConstants.HIBERNATE_TX_MANAGER_RO)
    public PlatformTransactionManager roHibernateTransactionManager(
            @Qualifier(DiConstants.SESSION_FACTORY_RO) SessionFactory sessionFactory
    )
    {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean(DiConstants.HIBERNATE_TX_MANAGER_RW)
    public PlatformTransactionManager rwHibernateTransactionManager(
            @Qualifier(DiConstants.SESSION_FACTORY_RW) SessionFactory sessionFactory
    )
    {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean(DiConstants.ADAPTIVE_HIBERNATE_TX_MANAGER)
    public PlatformTransactionManager adaptiveTransactionManger(
            @Qualifier(DiConstants.ADAPTIVE_HIBERNATE_RO_TX_MANAGER) PlatformTransactionManager roTxManager,
            @Qualifier(DiConstants.ADAPTIVE_HIBERNATE_RW_TX_MANAGER) PlatformTransactionManager rwTxManager,
            @Qualifier(DiConstants.ADAPTIVE_DATA_SOURCE) AdaptiveDataSource dataSource)
    {
        return new AdaptivePlatformTransactionManager(roTxManager, rwTxManager, dataSource);
    }

    @Bean(DiConstants.ADAPTIVE_HIBERNATE_RO_TX_MANAGER)
    public PlatformTransactionManager adaptiveRoHibernateTransactionManager(
            @Qualifier(DiConstants.ADAPTIVE_SESSION_FACTORY) SessionFactory sessionFactory
    ) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean(DiConstants.ADAPTIVE_HIBERNATE_RW_TX_MANAGER)
    public PlatformTransactionManager adaptiveRwHibernateTransactionManager(
            @Qualifier(DiConstants.ADAPTIVE_SESSION_FACTORY) SessionFactory sessionFactory
    ) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean(DiConstants.ADAPTIVE_DATA_SOURCE)
    public AdaptiveDataSource adaptiveDataSource(
            @Qualifier(DiConstants.DATA_SOURCE_RO) DataSource roDataSource,
            @Qualifier(DiConstants.DATA_SOURCE_RW) DataSource rwDataSource
    ) {
        return new AdaptiveDataSource(roDataSource, rwDataSource);
    }

    @Bean(DiConstants.ADAPTIVE_SESSION_FACTORY)
    public LocalSessionFactoryBean roAdaptiveSessionFactory(AdaptiveDataSource dataSource) {
        LocalSessionFactoryBean result = new LocalSessionFactoryBean();
        result.setDataSource(dataSource);
        return result;
    }
}
