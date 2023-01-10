package org.web.configurations;


import jakarta.servlet.*;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.io.File;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private final File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppContext.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
    }

    private MultipartConfigElement getMultipartConfigElement() {
        int mavUploadSizeInMb = 1024 * 1024;
        return new
                MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                mavUploadSizeInMb, mavUploadSizeInMb * 2L, mavUploadSizeInMb / 2);
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new HiddenHttpMethodFilter() };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        DelegatingFilterProxy welcomeFilter = new DelegatingFilterProxy();
        welcomeFilter.setTargetBeanName("welcomesFilter");

        servletContext.addFilter("welcomesFilter", welcomeFilter.getClass()).
                addMappingForUrlPatterns(null, false, "/welcome");

        DelegatingFilterProxy postFilter = new DelegatingFilterProxy();
        postFilter.setTargetBeanName("postFilter");

        servletContext.addFilter("postFilter", postFilter.getClass()).
                addMappingForUrlPatterns(null, false, "/posts");
    }
}
