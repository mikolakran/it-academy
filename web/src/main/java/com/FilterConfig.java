package com;

import com.web.facades.PostFacade;
import com.web.facades.TopicFacade;
import com.web.filters.PostFilter;
import com.web.filters.WelcomeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private PostFacade postFacade;

    @Autowired
    private TopicFacade topicFacade;

    @Bean
    public FilterRegistrationBean<WelcomeFilter> welcomeFilterFilterRegistrationBean(){
        FilterRegistrationBean<WelcomeFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new WelcomeFilter(topicFacade));
        filterRegistrationBean.addUrlPatterns("/welcome");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<PostFilter> postsFilterFilterRegistrationBean(){
        FilterRegistrationBean<PostFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new PostFilter(postFacade));
        filterRegistrationBean.addUrlPatterns("/posts");
        return filterRegistrationBean;
    }
}
