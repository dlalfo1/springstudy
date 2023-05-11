package com.gdu.staff.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@MapperScan(basePackages={"com.gdu.staff..mapper"}) 			  
@PropertySource(value={"classpath:application.properties"})  
@EnableTransactionManagement	
@Configuration	
public class DBConfig {
	
	@Autowired
	private Environment env; 
	
	
	// HikariConfig Bean - HikariDataSource Bean 만들기 전에 필요한 Bean
	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
		hikariConfig.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
		hikariConfig.setUsername(env.getProperty("spring.datasource.hikari.username"));
		hikariConfig.setPassword(env.getProperty("spring.datasource.hikari.password"));
		return hikariConfig;
	}
	
	// HikariDataSource Bean
	@Bean(destroyMethod="close")
	public HikariDataSource hikariDataSource() {
		return new HikariDataSource(hikariConfig());
	}
	
	// SqlSessionFactory Bean - sql 실행하고 값 받아오고 하는 애를 여기서도 쓴다. 
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception { // DB관련 처리하는 애라 try-catch 필요한데 여기선 일단 예외를 던질거임.
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(hikariDataSource());	// 히카리 전달
		bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(env.getProperty("mybatis.config-location")));
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
		return bean.getObject(); // SqlSessionFactoryBean에서 Object를 꺼내면 SqlSessionFactory가 나온다.(걍 사용법임)
		
	}
	
	// SqlSessionTemplate Bean (기존의 SqlSession) - SqlSessionFactory가 얘를 만든다.
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception { // 이 메소드에서 factory를 가져올거니까 예외가 발생함. 이것도 일단 던진
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	 // Bean이 Bean을 갖다 쓰는데 결국은 SqlSessionTemplate(SqlSession)을 만들기 위한 코드들이다.
	
	
	// TransactionManager Bean
	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(hikariDataSource());
	}
	

}
