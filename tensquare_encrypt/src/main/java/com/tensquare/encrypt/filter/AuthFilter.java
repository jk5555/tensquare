package com.tensquare.encrypt.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限校验过滤器
 * @author jkun
 */
@Component
public class AuthFilter extends ZuulFilter {
    /**
     * 过滤器的类型，对应在请求的哪个生命周期执行
     * @return string
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器的执行排名
     * @return int
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    /**
     * 过滤器是否应该被执行，这里直接写死执行了，实际使用可以根据某些业务情况编写代码判断是否可以执行
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 具体执行逻辑
     * @return object
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        System.out.println(request.getMethod() + request.getLocalAddr() + request.getLocalPort());
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            ctx.setResponseStatusCode(401);
            ctx.setSendZuulResponse(false);
            return null;
        }else {
            System.out.println("token is on, could be forward");
        }
        return null;
    }
}
