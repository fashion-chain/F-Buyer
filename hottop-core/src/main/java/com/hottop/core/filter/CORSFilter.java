package com.hottop.core.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setHeader("Access-Control-Allow-Origin", "*");
//        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Max-Age", "3600");
//        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        res.setHeader("Access-Control-Allow-Headers", "*");
        filterChain.doFilter(servletRequest, res);
    }
}
