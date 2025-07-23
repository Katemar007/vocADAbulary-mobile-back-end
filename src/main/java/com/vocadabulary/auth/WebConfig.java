// Reads the headers from the request.
// Creates a MockUser.
// Stores it in MockUserContext so other parts of the backend can access the 
// “current user.”

package com.vocadabulary.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MockAuthInterceptor mockAuthInterceptor;

    @Autowired
    public WebConfig(MockAuthInterceptor mockAuthInterceptor) {
        this.mockAuthInterceptor = mockAuthInterceptor;
    }

    @Override
    public void addInterceptors(@org.springframework.lang.NonNull InterceptorRegistry registry) {
        registry.addInterceptor(mockAuthInterceptor);
    }
}