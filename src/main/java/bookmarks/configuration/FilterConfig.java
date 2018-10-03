package bookmarks.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bookmarks.filter.AuthFilter;

@Configuration
@SuppressWarnings({"unused", "WeakerAccess"})
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterBean (AuthFilter authFilter){
        FilterRegistrationBean<AuthFilter> authFilterBean = new FilterRegistrationBean<>();
        authFilterBean.setFilter(authFilter);
        authFilterBean.setOrder(1);
        return authFilterBean;
    }
}
