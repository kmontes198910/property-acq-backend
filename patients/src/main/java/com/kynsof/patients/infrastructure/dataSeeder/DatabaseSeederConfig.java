//package com.kynsof.patients.infrastructure.dataSeeder;
//
//
//import com.kynsof.patients.infrastructure.repository.query.GeographicLocationReadDataJPARepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.init.ScriptUtils;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
//@Configuration
//public class DatabaseSeederConfig {
//
//    private final DataSource dataSource;
//    private final GeographicLocationReadDataJPARepository repositoryQuery;
//
//
//    public DatabaseSeederConfig(GeographicLocationReadDataJPARepository repositoryQuery,
//                                DataSource dataSource) {
//        this.repositoryQuery = repositoryQuery;
//        this.dataSource = dataSource;
//
//    }
//
//    @PostConstruct
//    public void seedDatabase() {
//        try {
//            if (repositoryQuery.count() == 0) {
//                ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("geographiclocation.sql"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}