package pub.toki.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.util.List;

@Configuration
public class Configure extends WebMvcConfigurationSupport {


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthInterceptor());
        super.addInterceptors(registry);
    }


    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthUserMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }


    @Bean
    public AuthInterceptor getAuthInterceptor() {
        return new AuthInterceptor();
    }
}
