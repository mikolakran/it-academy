package com;

import com.RunSpringBootConfiguration;
import com.web.facades.PostFacade;
import com.web.facades.TopicFacade;
import com.web.filters.PostFilter;
import com.web.filters.WelcomeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RunSpringBootConfiguration.class);
    }
}
