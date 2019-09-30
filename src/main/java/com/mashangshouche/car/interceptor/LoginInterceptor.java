package com.mashangshouche.car.interceptor;

import com.mashangshouche.car.common.AuthHold;
import com.mashangshouche.car.common.IgnoreLogin;
import com.mashangshouche.car.common.JwtHelper;
import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.entity.User;
import com.mashangshouche.car.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class LoginInterceptor extends DefaultInterceptor implements HandlerInterceptor {
    private static final String PACKAGE_PREFIX = "com.mashangshouche.car";
    private static final String TOKEN_KEY = "token";
    @Autowired
    UserService userService;
    @Autowired
    JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Class<?> beanType = handlerMethod.getBeanType();
        String typeName = beanType.getName();
        if (!typeName.startsWith(PACKAGE_PREFIX)) {
            return true;
        }

        if (beanType.isAnnotationPresent(IgnoreLogin.class)) {
            return true;
        }

        if (handlerMethod.hasMethodAnnotation(IgnoreLogin.class)) {
            return true;
        }

        //登录校验
        String token = request.getHeader(TOKEN_KEY);

        if (StringUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if (null != cookies && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (TOKEN_KEY.equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (StringUtils.isEmpty(token)) {
            endResponse(request, response, Result.error(401, "未登录"));
            return false;
        }
        Optional<String> userIdOptional = jwtHelper.verify(token);
        if (!userIdOptional.isPresent()) {
            endResponse(request, response, Result.error(401, "未登录"));
            return false;
        }
        Optional<User> userOptional = userService.findById(userIdOptional.get());
        if (!userOptional.isPresent()) {
            endResponse(request, response, Result.error(401, "用户不存在"));
            return false;
        } else {
            User user = userOptional.get();
            AuthHold.setUser(user);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        AuthHold.clean();
    }
}
