package com.nlmeetingroom.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("X-Token");
        if(authHeader != null ){
            String token = authHeader;
            if(!token.equals("")&&!token.equals("undefined")) {
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if (claims != null) {
                        if ("admin".equals(claims.get("roles"))) {//如果是管理员
                            request.setAttribute("admin_claims", claims);
                        }
                        if ("user".equals(claims.get("roles"))) {//如果是用户
                            request.setAttribute("user_claims", claims);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        return true;
    }
}
