package com.biubiu.agent.service;

import com.biubiu.agent.utils.NetworkUtils;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.biubiu.agent.utils.NetworkUtils.getLocalHost;

@Service
public class SysService {

    private final static Logger logger = LoggerFactory.getLogger(SysService.class);

    /**
     * 获取内存
     * @return
     */
    public Map<String, Object> quartzMem() {
        Runtime run = Runtime.getRuntime();
        String host = "";
        try {
            host = getLocalHost();
        } catch (SocketException e) {
            logger.error("", e);
        }
        long max = run.maxMemory() / 1024 / 1024;
        long used = (run.totalMemory() - run.freeMemory()) / 1024 / 1024;
        int percent = (int) ((double) used / (double) max * 100);
        Map<String, Object> info = new HashMap<>();
        info.put("host", host);
        info.put("max", max);
        info.put("used", used);
        info.put("percent", percent);
        return info;
        // redisTemplate.opsForHash().putAll("mem_info", ImmutableMap.of(host, JSONObject.toJSONString(info)));
        // redisTemplate.expire("mem_info", 20, TimeUnit.SECONDS);
    }

    /**
     *
     */
    public Map<String, Object> cpuPercent() {
        int count = Thread.activeCount();
        String host = "";
        try {
            host = getLocalHost();
        } catch (SocketException e) {
            logger.error("", e);
        }
        int percent = cpuUsage();
        return ImmutableMap.of("host", host, "percent", percent);
    }

    /**
     * 功能：获取Linux系统cpu使用率
     * */
    public static int cpuUsage() {
        try {
            Map<?, ?> map1 = cpuinfo();
            Thread.sleep(5 * 1000);
            Map<?, ?> map2 = cpuinfo();
            long user1 = Long.parseLong(map1.get("user").toString());
            long nice1 = Long.parseLong(map1.get("nice").toString());
            long system1 = Long.parseLong(map1.get("system").toString());
            long idle1 = Long.parseLong(map1.get("idle").toString());

            long user2 = Long.parseLong(map2.get("user").toString());
            long nice2 = Long.parseLong(map2.get("nice").toString());
            long system2 = Long.parseLong(map2.get("system").toString());
            long idle2 = Long.parseLong(map2.get("idle").toString());

            long total1 = user1 + system1 + nice1;
            long total2 = user2 + system2 + nice2;
            float total = total2 - total1;

            long totalIdle1 = user1 + nice1 + system1 + idle1;
            long totalIdle2 = user2 + nice2 + system2 + idle2;
            float totalidle = totalIdle2 - totalIdle1;
            float cpusage = (total / totalidle) * 100;
            return (int) cpusage;
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 功能：CPU使用信息
     * */
    public static Map<?, ?> cpuinfo() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                logger.error("", e2);
            }
        }
        return map;
    }

}
