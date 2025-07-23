// Reads the headers from the request.
// Creates a MockUser.
// Stores it in MockUserContext so other parts of the backend can access the 
// “current user.”

package com.vocadabulary.auth;

import com.vocadabulary.auth.MockUser;
import com.vocadabulary.auth.MockUserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MockAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@org.springframework.lang.NonNull HttpServletRequest request,
                             @org.springframework.lang.NonNull HttpServletResponse response,
                             @org.springframework.lang.NonNull Object handler) {
        String userId = request.getHeader("X-Mock-User-Id");
        String role = request.getHeader("X-Mock-User-Role");

        if (userId != null && role != null) {
            MockUser mockUser = new MockUser(Long.parseLong(userId), role);
            MockUserContext.setCurrentUser(mockUser);
        }
        System.out.println(">>> Interceptor running...");
        System.out.println(">>> Header X-Mock-User-Id: " + userId);
        System.out.println(">>> Header X-Mock-User-Role: " + role);
        
        return true;
    }
}