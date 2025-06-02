package com.kynsoft.finamer.digitalsignature.infrastructure.repository;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "readEntityManagerFactory", transactionManagerRef = "readTransactionManager", basePackages = {
        "com.kynsoft.finamer.digitalsignature.infrastructure.repository.query" })
public class PostgresDBReadConfiguration {

    @Bean(name = "readDataSourceProperties")
    @ConfigurationProperties("spring.read-datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.read-datasource")
    public DataSource datasource(@Qualifier("readDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "readEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
            @Qualifier("readDataSource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.default_schema", "digital_signature"); // Añadiendo el esquema por defecto
        properties.put("hibernate.hbm2ddl.auto", "update"); // Usar "update" para actualizar automáticamente el esquema
        
        return builder.dataSource(dataSource)
                .packages("com.kynsoft.finamer.digitalsignature.infrastructure.entity")
                .properties(properties)
                .persistenceUnit("ReadDB").build();
    }

    @Bean(name = "readTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("readEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
