package com.biubiu.agent.controller;

import com.biubiu.agent.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "sys")
public class SysController {

    @Autowired
    private SysService sysService;
    /**
     * 获取JVM内存使用情况
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "memory")
    public ResponseEntity getMemInfo(HttpServletResponse response) throws IOException {
        Map<String, Object> map = sysService.quartzMem();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 获取CPU使用情况
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "cpu")
    public ResponseEntity getCpuInfo(HttpServletResponse response) throws IOException {
        Map<String, Object> map = sysService.cpuPercent();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
