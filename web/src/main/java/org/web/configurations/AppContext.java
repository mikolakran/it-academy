package org.web.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@PropertySource("classpath:properties-config.properties")
@ComponentScan(basePackages = {"dao", "org.web"})
@EnableTransactionManagement
public class AppContext {

    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSenderImpl getJavaMailSenderImpl() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setPort(Integer.parseInt(environment.getRequiredProperty("mail.port")));
        javaMailSender.setUsername(environment.getRequiredProperty("mail.username"));
        javaMailSender.setPassword(environment.getRequiredProperty("mail.password"));
        javaMailSender.setJavaMailProperties(javaMailSenderProperties());
        return javaMailSender;
    }

    private Properties javaMailSenderProperties() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", environment.getRequiredProperty("mail.transport.protocol"));
        properties.put("mail.smtp.auth", environment.getRequiredProperty("mail.smtp.auth"));
        properties.put("mail.smtp.host", environment.getRequiredProperty("mail.smtp.host"));
        properties.put("mail.smtp.starttls.enable", environment.getRequiredProperty("mail.smtp.starttls.enable"));
        properties.put("mail.debug", environment.getRequiredProperty("mail.debug"));
        properties.put("mail.smtp.starttls.required", environment.getRequiredProperty("mail.smtp.starttls.required"));
        properties.put("mail.smtp.socketFactory.class", environment.getRequiredProperty("mail.smtp.socketFactory.class"));
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean myEmf() {
        LocalContainerEntityManagerFactoryBean lc = new LocalContainerEntityManagerFactoryBean();
        lc.setDataSource(dataSource());
        lc.setPackagesToScan("entity");
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        lc.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        lc.setJpaProperties(hibernateProperties());
        return lc;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jTM = new JpaTransactionManager();
        jTM.setEntityManagerFactory(myEmf().getObject());
        return jTM;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dMD = new DriverManagerDataSource();
        dMD.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dMD.setUrl(environment.getRequiredProperty("jdbc.url"));
        dMD.setUsername(environment.getRequiredProperty("jdbc.username"));
        dMD.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dMD;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
