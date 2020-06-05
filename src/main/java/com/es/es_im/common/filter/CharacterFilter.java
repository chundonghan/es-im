package com.es.es_im.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CharacterFilter implements Filter
{

    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpCharacterRequest wrapRequest= new HttpCharacterRequest(request,request.getParameterMap());
        chain.doFilter(wrapRequest, resp);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
        // TODO Auto-generated method stub
        
    }

}
