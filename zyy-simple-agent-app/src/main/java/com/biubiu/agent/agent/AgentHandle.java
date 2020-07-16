package com.biubiu.agent.agent;

import com.biubiu.agent.pojo.Agent;
import com.biubiu.agent.service.AgentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component
public class AgentHandle implements Handle {

    @Autowired
    private AgentService agentService;

    @Override
    public void proxy(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {
        // String url = URLDecoder.decode(request.getRequestURL().toString(), "UTF-8");
        // 添加参数
        URI uri = new URI(request.getRequestURI());
        String path = uri.getPath();
        Agent agent = agentService.findAgentByUrl(path);
        if(agent == null || StringUtils.isBlank(agent.getTargetHost())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println("没有关于这个链接的代理映射。");
            return;
        }
        String targetUrl = agent.getTargetUrl();
        String query = request.getQueryString();
        URI newUri = new URI(agent.getTargetHost() + (StringUtils.isNotBlank(targetUrl) ? targetUrl : path) + "?" + query);
        //执行代理查询
        String methodName = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(methodName);
        if(httpMethod == null) {
            return;
        }
        ClientHttpRequest delegate = new SimpleClientHttpRequestFactory().createRequest(newUri, httpMethod);
        Enumeration<String> headerNames = request.getHeaderNames();
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
        ClientHttpResponse clientHttpResponse = delegate.execute();
        response.setStatus(clientHttpResponse.getStatusCode().value());
        clientHttpResponse.getHeaders().entrySet().forEach((kv) -> {
            kv.getValue().stream().forEach(it -> {
                if (kv.getKey().startsWith("Content-")) {
                    response.setHeader(kv.getKey(), it);
                }
            });
        });
        StreamUtils.copy(clientHttpResponse.getBody(), response.getOutputStream());
    }
}
