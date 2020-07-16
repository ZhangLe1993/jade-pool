package com.biubiu.agent.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biubiu.agent.agent.Handle;
import com.biubiu.agent.annotation.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class IndexController {

    @Autowired
    private Handle handle;

    @Autowired
    WebApplicationContext applicationContext;

    /**
     * 代理所有请求
     * @param request
     * @param response
     * @throws Exception
     */
    @SystemLog(description = "代理转发")
    @RequestMapping(value = "/**", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void proxy(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {
        // 获取本服务的 api
        handle.proxy(request, response);
    }


    private List<String> getRegisterUrls() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<String> urls = new ArrayList<>();
        for (RequestMappingInfo info : map.keySet()) {
            //获取 url 的 Set 集合，一个方法可能对应多个 url
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            // 这里可获取请求方式 Get,Post 等等
            // Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            urls.addAll(patterns);
        }
        return urls;
    }

}
