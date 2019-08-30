package com.ieps.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liangjw on 2019/8/26.
 */
public class UrlInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object o) throws Exception {

        String oldUrl = request.getRequestURL().toString();

        String newUrl = null;
        if (oldUrl.contains("ieps/")) {
            int index = oldUrl.indexOf("ieps/");
            System.out.println("length" + index);
            String[] str = oldUrl.split("ieps/");
            oldUrl = str[0] + str[1];
            System.out.println(oldUrl);
        }

        newUrl = oldUrl;

        System.out.println("URL" + oldUrl);
        System.out.println("URL" + newUrl);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object o, Exception e) throws Exception {

    }
}
