package com.wordparser.demo.configurator;

import com.wordparser.demo.api.DocumentDownloadController;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class ShallowEtagConfiguration {

    @Bean
    public FilterRegistrationBean shallowEtagHeaderFilter() {
        ShallowEtagHeaderFilter filter = new ShallowEtagHeaderFilter();
        filter.setWriteWeakETag(true);
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.addUrlPatterns(DocumentDownloadController.ROOT_MAPPING);
        return bean;
    }

}
