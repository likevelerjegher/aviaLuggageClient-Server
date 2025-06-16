package com.example.demo.config;

import com.example.demo.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/avia");
        dataSource.setUsername("mainPostgre");
        dataSource.setPassword("1234567890");
        return dataSource;
    }
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    //@Bean
    //public BCryptPasswordEncoder passwordEncoder() {
    //    return new BCryptPasswordEncoder();
    //}
}