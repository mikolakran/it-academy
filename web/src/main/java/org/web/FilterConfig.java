package org.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web.filters.WelcomeFilter;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean<WelcomeFilter> welcomeFilterFilterRegistrationBean(){
        FilterRegistrationBean<WelcomeFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.addUrlPatterns("/welcome");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WelcomeFilter> postsFilterFilterRegistrationBean(){
        FilterRegistrationBean<WelcomeFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.addUrlPatterns("/posts");
        return filterRegistrationBean;
    }


/*    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        DelegatingFilterProxy welcomeFilter = new DelegatingFilterProxy();
        welcomeFilter.setTargetBeanName("welcomesFilter");

        servletContext.addFilter("welcomesFilter", welcomeFilter.getClass()).
                addMappingForUrlPatterns(null, false, "/welcome");

        DelegatingFilterProxy postFilter = new DelegatingFilterProxy();
        postFilter.setTargetBeanName("postFilter");

        servletContext.addFilter("postFilter", postFilter.getClass()).
                addMappingForUrlPatterns(null, false, "/posts");
    }*/
}
