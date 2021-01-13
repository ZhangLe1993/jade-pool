package com.biubiu.agent.controller;

import com.biubiu.agent.annotation.SystemLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
public class ProxyController {

    @Value("${proxy.targetAddr}")
    private String targetAddr;

    /**
     * 代理所有请求
     * @param request
     * @param response
     * @throws Exception
     */
    @SystemLog(description = "代理转发")
    @RequestMapping(value = "/proxyxxx/**", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void proxy(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {
        // String url = URLDecoder.decode(request.getRequestURL().toString(), "UTF-8");
        URI uri = new URI(request.getRequestURI());
        String path = uri.getPath();
        String query = request.getQueryString();
        URI newUri = new URI(targetAddr + path.replace("/proxy", "") + "?" + query);
        // 执行代理查询
        String methodName = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(methodName);
        if(httpMethod == null) {
            return;
        }
        ClientHttpRequest delegate = new SimpleClientHttpRequestFactory().createRequest(newUri, httpMethod);
        Enumeration<String> headerNames = request.getHeaderNames();
        // 设置请求头
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> v = request.getHeaders(headerName);
            List<String> arr = new ArrayList<>();
            while (v.hasMoreElements()) {
                arr.add(v.nextElement());
            }
            delegate.getHeaders().addAll(headerName, arr);
        }
        StreamUtils.copy(request.getInputStream(), delegate.getBody());
        // 执行远程调用
        ClientHttpResponse clientHttpResponse = delegate.execute();
        response.setStatus(clientHttpResponse.getStatusCode().value());
        // 设置响应头
        clientHttpResponse.getHeaders().forEach((key, value) -> value.forEach(it -> {
            response.setHeader(key, it);
        }));
        StreamUtils.copy(clientHttpResponse.getBody(), response.getOutputStream());
    }
}
