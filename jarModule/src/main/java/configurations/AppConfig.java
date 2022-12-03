package configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@ComponentScan("dao")
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean myEmf() {
        LocalContainerEntityManagerFactoryBean lc = new LocalContainerEntityManagerFactoryBean();
        lc.setDataSource(dataSource());
        lc.setPackagesToScan("entity");
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        lc.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "false");
        lc.setJpaProperties(properties);
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
        dMD.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dMD.setUrl("jdbc:mysql://localhost:3306/it-academy");
        dMD.setUsername("root");
        dMD.setPassword("");
        return dMD;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
