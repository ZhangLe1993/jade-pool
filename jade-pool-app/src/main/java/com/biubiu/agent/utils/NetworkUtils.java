package com.biubiu.agent.utils;

import com.biubiu.agent.service.SysService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.Enumeration;

public class NetworkUtils {

    private final static Logger logger = LoggerFactory.getLogger(NetworkUtils.class);
    /**
     * 获取本实例代号
     *
     * @return
     * @throws SocketException
     */
    public static String getLocalHost() throws SocketException {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String host = ia.getHostName();//获取计算机主机名
            if (StringUtils.isNotBlank(host)) {
                return host;
            }
        } catch (UnknownHostException e) {
            logger.error("", e);
        }
        // 根据网卡取本机配置的IP
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        String localhost = "";
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (ip instanceof Inet4Address) {
                    String localip = ip.getHostAddress();
                    if (!"127.0.0.1".equals(localip)) {
                        localhost = localip;
                    }
                }
            }
            if ("bond0".equalsIgnoreCase(netInterface.getName()) || "bond0".equalsIgnoreCase(netInterface.getDisplayName())) {
                break;
            }
        }
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        localhost += " - " + pid;
        return localhost;
    }
}
