package com.tensquare.encrypt.filter;

import com.google.common.base.Strings;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author jkun
 */
@Component
public class RSARequestFilter extends ZuulFilter {

    @Autowired
    private RsaService rsaService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        try {

            String decryptData = null;
            HashMap dataMap = null;
            String token = null;

            String url = request.getRequestURL().toString();
            InputStream stream = ctx.getRequest().getInputStream();
            String requestParam = StreamUtils.copyToString(stream, Charsets.UTF_8);

            if (!Strings.isNullOrEmpty(requestParam)) {
                System.out.printf("请求体中的密文: %s%n", requestParam);
                decryptData = rsaService.RSADecryptDataPEM(requestParam, RsaKeys.getServerPrvKeyPkcs8());

                System.out.printf("解密后的内容: %s%n", decryptData);
            }

            System.out.printf("request: %s >>> %s, data=%s%n", request.getMethod(), url, decryptData);

            if (!Strings.isNullOrEmpty(decryptData)) {
                System.out.println("json字符串写入request body");
                final byte[] reqBodyBytes = decryptData.getBytes();
                ctx.setRequest(new HttpServletRequestWrapper(request) {
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }

                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }
                });
            }

            System.out.println("转发request");
            // 设置request请求头中的Content-Type为application/json，否则api接口模块需要进行url转码操作
            ctx.addZuulRequestHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON) + ";charset=UTF-8");

        } catch (Exception e) {
            System.out.println(this.getClass().getName() + "运行出错" + e.getMessage());
            //设置出错状态，让error过滤器执行
            //ctx.set("sendErrorFilter.ran");
        }

        return null;
    }
}