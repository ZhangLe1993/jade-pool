package com.biubiu.agent.agent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Handle {
    /**
     * 代理接口
     * @param request
     * @param response
     */
    void proxy(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException;
}
