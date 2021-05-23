package com.company.transportmanagementadmin.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

	@Value("${datasource.url}")
	private String url;

	@Value("${datasource.username}")
	private String userName;

	@Value("${datasource.password}")
	private String password;

	@Primary
	@Bean(name = "dataSource")
	public DriverManagerDataSource setdDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(url);
		driverManagerDataSource.setUsername(userName);
		driverManagerDataSource.setPassword(password);
		return driverManagerDataSource;
	}

	@Primary
	@Bean(name = "namedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate setNamedParameterJdbcTemplate(DriverManagerDataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
